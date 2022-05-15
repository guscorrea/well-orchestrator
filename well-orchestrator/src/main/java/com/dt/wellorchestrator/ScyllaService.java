package com.dt.wellorchestrator;

import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.dt.wellorchestrator.config.ScyllaDataSource;

public class ScyllaService {

	static ScyllaDataSource datasource = new ScyllaDataSource(new String[] { "string" }, "catalog");

	static PreparedStatement insert = datasource.getSession().prepare("INSERT INTO mutant_data (first_name,last_name,address,picture_location) VALUES (?,?,?,?)");

	static PreparedStatement delete = datasource.getSession().prepare("DELETE FROM mutant_data WHERE first_name = ? and last_name = ?");

	public void connect_and_query() {

		selectQuery();
		insertQuery("Mike", "Tyson", "12345 Foo Lane", "http://www.facebook.com/mtyson");
		insertQuery("Alex", "Jones", "56789 Hickory St", "http://www.facebook.com/ajones");
		deleteQuery("Mike", "Tyson");
		deleteQuery("Alex", "Jones");

		datasource.closeConnection();
	}

	public static void selectQuery() {
		System.out.print("\n\nDisplaying Results:");
		ResultSet results = datasource.getSession().execute("SELECT * FROM catalog.mutant_data");
		for (Row row : results) {
			String first_name = row.getString("first_name");
			String last_name = row.getString("last_name");
			System.out.print("\n" + first_name + " " + last_name);
		}
	}

	public static void insertQuery(String firstName, String lastName, String address, String pictureLocation) {
		System.out.print("\n\nInserting " + firstName + "......");
		datasource.getSession().execute(insert.bind(firstName, lastName, address, pictureLocation));
		selectQuery();
	}

	public static void deleteQuery(String firstName, String lastName) {
		System.out.print("\n\nDeleting " + firstName + "......");
		datasource.getSession().execute(delete.bind(firstName, lastName));
		selectQuery();
	}

}
