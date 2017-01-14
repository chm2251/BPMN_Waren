package edu.thi.process;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.runtime.Execution;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class WarenlieferungIdentifizieren implements JavaDelegate {

    @SuppressWarnings("unchecked")
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Long bestellID = (Long) execution.getVariable("bestellID");

        Client client = Client.create();

        WebResource webResource = client
           .resource("http://localhost:8080/Bestellungen");
        String input = "{\"bestellID\":\""+bestellID+"}";
        ClientResponse response = webResource.type("application/jso")
           .post(ClientResponse.class, input);
        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
             + response.getStatus());
         }

         //Bestellungen bestellungen = response.getEntity(Bestellungen.class);
         Map<String, Object> ack = (Map<String, Object>) execution.getVariable("ack");
         
         Map<String, Object> data = new HashMap<String, Object>();
         data.put("ack", ack);
         
         RuntimeService runtimeService = execution.getEngineServices().getRuntimeService();
         
         // Search if a process is already waiting at an intermediate event
         Execution waitingExecution = runtimeService.createExecutionQuery()
                   .messageEventSubscriptionName("AnfrageToERP")
                   .singleResult();

         if (waitingExecution != null) {
             // An execution is waiting --> continue it
             runtimeService.messageEventReceived("AnfrageToERP", waitingExecution.getId(), ack);
         } else {
             // No execution is waiting --> start a new Aggregator instance
             runtimeService.startProcessInstanceByMessage("AnfrageToERP", ack);
         }
    }

}