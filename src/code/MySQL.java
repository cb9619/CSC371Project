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
		String table = "Applicant ";
		String column = null;
		ResultSet rs;
		int count = 0;

		while(run)
		{
			System.out.println("1: Yes\n" + "2: No\n" + "Would you like to enter another column? ");
			int action = scan.nextInt();
			if(action == 1)
			{
				if(count > 0)
				{
					column = column + ", ";
				}
				System.out.println("1: SUID\n" +
									"2: FirstName\n" +
									"3: LastName\n" +
									"4: Email\n" +
									"5: gpa\n" +
									"6: Major\n" +
									"7: IsHired\n" +
									"8: StudentId\n" +
									"Enter a column you would like to see the result of: ");
				int value = scan.nextInt();
				if(column == null)
				{
					if(value == 1)
					{
						column = "SUID";
					}
					else if(value == 2)
					{
						column = "FirstName";
					}
					else if(value == 3)
					{
						column = "LastName";
					}
					else if(value == 4)
					{
						column = "Email";
					}
					else if(value == 5)
					{
						column = "gpa";
					}
					else if(value == 6)
					{
						column = "Major";
					}
					else if(value == 7)
					{
						column = "IsHired";
					}
					else
					{
						column = "StudentId";
					}
				}
				else
				{
					if(value == 1)
					{
						column = column + "SUID";
					}
					else if(value == 2)
					{
						column = column + "FirstName";
					}
					else if(value == 3)
					{
						column = column + "LastName";
					}
					else if(value == 4)
					{
						column = column + "Email";
					}
					else if(value == 5)
					{
						column = column + "gpa";
					}
					else if(value == 6)
					{
						column = column + "Major";
					}
					else if(value == 7)
					{
						column = column + "IsHired";
					}
					else
					{
						column = column + "StudentId";
					}
				}
				count++;
			}
			else
			{
				run = false;
			}
		}

		String query = "SELECT " + column +
						" FROM " + table +
						"WHERE gpa >= 3.00";
		rs = statement.executeQuery(query);
		System.out.println(column);
		System.out.println("=====================================================");
		while(rs.next())
		{
			for(int i = 1; i <= count; i++)
			{
				System.out.print(rs.getString(i) + " ");
			}
			System.out.print("\n");
		}
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

	private static void performDelete(Connection connection) throws SQLException
	{
		System.out.println("Enter the key of the entity you want to delete.");
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter an SUID: ");
		int id = scan.nextInt();
		System.out.println("Enter a year: ");
		int year = scan.nextInt();
		System.out.println("Enter a season: ");
		String season = scan.next();
		String query = "DELETE FROM Directs WHERE SUID = ? AND Year = ? AND Season = ?";

		PreparedStatement preparedStmt = connection.prepareStatement(query);
		preparedStmt.setInt(1, id);
		preparedStmt.setInt(2, year);
		preparedStmt.setString(3, season);
		int rowsAffected = preparedStmt.executeUpdate();
		if(rowsAffected <= 0)
		{
			System.out.println("row that meets all criteria does not exist");
		}

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
							"3: (Delete) Make it as if a student has never directed a specific orientation program.\n" +
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