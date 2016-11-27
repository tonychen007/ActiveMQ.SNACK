package com.tgb.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;

public class JMSConsumer3 {
	private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
	private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
	//private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;

	private static final String BROKEURL = "tcp://0.0.0.0:61636";
	
	public static void main(String[] args) {
		ConnectionFactory connectionFactory;
		Connection connection = null;
		Session session = null;
		Destination destination;
		MessageConsumer messageConsumer = null;
		connectionFactory = new ActiveMQConnectionFactory(JMSConsumer3.USERNAME, JMSConsumer3.PASSWORD,
				JMSConsumer3.BROKEURL);

		try {
			// String selector = "index % 2 = 0";
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

		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public static void consumerPoll(MessageConsumer msgConsumer) throws JMSException {
		while (true) {
			TextMessage textMessage = (TextMessage) msgConsumer.receive(1000);
			if (textMessage != null) {
				System.out.println("Recv:" + textMessage.getText());
			} else {
				break;
			}
		}
	}
}
