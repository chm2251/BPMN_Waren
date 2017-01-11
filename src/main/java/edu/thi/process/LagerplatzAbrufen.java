package edu.thi.process;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.runtime.Execution;

public class LagerplatzAbrufen implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Long anzahl = (Long) execution.getVariable("anzahl");
        
        Map<String, Object> data = new HashMap<>();
        data.put("anzahl", anzahl);
        
        
        RuntimeService runtimeService = execution.getEngineServices().getRuntimeService();
        
        // Search if a process is already waiting at an intermediate event
        Execution waitingExecution = runtimeService.createExecutionQuery()
                  .messageEventSubscriptionName("AnzahlToLVS")
                  .singleResult();

        if (waitingExecution != null) {
            // An execution is waiting --> continue it
            runtimeService.messageEventReceived("AnzahlToLVS", waitingExecution.getId(), data);
        } else {
            // No execution is waiting --> start a new Aggregator instance
            runtimeService.startProcessInstanceByMessage("AnzahlToLVS", data);
        }
    }

}