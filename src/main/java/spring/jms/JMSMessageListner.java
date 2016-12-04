package spring.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class JMSMessageListner implements MessageListener {

	@Override
	public void onMessage(Message message) {
		try {
			System.out.println("Received message for: " + ((TextMessage) message).getText());
		} catch (JMSException e) {
			
		}
	}
}
