package spring.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.core.MessageCreator;

public class MyMessageCreator implements MessageCreator {

	@Override
	public Message createMessage(Session session) throws JMSException {
		TextMessage txtMsg = session.createTextMessage();
		txtMsg.setText("This is a test");
		return txtMsg;
	}
}
