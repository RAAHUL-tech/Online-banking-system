package bank;

/**
 * This class is for employees working in the bank (Manager) who can see the customer details
 * and log files
 */
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class employee {
	
	int id;   //admin id
	String pass;  //admin's entered password
	String pass1;  //password taken from database for checking
	
	employee()
	{
		System.out.println("Enter your id :");
		Scanner s=new Scanner(System.in);
		id=s.nextInt();
		System.out.println("Enter your password :");
		pass=s.next();
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","root123");
			CallableStatement stmt=con.prepareCall("{call a4(?,?)}");
			/**
			 * create procedure a4(in id int,out pass varchar(10))
			 * begin
			 * select password into pass from employee where id=id;
			 * end //
			 */
			stmt.setInt(1, id);
			stmt.registerOutParameter(2,java.sql.Types.VARCHAR);
			stmt.executeUpdate();
			pass1=stmt.getString(2);
			con.close();
			if(pass.equals(pass1))  //checking passwords
			{
				System.out.println("Enter 1 to see the customer list and 2 for log list and 3 for loan list and 4 for exit...");
				int choice=s.nextInt();
				if(choice==1)   //viewing customer details
				{
					Class.forName("com.mysql.jdbc.Driver");
					Connection con1=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","root123");
					Statement stmt2=con1.createStatement();
					ResultSet rs=stmt2.executeQuery("select * from customer");
					while(rs.next())
					{
						System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getInt(4));
					}
					con1.close();
					
				}
				else if(choice==2)  //viewing log details
				{
					Class.forName("com.mysql.jdbc.Driver");
					Connection con2=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","root123");
					Statement stmt2=con2.createStatement();
					ResultSet rs=stmt2.executeQuery("select * from log");
					while(rs.next())
					{
						System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getInt(3)+" "+rs.getInt(4));
					}
					con2.close();
				}
				else if(choice==3) //viewing loan details
				{
					Class.forName("com.mysql.jdbc.Driver");
					Connection con2=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","root123");
					Statement stmt2=con2.createStatement();
					ResultSet rs=stmt2.executeQuery("select * from loan");
					while(rs.next())
					{
						System.out.println(rs.getInt(1)+" "+rs.getInt(2)+" "+rs.getInt(3));
					}
					con2.close();
				}
				else
					System.exit(0);
			}
			else
				System.out.println("Wrong id and password...");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

}
