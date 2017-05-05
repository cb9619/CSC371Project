package code;

import java.sql.*;
import java.util.Scanner;

public class MySQL
{
	private static void performSelect(Connection connection) throws SQLException
	{
		Statement statement = connection.createStatement();
		Scanner scan = new Scanner(System.in);
		boolean run = true;
		String table = "Applicant";
		String column = null;
		ResultSet rs;

		while(run)
		{
			System.out.println("1: Yes\n" + "2: No\n" + "Would you like to enter another column? ");
			int action = scan.nextInt();
			if(action == 1)
			{
				System.out.println("1: SUID\n" +
									"2: FirstName\n" +
									"3: LastName\n" +
									"4: Email\n" +
									"5: gpa\n" +
									"6: Major\n" +
									"7: IsHired\n" +
									"8: StudentId\n" +
									"Enter a column you would like to see the result of: ");
				int count = 0;
				int columns[] = null;
				columns[count] = scan.nextInt();
				count++;
			}
			else
			{
				run = false;
			}
		}

		//need to loop through and figure out the columns the user wants

		String query = "SELECT " + column +
						"FROM " + table +
						"WHERE gpa >= 3.00";
	}

	private static void performInsert(Connection connection)
	{

	}

	private static void performDelete(Connection connection) throws SQLException
	{
		Scanner scan = new Scanner(System.in);
		int id = scan.nextInt();;
		String query = "DELETE FROM Directs WHERE SUID = ?";


		PreparedStatement preparedStmt = connection.prepareStatement(query);
		preparedStmt.setInt(1, id);
		int rowsAffected = preparedStmt.executeUpdate();
		if(rowsAffected <= 0)
		{
			System.out.println(id+" does not exist");
		}

	}

	public static void main(String args[]) throws SQLException
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e)
			{
			System.out.println ("Could not load driver.\n");
			}
		boolean run = true;
		String url = "jdbc:mysql://db.cs.ship.edu/csc371-15";
		String username = "csc371-15";
		String password = "12345";
		Connection connection = DriverManager.getConnection(url, username, password);

		while(run)
		{
			Scanner scan = new Scanner(System.in);
			System.out.println("1: Enter a SELECT statement.\n" +
							"2: Enter a INSERT INTO statement.\n" +
							"3: (Delete) Make it as if a student has never directed an orientation program.\n" +
							"4: To exit program.\n" +
							"Enter a number to execute command: ");
			int action = scan.nextInt();


			if(action == 1)
			{
				performSelect(connection);
			}
			else if(action == 2)
			{
				performInsert(connection);
			}
			else if(action == 3)
			{
				performDelete(connection);
			}
			else
			{
				run = false;
			}
		}
		connection.close();
	}
}