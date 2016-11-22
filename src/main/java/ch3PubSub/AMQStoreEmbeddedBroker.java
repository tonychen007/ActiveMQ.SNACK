package ch3PubSub;

import org.apache.activemq.broker.BrokerService;

@SuppressWarnings("all")
public class AMQStoreEmbeddedBroker {
    
    public static void createEmbeddedBroker() throws Exception {        
        BrokerService broker = new BrokerService();
        broker.addConnector("tcp://localhost:61616");        
        broker.start();
    }
    
    public static void main(String[] args) throws Exception {
    	createEmbeddedBroker();
	}
}
