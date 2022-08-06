package bank;

/**
 * This class is used to check the balance in the account
 */
import java.util.*;
import java.sql.*;
public class balance {

	int accno;  //account number of the user
	String pass;  //password entered by the user
	String pass1;  //password taken from database
	balance()
	{
		System.out.println("Enter your account no:");
		Scanner s=new Scanner(System.in);
		accno=s.nextInt();
		System.out.println("Enter your password :");
		pass=s.next();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","root123");
			/**
			 *  create procedure a1(IN accno int,OUT i varchar(10))
                -> begin
                -> select password into i from customer where ac_no=accno;
                -> end//
			 */
			CallableStatement stmt1=con.prepareCall("{call a1(?,?)}");
			stmt1.setInt(1, accno);
			stmt1.registerOutParameter(2,java.sql.Types.VARCHAR);
			stmt1.executeUpdate();
			pass1=stmt1.getString(2);
			con.close();
			if(pass.equals(pass1))  //checking the passwords
			{
				try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection con1=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","root123");
				//getting the balance from database
				PreparedStatement stmt=con1.prepareStatement("select * from customer where ac_no=?");
				stmt.setInt(1, accno);
				ResultSet rs=stmt.executeQuery();
				while(rs.next())
				{
					System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getInt(4));
				}
				con1.close();
				}
				catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			else
				System.out.println("Wrong account no and password..");
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}	
}
