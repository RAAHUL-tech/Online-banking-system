package bank;

/**
 * This class is invoked when user needs loan in the bank
 */
import java.util.*;
import java.sql.*;
public class loan {
	
	int acno;  //user account number
	String pass;  //user's entered password
	String pass1; //password taken from database
	int loanno;  //loan number
	int loanno1;  //loan numbers in database
	int loanamount;  //loan amount
	int amount;  //balance in user's account before adding loan amount
	int newamount; //balance after adding loan amount
	int anin;  //annual income
	
	loan()
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
			if(pass.equals(pass1))  //checking password
			{
				System.out.println("Enter your anual income :");
				anin=s.nextInt();
				if(anin>=100000)  //loan checking criteria
				{
					System.out.println("Enter your loan amount :");
					loanamount=s.nextInt();
					try
					{
						Class.forName("com.mysql.jdbc.Driver");
						Connection con1=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","root123");
						CallableStatement stmt1=con1.prepareCall("{call a5(?)}");
						stmt1.registerOutParameter(1,java.sql.Types.INTEGER);
						stmt1.executeUpdate();
						loanno1=stmt1.getInt(1);
						loanno=loanno1+1;
						try
						{
							Class.forName("com.mysql.jdbc.Driver");
							Connection con2=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","root123");
							PreparedStatement stmt2=con2.prepareStatement("insert into loan values(?,?,?)");
							//inserting loan details in loan table
							stmt2.setInt(1, acno);
							stmt2.setInt(2, loanno);
							stmt2.setInt(3, loanamount);
							stmt2.executeUpdate();
							try
							{
								Class.forName("com.mysql.jdbc.Driver");
								Connection con3=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","root123");
								CallableStatement stmt3=con3.prepareCall("{call a2(?,?)}");
								stmt3.setInt(1, acno);
								stmt3.registerOutParameter(2,java.sql.Types.INTEGER);
								stmt3.executeUpdate();
								amount=stmt3.getInt(2);
								newamount=loanamount+amount;
								try
								{
									Class.forName("com.mysql.jdbc.Driver");
									Connection con4=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","root123");
									//updating the new amount into the database
									PreparedStatement stmt4=con4.prepareStatement("update customer set balance=? where ac_no=?");
									//updating the balance
									stmt4.setInt(1, newamount);
									stmt4.setInt(2, acno);
									stmt4.executeUpdate();
									try
									{
										Class.forName("com.mysql.jdbc.Driver");
										Connection con5=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","root123");
										//inserting these changes in log table
										PreparedStatement stmt5=con5.prepareStatement("insert into log values(?,?,?,?)");
										stmt5.setInt(1, acno);
										stmt5.setString(2, "l-d");
										stmt5.setInt(3, loanamount);
										stmt5.setInt(4, newamount);
										stmt5.executeUpdate();
										System.out.println("Your loan has been approved...");
										System.out.println("Your loan number is : "+ loanno);
										con5.close();
										con4.close();
										con3.close();
										con2.close();
										con1.close();
										
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
					System.out.println("Sorry your anual income is too low...");
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
