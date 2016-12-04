package embedded.app;

import java.util.ArrayList;
import java.util.List;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerPlugin;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.security.AuthenticationUser;
import org.apache.activemq.security.SimpleAuthenticationPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BrokerServiceContainer implements MessageListener {

	private static final Logger logger = LoggerFactory.getLogger(BrokerServiceContainer.class);
	private static final String CONNECTOR_ADDR = "tcp://localhost:61616";

	private String brokerUrl = "tcp://localhost:61616";
	private Session session;
	private MessageProducer producer;
	private MessageConsumer consumer;
	private String requestQueue = "requests";

	public static void main(String[] args) {
		BrokerServiceContainer cont = new BrokerServiceContainer();
		BrokerService broker = new BrokerService();
		broker.setBrokerName("myBroker");
		broker.setDataDirectory("data/");

		SimpleAuthenticationPlugin authen = new SimpleAuthenticationPlugin();
		List<AuthenticationUser> user = new ArrayList<>();
		user.add(new AuthenticationUser("admin", "admin", "admins"));
		authen.setUsers(user);
		broker.setPlugins(new BrokerPlugin[] { authen });

		try {
			broker.addConnector(CONNECTOR_ADDR);
			broker.setPersistent(false);
			broker.setUseJmx(false);
			broker.start();
			cont.setupConsumer();

			System.in.read();
			cont.stop(broker);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
	}

	public void setupConsumer() throws JMSException {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);

		Connection connection;
		connection = connectionFactory.createConnection("admin", "admin");
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination adminQueue = session.createQueue(requestQueue);

		producer = session.createProducer(null);
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

		consumer = session.createConsumer(adminQueue);
		consumer.setMessageListener(this);
	}

	public void stop(BrokerService broker) throws Exception {
		producer.close();
		consumer.close();
		session.close();
		broker.stop();
	}

	public String handleRequest(String messageText) {
		return "Response to '" + messageText + "'";
	}

	@Override
	public void onMessage(Message message) {
		try {
			TextMessage response = this.session.createTextMessage();
			if (message instanceof TextMessage) {
				TextMessage txtMsg = (TextMessage) message;
				String messageText = txtMsg.getText();
				response.setText(handleRequest(messageText));
			}

			response.setJMSCorrelationID(message.getJMSCorrelationID());

			producer.send(message.getJMSReplyTo(), response);
		} catch (JMSException e) {
			
		}
	}
}
