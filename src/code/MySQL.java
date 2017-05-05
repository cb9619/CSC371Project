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
		boolean run = true;
		Scanner scan = new Scanner(System.in);
		while(run)
		{
			System.out.println("1: Yes\n" + "2: No\n" + "A row?");
			int action = scan.nextInt();
			if (action == 1)
			{
				System.out.println("Enter an SUID in the format: #########: ");
				int SUID = scan.nextInt();
				System.out.println("Enter a name: ");
				String name = scan.next();
				System.out.println("Enter a last name: ");
				String last = scan.next();
				System.out.println("Enter an email in the format cc####: ");
				String email = scan.next();

				try
				{
					Statement stmt = connection.createStatement();
					String input = "('" + SUID + "', '" + name + "', '" + last + "', '" + email+ "')";
					String query = "INSERT INTO Professional (SUID, FirstName, LastName, Email) VALUES " + input;
					int nrows = stmt.executeUpdate(query);
					stmt.close();
				} catch (SQLException e) {
					System.out.println("Error inserting into table: Duplicate key");
				}
			}
			else if (action == 2)
			{
				run = false;
			}

		}

	}

	private static void performDelete(Connection connection)
	{

	}

	public static void main(String args[]) throws SQLException
	{
		try
		{
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
							"3: Enter a DELETE statement.\n" +
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