package edu.thi.process;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.ClientBuilder;
//import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.runtime.Execution;

import com.sun.jersey.api.client.Client; 
import com.sun.jersey.api.client.WebResource;
import edu.thi.models.Bestellungen;

public class ERP_Webservice_aufrufen implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Bestellungen bestellung = (Bestellungen) execution.getVariable("bestellung");
        Long bestellID = bestellung.getBestellID();
        final String WEBSERVICE_ADDRESS = "http://localhost:8080/ErpSystem/webapi/Bestellungen/"+bestellID; 


        Client c = Client.create();
        WebResource resource = c.resource(WEBSERVICE_ADDRESS); 
        String response = resource.get(String.class); 
        //System.out.println(response);

        String message = null; 

        if (response.equals("Ja")){
            message = "IstBestellt";
        }
        else    { 
            message = "IstNichtBestellt";
        }
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("response", response);
        
        RuntimeService runtimeService = execution.getEngineServices().getRuntimeService();
        
        // Search if a process is already waiting at an intermediate event
        Execution waitingExecution = runtimeService.createExecutionQuery()
                  .messageEventSubscriptionName(message)
                  .singleResult();

        if (waitingExecution != null) {
            // An execution is waiting --> continue it
            runtimeService.messageEventReceived(message, waitingExecution.getId(), data);
        } else {
            // No execution is waiting --> start a new Aggregator instance
            runtimeService.startProcessInstanceByMessage(message, data);
        }



    }
}