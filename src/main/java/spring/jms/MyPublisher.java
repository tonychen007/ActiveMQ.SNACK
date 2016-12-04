package spring.jms;

import javax.jms.Destination;

import org.springframework.jms.core.JmsTemplate;

public class MyPublisher {
	private JmsTemplate template;
	private Destination destination;
	private MyMessageCreator myMsgCreator;
	
	public void sendMessage() {
		template.send(destination,myMsgCreator);
	}

	public JmsTemplate getTemplate() {
		return template;
	}

	public void setTemplate(JmsTemplate template) {
		this.template = template;
	}

	public Destination getDestination() {
		return destination;
	}

	public void setDestination(Destination destination) {
		this.destination = destination;
	}

	public MyMessageCreator getMyMsgCreator() {
		return myMsgCreator;
	}

	public void setMyMsgCreator(MyMessageCreator myMsgCreator) {
		this.myMsgCreator = myMsgCreator;
	}	
}
