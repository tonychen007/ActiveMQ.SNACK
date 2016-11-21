package com.tgb.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;

public class JMSConsumer {
	private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
	private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
	private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;

	public static void main(String[] args) {
		ConnectionFactory connectionFactory;
		Connection connection = null;
		Session session;
		Destination destination;
		MessageConsumer messageConsumer;
		connectionFactory = new ActiveMQConnectionFactory(JMSConsumer.USERNAME, JMSConsumer.PASSWORD,
				JMSConsumer.BROKEURL);

		try {
			//String selector = "index % 2 = 0";					
			connection = connectionFactory.createConnection();	
			connection.start();	
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue("HelloWorld");
			messageConsumer = session.createConsumer(destination);
			messageConsumer.setMessageListener(new MessageListener() {
				
				@Override
				public void onMessage(Message message) {
					ActiveMQTextMessage amq = (ActiveMQTextMessage) message;
					try {
						System.out.println("onMessage:" + amq.getText());
					} catch (JMSException e) {

					}
				}
			});
			
//			while (true) {
//				TextMessage textMessage = (TextMessage) messageConsumer.receive(1000);				
//				if (textMessage != null) {
//					System.out.println("Recv:" + textMessage.getText());					
//				} else {
//					break;
//				}
//			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
