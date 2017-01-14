package edu.thi.process;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class WebServiceClient implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Long bestellID = (Long) execution.getVariable("bestellID");

        Client client = Client.create();

        WebResource webResource = client
           .resource("http://localhost:8080/Bestellungen");
        String input = "{\"bestellID\":\""+bestellID+"}";
        ClientResponse response = webResource.type("application/json")
           .post(ClientResponse.class, input);
        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
             + response.getStatus());
         }

         //Bestellungen bestellungen = response.getEntity(Bestellungen.class);
         execution.getVariable("ack");
    }

}