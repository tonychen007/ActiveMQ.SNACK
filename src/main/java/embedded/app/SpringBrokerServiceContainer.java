package embedded.app;

import org.apache.activemq.broker.BrokerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringBrokerServiceContainer {
	private static final Logger logger = LoggerFactory.getLogger(BrokerServiceContainer.class);
	private static final String APP_XML_CFG = "classpath:SpringBroker.xml";

	public static void main(String[] args) {
		ClassPathXmlApplicationContext xmlAppCtx = new ClassPathXmlApplicationContext(APP_XML_CFG);
		BrokerService broker = xmlAppCtx.getBean("broker", BrokerService.class);
		xmlAppCtx.close();
		
		try {
			broker.start();
		} catch (Exception e) {

		}
	}
}
