package edu.thi.process;

import java.util.HashMap;
import java.util.Map;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.runtime.Execution;



public class WarenlieferungIdentifizieren implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
    
        Long bestellID = (Long) execution.getVariable("bestellung.bestellID");
        
        
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("bestellID", bestellID);
        
        RuntimeService runtimeService = execution.getEngineServices().getRuntimeService();
        
        // Search if a process is already waiting at an intermediate event
        Execution waitingExecution = runtimeService.createExecutionQuery()
                  .messageEventSubscriptionName("AnfrageToERP")
                  .singleResult();

        if (waitingExecution != null) {
            // An execution is waiting --> continue it
            runtimeService.messageEventReceived("AnfrageToERP", waitingExecution.getId(), data);
        } else {
            // No execution is waiting --> start a new Aggregator instance
            runtimeService.startProcessInstanceByMessage("AnfrageToERP", data);
        }

      
    }

}