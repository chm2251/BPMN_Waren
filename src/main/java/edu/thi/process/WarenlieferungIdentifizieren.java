package edu.thi.process;

import java.util.HashMap;
import java.util.Map;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.runtime.Execution;

import edu.thi.models.Bestellungen;

public class WarenlieferungIdentifizieren implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
    
        Long bestellID = (Long) execution.getVariable("bestellID");
        String name = (String) execution.getVariable("name");
        Integer anzahl = (Integer) execution.getVariable("anzahl");
        String processId = (String) execution.getVariable("processId");
        
        Bestellungen bestellung = new Bestellungen(); 
        bestellung.setBestellID(bestellID);
        bestellung.setAnzahl(anzahl);
        bestellung.setName(name);
        bestellung.setProcessId(processId);
       
//        RuntimeService runtimeService = execution.getEngineServices().getRuntimeService();
//        
//        // Search if a process is already waiting at an intermediate event
//        Execution waitingExecution = runtimeService.createExecutionQuery()
//                  .messageEventSubscriptionName("AnfrageToERP")
//                  .singleResult();
//
//        if (waitingExecution != null) {
//            // An execution is waiting --> continue it
//            runtimeService.messageEventReceived("AnfrageToERP", waitingExecution.getId(), ack);
//        } else {
//            // No execution is waiting --> start a new Aggregator instance
//            runtimeService.startProcessInstanceByMessage("AnfrageToERP", ack);
//        }

      
    }

}