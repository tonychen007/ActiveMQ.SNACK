package com.tgb.activemq;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

public class JMSConcurrentConsumer {
	
	public static void main(String[] args) {
		ExecutorService thService = Executors.newCachedThreadPool();
		thService.submit(new ConcurrentConsumer());
		thService.submit(new ConcurrentConsumer());
		thService.shutdown();
	}
}

class ConcurrentConsumer implements Runnable {
	private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
	private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
	//private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;

	private static final String BROKEURL = "tcp://0.0.0.0:61616";
	
	@Override
	public void run() {
		ConnectionFactory connectionFactory;
		Connection connection = null;
		Session session = null;
		Destination destination;
		MessageConsumer messageConsumer = null;
		connectionFactory = new ActiveMQConnectionFactory(ConcurrentConsumer.USERNAME, ConcurrentConsumer.PASSWORD,
				ConcurrentConsumer.BROKEURL);

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
}
