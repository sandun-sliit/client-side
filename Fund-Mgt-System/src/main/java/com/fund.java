package com;

import java.sql.*;

public class fund {
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/funddb",
					"root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertfund(String resID,String resName,String famount, String subject, String desc) {
		String output = "";
		System.out.println("insert method called");
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database";
			}
			// create a prepared statement
			String query = "insert into funds (fundID,resID,resName,fAmount,subject,description)" + " values (?,?,?,?,?,?)";

			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, resID);
			preparedStmt.setString(3, resName);
			preparedStmt.setString(4, famount);
			preparedStmt.setString(5, subject);
			preparedStmt.setString(6, desc);
			
			// execute the statement
			preparedStmt.execute();
			con.close();

			String newfunds = readFunds();
			output = "{\"status\":\"success\", \"data\": \"" + newfunds + "\"}";

		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while inserting the fund.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readFunds() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Researcher ID</th>" + "<th>Researcher Name</th><th>Funding Amount</th>"
					+ "<th>Subject</th>"+ "<th>Description</th>" + "<th>Update</th><th>Remove</th></tr>";
			String query = "select * from funds";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String fundID = Integer.toString(rs.getInt("fundID"));
				String resID = Integer.toString(rs.getInt("resID"));
				String resName = rs.getString("resName");
				Double amount = rs.getDouble("fAmount");
				String subject =rs.getString("subject");
				String desc = rs.getString("Description");
				// Add into the html table
				output += "<tr><td><input id='hidFundIDUpdate' name='hidFundIDUpdate' type='hidden' value='" + fundID
						+ "'>" + resID + "</td>";
				output += "<td>" + resName + "</td>";
				output += "<td>" + amount + "</td>";
				output += "<td>" + subject + "</td>";
				output += "<td>" + desc + "</td>";
				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' "
						+ "class='btnUpdate btn btn-secondary' data-itemid='" + fundID + "'></td>"
						+ "<td><input name='btnRemove' type='button' value='Remove' "
						+ "class='btnRemove btn btn-danger' data-itemid='" + fundID + "'></td></tr>";

			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the Funds.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deleteFund(String fundID) {
		System.out.println("delete method called");
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from funds where fundID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(fundID));

			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newFunds = readFunds(); 
			 output = "{\"status\":\"success\", \"data\": \"" + 
			 newFunds + "\"}"; 
			
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while deleting the fund.\"}"; 
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateFund(String fundID,String resID, String resName, String amount, String subject, String desc) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE funds SET resID=?,resName=?,famount=?,subject=?,description=? WHERE fundID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, resID);
			preparedStmt.setString(2, resName);
			preparedStmt.setDouble(3, Double.parseDouble(amount));
			preparedStmt.setString(4, subject);
			preparedStmt.setString(5, desc);
			preparedStmt.setInt(6, Integer.parseInt(fundID));

			// execute the statement
			preparedStmt.execute();
			con.close();
			String newFunds = readFunds();
			output = "{\"status\":\"success\", \"data\": \"" + newFunds + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while updating the fund.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

}