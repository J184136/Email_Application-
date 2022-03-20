import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

@SuppressWarnings("unused")
public class Email_Application  {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		Connect c=new Connect();
		Connection con=c.getConnection();
	    Statement stat = con.createStatement();
	   // ResultSet res = stat.executeQuery("select * from user_detials");
	    Actions obj = new Actions();
		@SuppressWarnings("resource")
		Scanner scan= new Scanner(System.in);
		//Page1
		String page1="Page1";
		while(!page1.equals("")) {
			System.out.println();
		System.out.println("Enter create for creating a new account ");
		System.out.println("Enter login for login into a existing  account ");
		System.out.println("Enter stop to Stop the system ");
		String val=scan.nextLine().toLowerCase();
		if(val.equals("create")) {
			// create a account
			//Page2
			obj.create(con);
			//created so go to Page1
		}
		else if(val.equals("login")) {
			//Page2
			String page2="Page2";
			while(!page2.equals("")) {
				System.out.println();
			System.out.println("Enter Email Id like this _@gmail.com");
			String Email_id=scan.nextLine().toLowerCase();
			System.out.println("Enter Password ");
			String Password=scan.nextLine().toLowerCase();
			//Check  email Id and Password
			String nextStep="";
			if(Email_id.contains("@gmail.com")) {
		      nextStep=obj.Check(con,Email_id,Password);
			}
			else {
				System.out.println("Email id should be like this _@gmail.com");
			}
		    if(nextStep!=null) {
			if(nextStep.equals("Logined")) {
				//Page3
				String page3="Page3";
				while(!page3.equals("")) {
					System.out.println();
				System.out.println("Enter send to Send a mail");
				System.out.println("Enter show to Show all Sended  mails");
				System.out.println("Enter recived to Show all Recived  mails");
				System.out.println("Enter stared to Show all Stared  mails");
				System.out.println("Enter draft to Show all Draft  mails");
				System.out.println("Enter change to Change the Username");
				System.out.println("Enter logout to Logout");
				String SecondStep=scan.nextLine().toLowerCase();
				if(SecondStep.equals("send")) {
					obj.SendMail(con,Email_id);
					// go to Page3
				}
				else if(SecondStep.equals("show")) {
					obj.ShowMail(con,Email_id,stat);
					// go to Page3
				}
				else if(SecondStep.equals("recived")) {
					obj.RecivedMail(con,Email_id,stat);
					// go to Page3
				}
				else if(SecondStep.equals("stared")) {
					obj.StaredMail(con,Email_id);
					// go to Page3
				}
				else if(SecondStep.equals("draft")) {
				    obj.DraftMail(con,Email_id,stat);
				 // go to Page3
				}
				else if(SecondStep.equals("change")) {
					obj.ChangeUsername(con,Email_id);
					// go to Page3
				}
				else if(SecondStep.equals("logout")) {
					page2="";
					page3="";
					System.out.println("Logout!!!!!!");
					// go to Page1
					
				}
				else {
					System.out.println("Wrong Input");
				}
				}
				
			}
			else if(nextStep.equals("WrongPass")) {
				obj.ChangePassword(con,Email_id);
				// go to Page2
			}
		    }
			else {
				page2="";
				// go to Page1
			}
			}
		}
		else if(val.equals("stop")) {
			page1="";
			System.out.println("Stoped Working!!!!");
		}
		else {
			System.out.println("Wrong Input");
		}
		}
	}

}
