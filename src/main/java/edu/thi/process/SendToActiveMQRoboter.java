package edu.thi.process;
import java.io.Serializable;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;


import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import edu.thi.models.LagerplatzID;


public class SendToActiveMQRoboter implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
      
        String user = ActiveMQConnection.DEFAULT_USER;
        String password = ActiveMQConnection.DEFAULT_PASSWORD;
        String url = ActiveMQConnection.DEFAULT_BROKER_URL;
        Destination destination;
        
        Long lagerplatzId =  (Long) execution.getVariable("lagerplatzid");
        
        LagerplatzID lagerplatz = new LagerplatzID();
        lagerplatz.setLagerplatzId(lagerplatzId);
        lagerplatz.setProcessId(execution.getId());
        
    
        
        try {
            Connection connection = null;
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, password, url);
            connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("RoboterQueue");
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            ObjectMessage message = session.createObjectMessage();
            message.setJMSMessageID("lagerplatzId_" + lagerplatz.getLagerplatzId());
            message.setObject((Serializable) lagerplatz);        
            producer.send(message);

            connection.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}


