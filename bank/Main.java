package bank;

/**
 * @author Raahul krishna
 * Date of start of project : 04-08-2022
 * Date of completion of project : 06-08-2022
 * Date last modified/updated : 06-08-2022
 * 
 * Topic of project : Online Banking System CUM ATM
 * 
 * Technologies used : Java,JDBC(Java Database Connectivity),My-SQL,Maria database
 * 
 *   This application is a Online banking system where it can also act as a ATM.It can 
 *   do online transactions and also allows user to check the balance ,withdraw the
 *   amount and deposit amount into their account.Users can also apply for loan and by checking the
 *   loan criteria(annual income>=100000) we give loan to the users.The loan
 *   amount is depicted to the users account.
 *   
 *   In online transactions by giving the account number of person2 we transfer the specified
 *   amount to them securely.
 *   
 *   All the transactions are secured by checking the account number the passwords of the user
 *   periodically.
 *   
 *   All the transactions made are also stored in log table which can be viewed only by the
 *   bank mangers.
 *   
 *   We also allow users to update their password(if they want) at any time.
 *   
 *   If the transaction is successful we get a Success message else we get a error message 
 *   and no changes in the balance is made.
 */
import java.util.*;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int choice,choice2;
		try {
		Scanner s=new Scanner(System.in);
         System.out.println("Welcome to SBI...");
         while(true)
         {
        	 System.out.println("Enter 1 if your are customer and 2 if you are a employee and 3 to exit...");
        	 choice=s.nextInt();
        	 if(choice==1)
        	 {
        		 System.out.println("Enter 1 for existing customer, 2 for new customer and 3 for exit :");
        		 choice2=s.nextInt();
        		 if(choice2==1)
        		 {
        			 System.out.println("Enter 1 to check balance,2 to withdraw,3 to deposit,4 to update password, \n5 for transeferring money online,6 for taking loan and 7 for exit :");
        			 int choice3=s.nextInt();
        			 if(choice3==1)
        			 {
        				 balance b=new balance();
        			 }
        			 else if(choice3==2)
        			 {
        				 withdraw w=new withdraw();
        			 }
        			 else if(choice3==3)
        			 {
        				 deposit d=new deposit();
        			 }
        			 else if(choice3==4)
        			 {
        				update u=new update(); 
        			 }
        			 else if(choice3==5)
        			 {
        				 onlinetransfer o=new onlinetransfer();
        			 }
        			 else if(choice3==6)
        			 {
        				 loan l=new loan();
        			 }
        			 else 
        				 System.exit(0);
        		 }
        		 else if(choice2==2)
        		 {
        			 newuser n=new newuser();
        		 }
        		 else
        			 System.exit(0);
        	 }
        	 else if(choice==2)
        	 {
        		 employee e=new employee();
        	 }
        	 else 
        		 System.exit(0);
         }
		} catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
