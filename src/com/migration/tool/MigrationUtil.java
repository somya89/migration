package com.migration.tool;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class MigrationUtil {
	public static void main(String[] argv) {
		Statement stmt = null;
		BufferedReader br = null;

		System.out.println("-------- MySQL JDBC Connection Testing ------------");

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your MySQL JDBC Driver?");
			e.printStackTrace();
			return;
		}

		System.out.println("MySQL JDBC Driver Registered!");
		Connection connection = null;

		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/postest", "postest", "pos123");

		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}

		if (connection != null) {
			System.out.println("You made it, take control your database now!");
			// STEP 4: Execute a query
			System.out.println("Creating statement...");
			try {
				stmt = connection.createStatement();
				String sCurrentLine;

				br = new BufferedReader(new FileReader("script.sql"));

				while ((sCurrentLine = br.readLine()) != null) {
					if (!sCurrentLine.trim().isEmpty()) {
						System.out.println(sCurrentLine);
						stmt.execute(sCurrentLine);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "An Error has occured.Please send the file progress.log", "InfoBox: " + "Error",
						JOptionPane.ERROR_MESSAGE);
			}
			JOptionPane.showMessageDialog(null, "Success. Please proceed to start POS", "InfoBox: " + "Success", JOptionPane.INFORMATION_MESSAGE);
		} else {
			System.out.println("Failed to make connection!");
		}
	}
}
