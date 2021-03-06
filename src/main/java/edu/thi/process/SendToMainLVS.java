package edu.thi.process;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.runtime.Execution;

public class SendToMainLVS implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Long lagerplatzid = (Long) execution.getVariable("lagerplatzid.lagerplatzId");
        
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("lagerplatzid", lagerplatzid);
        
        
        RuntimeService runtimeService = execution.getEngineServices().getRuntimeService();
        
        // Search if a process is already waiting at an intermediate event
        Execution waitingExecution = runtimeService.createExecutionQuery()
                  .messageEventSubscriptionName("IntegrationLVS")
                  .singleResult();

        runtimeService.messageEventReceived("IntegrationLVS", waitingExecution.getId(), data);
        
    }

}