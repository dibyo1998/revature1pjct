package Carservice.car_m_service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Scanner;
abstract class Details{
	protected static Connection con=null;
	protected static Scanner sc=new Scanner(System.in);
	protected String user,password,url;
	abstract void operations();
}
public class User extends Details{
	
		public void operations() {
			try {
			Class.forName("com.mysql.cj.jdbc.Driver"); //registering the driver class 1
			url="jdbc:mysql://localhost:3306/fprojectONE"; // database path 3
			user="root";// 4
			password="Qwertyuiop1!";// 5
			con=DriverManager.getConnection(url,user,password); // establish connection 2
			System.out.println("If u wnt to register, press 'y', if u r a registered user press 'n'::");
			String type=sc.next();
			if(type.toLowerCase().equals("y")==false && type.toLowerCase().equals("n")==false) {
				while(type.toLowerCase().equals("y")==false && type.toLowerCase().equals("n")==false) {
					System.out.println("If u wnt to register, press 'y', if u r a registered user press 'n'::");
					type=sc.next();
				}
			}
			if(type.toLowerCase().equals("y")==true) {
					register();
				}
			else {
				
			String email=checkuser();
			if(email.equals("0")==true) {
				System.out.println("You r not registered.. u hve to register urself");
				System.exit(0);
			}
			if(email.equals("1")==true) {
				System.out.println("Incorrect password.. if u hve forgotten ur password.. contact with us at 9051349650..");
				System.exit(0);
			}
			//else {
			//	if(email.equals("2")==true) {
					int f=0;
					while(f==0) {
						System.out.println("If u wnt to see our stock, type'1',"
								+ "if u wnt to order type '2'"
								+ " and if u want to exit press any of 3,4,5,6,7,8 or 9::");
						System.out.println("Insert choice:");
						int choice=sc.nextInt();
						switch(choice){
						case 1:
							seestock();
							break;
						
						case 2:
							order(email);
							break;
						default:
							f=1;  
							System.out.println("Thank u.. visit again:::::");
							System.exit(0);
		}}}}
			catch(Exception e) {
				e.printStackTrace();
	}}
		private static String checkuser() throws Exception{
			//List<String> email=new ArrayList<>();
			System.out.println("Enter any of your registered emailid:");
			String email=sc.next();
			
			String s1="select * from user_details where emailid=?";
			PreparedStatement pre=con.prepareStatement(s1);
			pre.setString(1, email);
			ResultSet rs=pre.executeQuery();
			int rows=0;
			while(rs.next()) {
				rows=rows+1;
			}
			if(rows==0)
				return "0";
			
			else {
				System.out.println("Enter registered password:");
				String password=sc.next();
				s1="select * from user_details where emailid=?";
				pre=con.prepareStatement(s1);
				pre.setString(1, email);
				rs=pre.executeQuery();
				int f=0;
				//while(rs.next()) {
					rs.next();
					if(rs.getString("password").equals(password)==true) {
						return email;
					}
					else {
						return "1";
					}
				//}
				
			}
			//return 0;
		}
		private static void register() throws Exception {
			System.out.println("Enter your emailid:");
			String email=sc.next();
			String s1="select * from user_details where emailid=?";
			PreparedStatement pre=con.prepareStatement(s1);
			pre.setString(1, email);
			ResultSet rs=pre.executeQuery();
			int rows=0;
			while(rs.next()) {
				rows=rows+1;
			}
			if(rows>0) {
				System.out.println("Emailid exists...");
			}
			else {
				System.out.println("Enter your first name:");
				String fname=sc.next();
				System.out.println("Enter your last name:");
				String lname=sc.next();
				String name=fname+" "+lname;
				System.out.println("Set a password:");
				String password=sc.next();
				System.out.println("Enter the same password again:");
				String password1=sc.next();
				if(password.equals(password1)==false) {
					while(password.equals(password1)==false) {
						System.out.println("Password doesn't match..");
						System.out.println("Set a new password:");
						password=sc.next();
						System.out.println("Enter the same password again:");
						password1=sc.next();
					}
				}
				System.out.println("Enter your phone number:");
				String phone=sc.next();
				s1="insert into user_details values(?,?,?,?)";
				pre= con.prepareStatement(s1);
				pre.setString(2,password);
				pre.setString(1,name);
				pre.setString(3,email);
				pre.setString(4, phone);
				rows =pre.executeUpdate();
				if(rows>0) {
					System.out.println("Record inserted succesfully to car details");
				}
				else {
					System.out.println("Record not inserted succesfully to car details");
				}
			}
		}
		private static void seestock() throws Exception{
			String s1="select distinct carname,carbrand from Car_details";
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery(s1);
			int rows=0;
			while(rs.next()) {
				rows=rows+1;
			}
			if(rows==0) {
				System.out.println("No cars available now...");
			}
			else {
			System.out.println("Available stock::");
			rs=st.executeQuery(s1);
			while(rs.next()) {
			System.out.println("CAR NAME = "+rs.getString("carname")+
					" 	CAR BRAND = "+rs.getString("carbrand"));
			}
			System.out.println("We r inviting u to visit our shop to hve more details abt our "
					+ "products.....");
			}
			}
		private static void order(String email) throws Exception{
			System.out.println("Enter Car number of the car u wnt to buy::");
			//System.out.println("Car number:");
			String no=sc.next();
			String s1="select * from car_details where carno=?";
			PreparedStatement pre=con.prepareStatement(s1);
			pre.setString(1, no);
			ResultSet rs=pre.executeQuery();
			int rows=0;
			while(rs.next()) {
				rows=rows+1;
			}
			if(rows==0){
				System.out.println("Car number does not exist.. "
						+ "for any inconvinience contact us at 9051349650");
			}
			else {
				s1="select * from orders where carno=?";
				pre=con.prepareStatement(s1);
				pre.setString(1, no);
				rs=pre.executeQuery();
				rows=0;
				while(rs.next()) {
					rows=rows+1;
				}
				s1="select * from sales where carno=?";
				pre=con.prepareStatement(s1);
				pre.setString(1, no);
				rs=pre.executeQuery();
				int rows1=0;
				while(rs.next()) {
					rows1=rows1+1;
				}
				if(rows>0 || rows1>0){
					System.out.println("Car not available for sale.. "
							+ "for any inconvinience contact us at 9051349650");
				}
				else {
			s1="insert into orders values(?,?,?)";
			pre= con.prepareStatement(s1);
			
			
			pre.setString(1,no);
			pre.setString(2,email);
			String d=LocalDateTime.now().toString();
			pre.setString(3,d);
			rows =pre.executeUpdate();
			if(rows>0) {
				System.out.println("Record inserted succesfully to orders");
			}
			else {
				System.out.println("Record not inserted succesfully from orders");
			}
			}}
		}
	}
