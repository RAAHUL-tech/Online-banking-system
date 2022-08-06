package bank;

/**
 * This class deposits the amount into the user's account
 */
import java.util.*;
import java.sql.*;
public class deposit {

	int accno;   //user account number
	String pass;  //user's entered password
	String pass1;  //password taken from database
	int amount;  //amount to be deposited
	int amounttb;  //amount in the account before depositing
	int newbalance;  //amount in the account after depositing
	
	deposit()
	{
		System.out.println("Enter your account no:");
		Scanner s=new Scanner(System.in);
		accno=s.nextInt();
		System.out.println("Enter your password :");
		pass=s.next();
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","root123");
			CallableStatement stmt=con.prepareCall("{call a1(?,?)}");
			stmt.setInt(1, accno);
			stmt.registerOutParameter(2,java.sql.Types.VARCHAR);
			stmt.executeUpdate();
			pass1=stmt.getString(2);
			con.close();
			if(pass.equals(pass1))  //checking the passwords
			{
				System.out.println("Enter the amount to deposit :");
				amount=s.nextInt();
				try
				{
					Class.forName("com.mysql.jdbc.Driver");
					Connection con1=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","root123");
					CallableStatement stmt1=con1.prepareCall("{call a2(?,?)}");
					/**
					 * create procedure a2(in acno int,out bal int)
					 * begin
					 * select balance into bal from customer where ac_no=acno;
					 * end //
					 */
					stmt1.setInt(1, accno);
					stmt1.registerOutParameter(2,java.sql.Types.INTEGER);
					stmt1.executeUpdate();
					amounttb=stmt1.getInt(2);
					newbalance=amounttb+amount;
					try
					{
						Class.forName("com.mysql.jdbc.Driver");
						Connection con2=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","root123");
						//updating the new amount into the database
						PreparedStatement stmt2=con2.prepareStatement("update customer set balance=? where ac_no=?");
						stmt2.setInt(1, newbalance);
						stmt2.setInt(2, accno);
						stmt2.executeUpdate();
						try
						{
							Class.forName("com.mysql.jdbc.Driver");
							Connection con3=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","root123");
							//inserting these changes in log table
							PreparedStatement stmt3=con3.prepareStatement("insert into log values(?,?,?,?)");
							stmt3.setInt(1, accno);
							stmt3.setString(2, "d");
							stmt3.setInt(3, amount);
							stmt3.setInt(4, newbalance);
							stmt3.executeUpdate();
							con3.close();
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
						con2.close();
						con1.close();
						System.out.println("Your transaction is successfull!!");
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			else
				System.out.println("Wrong account number and password...");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
