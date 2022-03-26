import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Actions extends Connect{

	String FirstName;
	String LastName;
	String Email_id;
	int Phone_Number;
	int APhone_Number;
	String Password;
	Scanner scan= new Scanner(System.in);

	@SuppressWarnings("unused")
	public void create(Connection con) throws SQLException{
		//Page4
		String page4="page4";
		while(!page4.equals("")) {
			System.out.println();
		System.out.println("Enter FirstName ");
		String FirstName=scan.nextLine();
		System.out.println("Enter LastName ");
		String LastName=scan.nextLine();
		System.out.println("Enter Email Id like this _@gmail.com");
		String Email_id=scan.nextLine().toLowerCase();
		System.out.println("Enter Phone Number ");
		int Phone_Number=scan.nextInt();
		System.out.println("Enter Altername Phone Number ");
		int APhone_Number=scan.nextInt();
		System.out.println("Enter Password ");
		String Password=scan.nextLine().toLowerCase();
		System.out.println("Enter Confirm Password ");
		String CPassword=scan.nextLine().toLowerCase();
		if(Password.equals(CPassword)) {
		//insert sql command l
			page4=insert(FirstName, LastName, Email_id, Phone_Number, APhone_Number, Password, con);
		}
		else {
			System.out.print("Password and Confirm Password are not same ");
			System.out.println();
			// go to Page4
		} 
		}
	}
public String insert(String FirstName, String LastName, String Email_id, int Phone_Number, int APhone_Number,
		String Password, Connection con) throws SQLException {
	// TODO Auto-generated method stub
	//insert sql command 
	String page4="page4";
	int Count=0;
	int Count2=0;
	int Count3=0;
	this.FirstName=FirstName;
	this.LastName=LastName;
	this.APhone_Number=APhone_Number;
	this.Password=Password;
	this.Email_id=Email_id;
	this.Phone_Number=Phone_Number;
	PreparedStatement ps = con.prepareStatement("insert into user_detials  values (?,?,?,?,?,?)");
	Statement stat = con.createStatement();
    ResultSet res = stat.executeQuery("select * from user_detials");
	for(;res.next();) {
		if(Email_id.equals(res.getString(3))) {
			Count=1;
			break;
		}
	if(Phone_Number==res.getInt(4)) {
			Count2=1;
			Count=2;
			break;
		}
	}
	if(!Email_id.contains("@gmail.com")) {
		Count3=1;
		Count=2;
	}
	if(Count==0) {
		ps.setString(1, FirstName);
		ps.setString(2, LastName);
		ps.setString(3, Email_id);
		ps.setInt(4,Phone_Number);
		ps.setInt(5,APhone_Number);
		ps.setString(6, Password);
		ps.executeUpdate();
		System.out.println("Account Created!!!!!!!! ");
		page4="";
		return page4;
	}
	else if(Count==1) {
		System.out.println("This Email id is already Exists plz enter another Email id");
		// go to Page4
		return page4;
	}
	if(Count3==1) {
		System.out.println("Email id should be like this _@gmail.com");
		// go to Page4
		return page4;
	}
	 if(Count2==1) {
		System.out.println("This Phone_Number  is already Exists plz enter another Phone_Number ");
		// go to Page4
		return page4;
	}
	 return page4;
}

public String Check(Connection con,String Email_id,String Password) throws SQLException{
	this.Email_id=Email_id;
	this.Password=Password;
	Statement stat = con.createStatement();
    ResultSet res = stat.executeQuery("select * from user_detials");
	int Count=1;
	String nextStep=null;
	for(;res.next();) {
	if(Email_id.equals(res.getString(3))) {
		// select password regarding to the mail id 
		if(Password.equals(res.getString(6))) {
			System.out.println("Login Successfully...... ");
			System.out.println("WelCome!!!!!!  "+res.getString(1)+" "+res.getString(2));
			System.out.println();
			nextStep="Logined";
			Count=0;
			break;
		}
		else {
			System.out.println("Wrong Password!!!!");
			nextStep="WrongPass";
			Count=0;
			break;
		}
	}
	}

	if(Count!=0) {
		System.out.println("You don't have any Accout !!!!");
		System.out.println("Create a new Account  !!!!");
		// go to Page1
	}
	return nextStep;
}


public void SendMail(Connection con,String Email_id) throws SQLException {
	this.Email_id=Email_id;
	System.out.println("Enter the mail id to which u want to send mail like this _@gmail.com");
	String To = scan.nextLine().toLowerCase();
	System.out.println("Enter Subject");
	String Subject = scan.nextLine();
	System.out.println("Enter the Content of mail");
	String Content = scan.nextLine();
	if(To.contains("@gmail.com") || To.equals("")) {
	System.out.println("Do u want to send mail or save as draft?? ");
	System.out.println("Enter send to send and draft to save as draft ");
	String whatToDo= scan.nextLine().toLowerCase();
	if(whatToDo.equals("send")) {
		if(!To.equals("") && !Content.equals("")) {
			if(Subject.equals("")) {
				System.out.println("Do u want to send the Mail without Subject ?? Enter yes or no");
				String permission= scan.nextLine().toLowerCase();
				if(permission.equals("yes")) {
					PreparedStatement ps = con.prepareStatement("insert into emails (mail_id,To_send,email_subject,Content)  values (?,?,?,?)");
					ps.setString(1, Email_id);
					ps.setString(2, To);
					ps.setString(3, Subject);
					ps.setString(4, Content);
					ps.execute();
					System.out.println("Mail Sended!!!!");
					// go to Page3
				}
				else {
					System.out.println("Enter Subject");
					Subject = scan.nextLine();
					PreparedStatement ps = con.prepareStatement("insert into emails (mail_id,To_send,email_subject,Content)  values (?,?,?,?)");
					ps.setString(1, Email_id);
					ps.setString(2, To);
					ps.setString(3, Subject);
					ps.setString(4, Content);
					ps.execute();
					System.out.println("Mail Sended!!!!");
					// go to Page3
				}
			}
		}
		else {
			PreparedStatement ps = con.prepareStatement("insert into draft_emails (mail_id,To_send,email_subject,Content)  values (?,?,?,?)");
			ps.setString(1, Email_id);
			ps.setString(2, To);
			ps.setString(3, Subject);
			ps.setString(4, Content);
			ps.execute();
			System.out.println("Sender Mail or Content Should not be Blank Mail Save As Draft!!!!");
			// go to Page3
		}
	}
	else if(whatToDo.equals("draft")) {
		PreparedStatement ps = con.prepareStatement("insert into draft_emails (mail_id,To_send,email_subject,Content)  values (?,?,?,?)");
		ps.setString(1, Email_id);
		ps.setString(2, To);
		ps.setString(3, Subject);
		ps.setString(4, Content);
		ps.execute();
		System.out.println("Mail Save As Draft!!!!");
		// go to Page3
	}
	else {
		System.out.println("Wrong Input");
		// go to Page3
	}
	}
     else {
				System.out.println("Sender id should be like this _@gmail.com");
			}
	}


public void ShowMail(Connection con,String Email_id,Statement stat) throws SQLException {
	this.Email_id=Email_id;
	PreparedStatement ps = con.prepareStatement("select * from emails where mail_id=?");
	ps.setString(1, Email_id);
	ResultSet res = ps.executeQuery();
	int blank=0;
	for(;res.next();) {
		blank=res.getRow();
		System.out.println(res.getString(6)+"   "+res.getInt(1)+" From: "+res.getString(2)+"  To: "+res.getString(3)+"  Subject: "+res.getString(4)+"  Content: "+res.getString(5));
	}
	res.close();
	ps.close();
	System.out.println();
	if(blank!=0) {
	System.out.println("Do u want to Mark any Mail as Important?? Enter yes or no");
	String permission= scan.nextLine().toLowerCase();
	if(permission.equals("yes")) {
	System.out.println();
	System.out.println("Enter mail number which u want to mark as important ");
	String mark=scan.nextLine();
	String[] arrOfmark = mark.split(",");
	for(String m : arrOfmark) {
		int store=Integer.parseInt(m);
		
	PreparedStatement ps2 = con.prepareStatement("Update emails set Starred='**' where mail_id= ? and id=?");
	this.Email_id=Email_id;
	ps2.setString(1, Email_id);
	ps2.setInt(2, store);
	ps2.executeUpdate();
	}
	System.out.println("Marked...");
	// go to Page3
	}
	}
	else {
		System.out.println("till now no mail has been Send");
	}
}

public void StaredMail(Connection con,String Email_id) throws SQLException {
	PreparedStatement ps = con.prepareStatement("select * from emails where mail_id=? and Starred='**'");
	ps.setString(1, Email_id);
	ResultSet res = ps.executeQuery();
	int blank=0;
	for(;res.next();) {
		blank=res.getRow();
		System.out.println("   "+res.getString(6)+"   "+res.getInt(1)+" From: "+res.getString(2)+"  To: "+res.getString(3)+"  Subject: "+res.getString(4)+"  Content: "+res.getString(5));
	}
	res.close();
	ps.close();
	System.out.println();
	if(blank!=0) {
	System.out.println("Do u want to UnMark any Mail ?? Enter yes or no");
	String permission= scan.nextLine().toLowerCase();
	if(permission.equals("yes")) {
	System.out.println();
	System.out.println("Enter mail number which u don't want to mark as important ");
	String mark=scan.nextLine();
	String[] arrOfmark = mark.split(",");
	for(String m : arrOfmark) {
		int store=Integer.parseInt(m);
	PreparedStatement ps2 = con.prepareStatement("Update emails set Starred=' ' where mail_id= ? and id=?");
	this.Email_id=Email_id;
	ps2.setString(1, Email_id);
	ps2.setInt(2, store);	
	ps2.executeUpdate();
	}
	System.out.println("UnMarked...");
	// go to Page3
	}
	}
	else {
		System.out.println("till now no mail has been Marked");
	}
}

public void RecivedMail(Connection con,String Email_id,Statement stat) throws SQLException {
	PreparedStatement ps = con.prepareStatement("select * from emails where To_send=?");
	this.Email_id=Email_id;
	ps.setString(1, Email_id);
	ResultSet res = ps.executeQuery();
	int blank=0;
	for(;res.next();) {
		blank=res.getRow();
		System.out.println("  "+res.getString(6)+" To Me: "+res.getString(3)+"  From: "+res.getString(2)+"  Subject: "+res.getString(4)+"  Content: "+res.getString(5));
	}
	ps.close();
	res.close();
	System.out.println();
	if(blank==0) {
		System.out.println("till now no mail has been Recived");
	}
	// go to Page3
}

public void DraftMail(Connection con,String Email_id,Statement stat) throws SQLException {
	String To="",Content="",Subject="";
	this.Email_id=Email_id;
	PreparedStatement ps = con.prepareStatement("select * from draft_emails where mail_id=?");
	ps.setString(1, Email_id);
	ResultSet res = ps.executeQuery();
	int blank=0;
	for(;res.next();) {
		blank=res.getRow();
		System.out.println("   "+res.getInt(1)+" From: "+res.getString(2)+"  To: "+res.getString(3)+"  Subject: "+res.getString(4)+"  Content: "+res.getString(5));
	}
	System.out.println();
	if(blank!=0) {
	System.out.println("Do u want to send any Mail?? Enter yes or no");
	String permission= scan.nextLine().toLowerCase();
	if(permission.equals("yes")) {
		System.out.println("Enter mail number which u to sent ");
		int mark=scan.nextInt();
		ps.close();
		res.close();
		PreparedStatement ps2 = con.prepareStatement("select * from draft_emails where mail_id=? and id=?");
		ps2.setString(1, Email_id);
		ps2.setInt(2, mark);
		ResultSet res2 = ps2.executeQuery();
		for(;res2.next();) {
		if(res2.getString(3).isEmpty()) {
			System.out.println("Enter the mail id to which u want to send mail like this _@gmail.com");
		    @SuppressWarnings("unused")
		    To = scan.nextLine().toLowerCase();
		}
		else {
			To=res2.getString(3);
		}
	    if(res2.getString(5).isEmpty()) {
			System.out.println("Enter the Content of mail");
			Content = scan.nextLine();
		}
	    else {
	    	Content=res2.getString(5);
	    }
	    Subject=res2.getString(4);
			System.out.println("   "+res2.getInt(1)+" From: "+res2.getString(2)+"  To: "+To+"  Subject: "+Subject+"  Content: "+Content);
		}
		System.out.println();
		ps2.close();
		res2.close();
		//Page6
		String page6="Page6";
		while(!page6.equals("")) {
			System.out.println();
		System.out.println("Do u want to edit?? Enter yes or no");
		String permission2= scan.nextLine().toLowerCase();
		if(permission2.equals("yes")) {
			System.out.println("What u want to Change: content ,subject or to");
			String what = scan.nextLine().toLowerCase();
			while(!what.isEmpty()) {
				switch(what){
				case "to":
					System.out.println("Enter the mail id to which u want to send mail like this _@gmail.com");
				     To = scan.nextLine().toLowerCase();
				     break;
				case "subject":
					System.out.println("Enter Subject");
					Subject = scan.nextLine();
					break;
				case "content":
					System.out.println("Enter the Content of mail");
					Content = scan.nextLine();
					break;
			   default:
				   System.out.println("Wrong Input");
				   
				}
				  what="";
			}
		}
		else {
			page6="";
		}
		}
		if(!To.equals("") && !Content.equals("") && To.contains("@gmail.com")) {
			ps2.close();
			res2.close();
			if(Subject.equals("")) {
				System.out.println("Do u want to send the Mail without Subject ?? Enter yes or no");
				String permissionP= scan.nextLine().toLowerCase();
				if(permissionP.equals("yes")) {

					PreparedStatement ps3 = con.prepareStatement("insert into emails (mail_id,To_send,email_subject,Content)  values (?,?,?,?)");
					ps3.setString(1, Email_id);
					ps3.setString(2, To);
					ps3.setString(3, Subject);
					ps3.setString(4, Content);
					ps3.execute();
					System.out.println("Mail Sended!!!!");
					ps3.close();
					PreparedStatement ps4 = con.prepareStatement("DELETE FROM draft_emails WHERE mail_id=? and id=?");
					ps4.setString(1, Email_id);
					ps4.setInt(2, mark);
					ps4.executeUpdate();
					ps4.close();
			}
				else {
					System.out.println("Enter Subject");
					Subject = scan.nextLine();
					PreparedStatement ps3 = con.prepareStatement("insert into emails (mail_id,To_send,email_subject,Content)  values (?,?,?,?)");
					ps3.setString(1, Email_id);
					ps3.setString(2, To);
					ps3.setString(3, Subject);
					ps3.setString(4, Content);
					ps3.execute();
					System.out.println("Mail Sended!!!!");
					ps3.close();
					PreparedStatement ps4 = con.prepareStatement("DELETE FROM draft_emails WHERE mail_id=? and id=?");
					ps4.setString(1, Email_id);
					ps4.setInt(2, mark);
					ps4.executeUpdate();
					ps4.close();
					}
				}
			else {
			PreparedStatement ps3 = con.prepareStatement("insert into emails (mail_id,To_send,email_subject,Content)  values (?,?,?,?)");
			ps3.setString(1, Email_id);
			ps3.setString(2, To);
			ps3.setString(3, Subject);
			ps3.setString(4, Content);
			ps3.execute();
			System.out.println("Mail Sended!!!!");
			ps3.close();
			PreparedStatement ps4 = con.prepareStatement("DELETE FROM draft_emails WHERE mail_id=? and id=?");
			ps4.setString(1, Email_id);
			ps4.setInt(2, mark);
			ps4.executeUpdate();
			ps4.close();

			}
				}
			else {
			System.out.println("Not Sended!!!!");
		}
	}
	}
	else {
		System.out.println("till now no mail has been save as draft");
	}
	// go to Page3
}
public void ChangeUsername(Connection con,String Email_id) throws SQLException {
	//Statement stat = con.createStatement();
	PreparedStatement ps = con.prepareStatement("Update user_detials set FirstName= ? , LastName= ? where Email_id= ?");
	this.Email_id=Email_id;
	System.out.println("Enter FirstName ");
	String FirstName2=scan.nextLine();
	System.out.println("Enter LastName ");
	String LastName2=scan.nextLine();
	ps.setString(1, FirstName2);
	ps.setString(2, LastName2);
	ps.setString(3, Email_id);
	int rowsChanged= ps.executeUpdate();
	if(rowsChanged>0) {
		System.out.println("UserName Changed");
	}
	// go to Page3
}
public void  ChangePassword(Connection con,String Email_id) throws SQLException {
	System.out.println("Do u want to Change your Password ?? Enter yes or no");
	String permission= scan.nextLine().toLowerCase();
	if(permission.equals("yes")) {
		//Page5
		String page5="Page5";
		while(!page5.equals("")) {
			System.out.println();
	System.out.println("Enter New Password ");
	String Password=scan.nextLine().toLowerCase();
	System.out.println("Enter Confirm Password ");
	String CPassword=scan.nextLine().toLowerCase();
	if(Password.equals(CPassword)) {
	PreparedStatement ps = con.prepareStatement("Update user_detials set Password= ? where Email_id= ?");
	this.Email_id=Email_id;
	ps.setString(1, Password);
	ps.setString(2, Email_id);
	ps.executeUpdate();
	System.out.print("Password Changed!!!!!!");
	// go to Page2
	page5="";
	}
	else {
		System.out.print("Password and Confirm Password are not same ");
		// go to Page5
	}
		}
	}
}
//go to Page2

}
