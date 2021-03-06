package edu.thi.process;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.runtime.Execution;

import edu.thi.models.Bestellungen;

public class LagerplatzAbrufen implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        //Bestellungen bestellung = (Bestellungen) execution.getVariable("bestellung");
        
        Long bestellID = (Long) execution.getVariable("bestellung.bestellID");
        String name = (String) execution.getVariable("bestellung.name");
        Integer anzahl = (Integer) execution.getVariable("bestellung.anzahl");
        
        Map<String, Object> data = new HashMap<String, Object>();
        //data.put("bestellung", bestellung);
        data.put("bestellID", bestellID);
        data.put("name", name);
        data.put("anzahl",anzahl);
        
        
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