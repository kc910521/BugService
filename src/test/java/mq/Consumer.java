package mq;


import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import java.text.DecimalFormat;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Consumer {

    private static String brokerURL = "tcp://localhost:61616";
    private static transient ConnectionFactory factory;
    private transient Connection connection;
    private transient Session session;
    
    public Consumer() throws JMSException {
    	factory = new ActiveMQConnectionFactory(brokerURL);
    	connection = factory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }
    
    public void close() throws JMSException {
        if (connection != null) {
            connection.close();
        }
    }    
    
    public static void main(String[] args) throws JMSException {
    	Consumer consumer = new Consumer();
    	for (String stock : args) {
    		Destination destination = consumer.getSession().createTopic("STOCKS." + stock);
    		MessageConsumer messageConsumer = consumer.getSession().createConsumer(destination);
    		messageConsumer.setMessageListener( consumer.new Listener());
    	}
    }
	
	public Session getSession() {
		return session;
	}

	
	


	class Listener implements MessageListener {

		public void onMessage(Message message) {
			try {
				MapMessage map = (MapMessage)message;
				String stock = map.getString("stock");
				double price = map.getDouble("price");
				double offer = map.getDouble("offer");
				boolean up = map.getBoolean("up");
				DecimalFormat df = new DecimalFormat( "#,###,###,##0.00" );
				System.out.println(stock + "\t" + df.format(price) + "\t" + df.format(offer) + "\t" + (up?"up":"down"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
