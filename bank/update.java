package bank;

/**
 * This class updates the password of a customer if they wish.
 */
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class update {

	int acno; //customer account number
	String pass;  //customer entered password
	String pass1;   //password taken from database to check 
	String newpass;  //new password
	
	update()
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
			CallableStatement stmt1=con.prepareCall("{call a1(?,?)}");
			stmt1.setInt(1, acno);
			stmt1.registerOutParameter(2,java.sql.Types.VARCHAR);
			stmt1.executeUpdate();
			pass1=stmt1.getString(2);
			con.close();
			if(pass.equals(pass1))  //checking the password
			{
				System.out.println("Enter your new password :");
				newpass=s.next();
				Class.forName("com.mysql.jdbc.Driver");
				Connection con1=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","root123");
				// changing in database
				PreparedStatement stmt=con1.prepareStatement("update customer set password=? where ac_no=?");
				stmt.setString(1, newpass);
				stmt.setInt(2, acno);
				stmt.executeUpdate();
				con1.close();
				System.out.println("Your password has been changed successfully...");
			}
			else 
				System.out.println("Wrong account no and password..");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
