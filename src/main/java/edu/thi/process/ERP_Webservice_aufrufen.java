package edu.thi.process;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.runtime.Execution;

import com.sun.jersey.api.client.Client; 
import com.sun.jersey.api.client.WebResource;


public class ERP_Webservice_aufrufen implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Long bestellID = (Long) execution.getVariable("bestellID");

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