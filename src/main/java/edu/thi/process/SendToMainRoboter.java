package edu.thi.process;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.runtime.Execution;

public class SendToMainRoboter implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Boolean confirmation = (Boolean) execution.getVariable("confirmation");
        
        Map<String, Object> data = new HashMap<>();
        data.put("confirmation", confirmation);
        
        
        RuntimeService runtimeService = execution.getEngineServices().getRuntimeService();
        
        // Search if a process is already waiting at an intermediate event
        Execution waitingExecution = runtimeService.createExecutionQuery()
                  .messageEventSubscriptionName("IntegrationRoboter")
                  .singleResult();

        runtimeService.messageEventReceived("IntegrationRoboter", waitingExecution.getId(), data);
        
    }

}