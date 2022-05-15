package com.dt.wellorchestrator.config;

import java.net.InetSocketAddress;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Session;

public class ScyllaDataSource {

	String[] contactPoints; // List of node addresses to which client drivers tries to connect
	String keyspace; // Database whose tables have similar configurations.
	Cluster cluster; // Cluster library provides connection to a cluster.
	Session session; // Session library provides the connection to a specific keyspace.

	public ScyllaDataSource(String[] contactPoints, String keyspace) {
		super();
		this.contactPoints = contactPoints;
		this.keyspace = keyspace;
		createConnection();
	}

	public void createConnection() {
		cluster = Cluster.builder()
				.addContactPointsWithPorts(
						new InetSocketAddress("127.0.0.1", 9042),
						new InetSocketAddress("127.0.0.1", 9043),
						new InetSocketAddress("127.0.0.1", 9044))
				.build();

		session = cluster.newSession();

		session.execute("CREATE KEYSPACE IF NOT EXISTS catalog WITH REPLICATION = { 'class' : 'NetworkTopologyStrategy','DC1' : 3};");
		session.execute(
				"CREATE TABLE IF NOT EXISTS catalog.mutant_data (first_name text, last_name text, address text, picture_location text, "
						+ "PRIMARY KEY((first_name, last_name)));");


		session = cluster.connect(keyspace);

		System.out.println("Connected to cluster " + cluster.getMetadata().getClusterName());

		for (Host host : cluster.getMetadata().getAllHosts()) {
			System.out.printf("Datacenter: %s, Host: %s, Rack: %s\n", host.getDatacenter(), host.getEndPoint(), host.getRack());
		}

		System.out.println("Connected to cluster " + cluster.getClusterName());
	}

	public Session getSession() {
		return session;
	}

	public void closeConnection() {
		session.close();
		cluster.close();
	}

}
