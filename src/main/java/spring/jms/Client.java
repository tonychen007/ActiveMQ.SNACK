package spring.jms;

import org.apache.activemq.broker.BrokerService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Client {

	public static void main(String[] args) throws Exception {
		BrokerService broker = new BrokerService();
		broker.addConnector("tcp://localhost:61616");
		broker.setPersistent(false);
		broker.start();

		ClassPathXmlApplicationContext xmlAppCtx = new ClassPathXmlApplicationContext("classpath:activemqFactory.xml");
		MyPublisher publisher = (MyPublisher) xmlAppCtx.getBean("myPublisher");		
		publisher.sendMessage();
	}
}
