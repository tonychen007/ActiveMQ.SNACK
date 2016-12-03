package embedded.app;

import java.util.ArrayList;
import java.util.List;

import org.apache.activemq.broker.BrokerPlugin;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.security.AuthenticationUser;
import org.apache.activemq.security.SimpleAuthenticationPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BrokerServiceContainer {
	
	private static final Logger logger = LoggerFactory.getLogger(BrokerServiceContainer.class);
	private static final String CONNECTOR_ADDR = "tcp://localhost:61616";
	
	public static void main(String[] args) {
		BrokerService broker = new BrokerService();
		broker.setBrokerName("myBroker");
		broker.setDataDirectory("data/");
		
		SimpleAuthenticationPlugin authen = new SimpleAuthenticationPlugin();
		List<AuthenticationUser> user = new ArrayList<>();
		user.add(new AuthenticationUser("admin", "admin", "admins"));
		authen.setUsers(user);		
		broker.setPlugins(new BrokerPlugin[]{authen});
		
		try {
			broker.addConnector(CONNECTOR_ADDR);
			broker.start();
		} catch (Exception e) {
			logger.info(e.getMessage());			
		}	
	}	
}
