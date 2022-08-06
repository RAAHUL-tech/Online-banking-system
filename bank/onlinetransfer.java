package bank;

/**
 * This class initiates all online transactions 
 */
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.*;

public class onlinetransfer {
	int acno;  //account number of user
	String pass;  //password entered by user
	String pass1;  //password in database
	int acno2;  //account number of the person to which the money is to be transferred
	int amount;  //amount to be transferred
	int amounttb1;  //users bank balance before deducting the amount
	int amounttb2;  //person2 's bank balance before depicting the amount
	int newamount1; //new balance of the user after deducting
	int newamount2;  //new balance of person2 after depicting
	
	onlinetransfer()
	{
	System.out.println("Enter your account no:");
	Scanner s=new Scanner(System.in);
	acno=s.nextInt();
	System.out.println("Enter your password :");
	pass=s.next();
	
	try
	{
		Class.forName("com.mysql.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","root123");
		CallableStatement stmt=con.prepareCall("{call a1(?,?)}");
		stmt.setInt(1, acno);
		stmt.registerOutParameter(2,java.sql.Types.VARCHAR);
		stmt.executeUpdate();
		pass1=stmt.getString(2);
		con.close();
		if(pass.equals(pass1))  //checking the passwords
		{
			System.out.println("Enter the amount to be transferred :");
			amount=s.nextInt();
			System.out.println("Enter the account no of the person2 to which the amount to be transferred :");
			acno2=s.nextInt();  //person2 account number
			try
			{
				Class.forName("com.mysql.jdbc.Driver");
				Connection con1=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","root123");
				CallableStatement stmt1=con1.prepareCall("{call a2(?,?)}");
				//procedure a2 gets the balance before making the changes
				stmt1.setInt(1, acno);
				stmt1.registerOutParameter(2,java.sql.Types.INTEGER);
				stmt1.executeUpdate();
				amounttb1=stmt1.getInt(2);
				newamount1=amounttb1-amount;
				try
				{
					Class.forName("com.mysql.jdbc.Driver");
					Connection con2=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","root123");
					CallableStatement stmt2=con2.prepareCall("{call a2(?,?)}");
					stmt2.setInt(1, acno2);
					stmt2.registerOutParameter(2,java.sql.Types.INTEGER);
					stmt2.executeUpdate();
					amounttb2=stmt2.getInt(2);
					newamount2=amounttb2+amount;
					try
					{
						Class.forName("com.mysql.jdbc.Driver");
						Connection con3=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","root123");
						//updating the balance
						PreparedStatement stmt3=con3.prepareStatement("update customer set balance=? where ac_no=?");
						stmt3.setInt(1, newamount1);
						stmt3.setInt(2, acno);
						stmt3.executeUpdate();
						try
						{
							Class.forName("com.mysql.jdbc.Driver");
							Connection con4=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","root123");
							PreparedStatement stmt4=con4.prepareStatement("insert into log values(?,?,?,?)");
							stmt4.setInt(1, acno);
							stmt4.setString(2, "o-w");
							stmt4.setInt(3, amount);
							stmt4.setInt(4, newamount1);
							stmt4.executeUpdate();
							con4.close();
							try
							{
								Class.forName("com.mysql.jdbc.Driver");
								Connection con5=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","root123");
								
								PreparedStatement stmt5=con5.prepareStatement("update customer set balance=? where ac_no=?");
								stmt5.setInt(1, newamount2);
								stmt5.setInt(2, acno2);
								stmt5.executeUpdate();
								try
								{
									Class.forName("com.mysql.jdbc.Driver");
									Connection con6=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","root123");
									
									PreparedStatement stmt6=con6.prepareStatement("insert into log values(?,?,?,?)");
									stmt6.setInt(1, acno2);
									stmt6.setString(2, "o-d");
									stmt6.setInt(3, amount);
									stmt6.setInt(4, newamount2);
									stmt6.executeUpdate();
									con6.close();
									con5.close();
									con1.close();
									con2.close();
									con3.close();
									System.out.println("Transaction is succesfull...");
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
