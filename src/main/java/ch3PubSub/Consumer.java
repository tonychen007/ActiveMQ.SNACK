package ch3PubSub;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Consumer {

	private static String brokerURL = "ssl://localhost:61617";
	//protected static String brokerURL = "failover:(tcp://0.0.0.0:61616,tcp://0.0.0.0:61626)";
	private static transient ConnectionFactory factory;
	private transient Connection connection;
	private transient Session session;

	public Consumer() throws JMSException {		
		factory = new ActiveMQConnectionFactory(brokerURL);		
		connection = factory.createConnection();
		connection.setClientID("SomeClientID");
		connection.start();
		//session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);		
	}

	public void close() throws JMSException {
		if (connection != null) {
			connection.close();
		}
	}

	public static void main(String[] args) throws JMSException {
		Consumer consumer = new Consumer();
		String[] sbs = new String[] { "Taiwan", "USA", "JAPAN" };
		for (String stock : sbs) {
			Topic topic = consumer.getSession().createTopic("STOCKS." + stock + "?retroactive=true");		
			//MessageConsumer messageConsumer = consumer.getSession().createConsumer(destination);			
			//messageConsumer.setMessageListener(new Listener());
			
			TopicSubscriber durableSubscribe =  consumer.getSession().createDurableSubscriber(topic,"STOCKS." + stock);
			durableSubscribe.setMessageListener(new Listener());
		}
	}

	public Session getSession() {
		return session;
	}

}
