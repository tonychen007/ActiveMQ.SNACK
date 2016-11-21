package com.tgb.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class JMSProducer {

	private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
	private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
	private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;
	private static final int SENDNUM = 10;

	public static void main(String[] args) {
		ConnectionFactory connectionFactory;
		Connection connection = null;
		Session session;
		Destination destination;
		MessageProducer messageProducer;
		connectionFactory = new ActiveMQConnectionFactory(JMSProducer.USERNAME, JMSProducer.PASSWORD,
				JMSProducer.BROKEURL);

		try {
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue("HelloWorld");
			messageProducer = session.createProducer(destination);
			sendMessage(session, messageProducer);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void sendMessage(Session session, MessageProducer messageProducer) throws Exception {
		for (int i = 0; i < JMSProducer.SENDNUM; i++) {
			TextMessage message = session.createTextMessage("ActiveMQ Send" + i);
			message.setIntProperty("index", i);
			System.out.println("Send:Activemq Send" + i);
			messageProducer.send(message);
		}
	}
}
