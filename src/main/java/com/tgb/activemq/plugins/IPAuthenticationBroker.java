package com.tgb.activemq.plugins;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerFilter;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.command.ConnectionInfo;

public class IPAuthenticationBroker extends BrokerFilter {

	List<String> allowedIPAddresses;
	Pattern pattern = Pattern.compile("^([0-9\\.]*):(.*)");

	public IPAuthenticationBroker(Broker next, List<String> allowedIPAddresses) {
		super(next);
		this.allowedIPAddresses = allowedIPAddresses;
	}

	@Override
	public void addConnection(ConnectionContext context, ConnectionInfo info) throws Exception {
		String ipaddress = context.getConnection().getRemoteAddress();
		ipaddress = ipaddress.split("://")[1];
		Matcher matcher = pattern.matcher(ipaddress);
		if (matcher.matches()) {
			String ip = matcher.group(1);
			if (!allowedIPAddresses.contains(ip)) {
				throw new SecurityException("Connecting from IP address " + ip + " is not allowed");
			}
		} else {
			throw new SecurityException("Invalid remote address " + ipaddress);
		}
		
		super.addConnection(context, info);
	}
}
