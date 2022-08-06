package bank;

/**
 * This class adds new user to bank database by getting their name,date-of-birth and password.
 * the account number of the new user is generated by the program and is given to the user at the end.
 * by using this acc no and password he can withdraw/deposit amount in his account.
 */
import java.util.*;
import java.sql.*;
public class newuser {
	
	String name;   //user name
	String dob;   //user date-of-birth
	int acno;    //account numbers in the database
	int acno1;   //acno+1
	int amount;  //amount to be deposited
	String password;  //password
	newuser()
	{
		System.out.println("Enter your name :");
		Scanner s=new Scanner(System.in);
		name=s.next();
		System.out.println("Enter your dob:");
		dob=s.next();
		System.out.println("Enter a password :");
		password=s.next();
		System.out.println("Enter the amount to be deposited on your new account :");
		amount=s.nextInt();
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","root123");
			CallableStatement stmt=con.prepareCall("{call a3(?)}");
			/**
			 * create procedure a3(out acno int)
			 * begin
			 * select count(*) into acno from customer;
			 * end //
			 */
			stmt.registerOutParameter(1,java.sql.Types.INTEGER);
			stmt.executeUpdate();
			acno1=stmt.getInt(1);
			acno=acno1+1;
			try
			{
				Class.forName("com.mysql.jdbc.Driver");
				Connection con1=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","root123");
				// inserting new user record in the database.
				PreparedStatement stmt1=con1.prepareStatement("insert into customer values(?,?,?,?,?)");
				stmt1.setInt(1, acno);
				stmt1.setString(2, name);
				stmt1.setString(3, dob);
				stmt1.setInt(4, amount);
				stmt1.setString(5, password);
				stmt1.executeUpdate();
			    System.out.println("Account created...");
			    System.out.println("Your account no is "+ acno);
			    con1.close();
			    con.close();
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

}
