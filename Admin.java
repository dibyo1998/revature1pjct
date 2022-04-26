
package Carservice.car_m_service;


import java.util.*;
import java.sql.*;
import java.time.*;
abstract class Details1{
	protected static Connection con=null;
	protected static Scanner sc=new Scanner(System.in);
	protected String user,password,url;
	//abstract void operations();
}
public class Admin extends Details1{
	
	public void operations() {
			System.out.println("Enter your password:");
			String passwrd=sc.next();
			if(passwrd.equals("H!ji2!j!@123")==false) {
				System.out.println("Wrong password...");
				System.exit(0);
			}
			try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		url="jdbc:mysql://localhost:3306/fprojectONE";
		user="root";
		password="Qwertyuiop1!";
		con=DriverManager.getConnection(url,user,password);
		int f=0;
		while(f==0) {
		System.out.println("If u wnt to insert car details, type'1',"
				+ "delete car details type '2' , "
				+ "update car details '3', "
				+ "insert sales record press 4, "
				+ "see car records press 5, see sales records press 6, see orders press 7"
				+ " and to exit press 0,8 or 9::");
		System.out.println("Insert choice:");
		int choice=sc.nextInt();
		switch(choice){
		case 1:
			insertcarRecord();
			
			break;
		
		case 2:
			deletecarRecord();
			break;
		case 3:
			updatecarRecord();
			break;
		case 4:
			insertusersalesRecord();
			break;
		case 5:
			seecardetails();
			break;
		case 6:
			seesales();
			break;
		case 7:
			seeorders();
			break;
		default:
			f=1;
			System.out.println("Exit.....:");
			System.exit(0);
		}
		}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
}
	private static void insertcarRecord() throws Exception{
		String s1="insert into Car_details values(?,?,?)";
		PreparedStatement pre= con.prepareStatement(s1);
		System.out.println("Enter Car details::");
		System.out.println("Car number:");
		String no=sc.next();
		System.out.println("Name(Do not give space in between):");
		String name=sc.next();
		System.out.println("Brand:");
		//System.out.println("hiii");
		String brand=sc.next();
		pre.setString(1,no);
		pre.setString(2,name);
		pre.setString(3,brand);
		
		int rows =pre.executeUpdate();
		if(rows>0) {
			
			System.out.println("Record inserted succesfully to car details");
			//return 1;
		}
		else {
			System.out.println("Record not inserted succesfully to car details");
			//return 2;
		}
	}
	private static void insertusersalesRecord() throws Exception{
		
		
		System.out.println("Enter car number which has been sold::");
		System.out.println("Car number:");
		String no=sc.next();
		System.out.println("Confirm the car number: "
				+ "(Note tht the record inserted into saled can't be modified");
		String no1=sc.next();
		if(no.equals(no1)==false) {
			while(no.equals(no1)==false) {
				System.out.println("Number doesn't match..");
				System.out.println("Car number:");
				no=sc.next();
				System.out.println("Confirm the car number:(Note tht the record inserted into saled can't be modified");
				no1=sc.next();
			}
		}
		String s1="select * from orders where carno=?";
		PreparedStatement pre= con.prepareStatement(s1);
		pre.setString(1,no);
		ResultSet rs=pre.executeQuery();
		rs.next();
		String email=rs.getString("emailid");
		//System.out.println("Brand:");
		//System.out.println("hiii");
		String date=LocalDateTime.now().toString();
		 s1="insert into sales values(?,?,?)";
		 pre= con.prepareStatement(s1);
		pre.setString(2,no);
		pre.setString(1,email);
		pre.setString(3,date);
		//pre.setInt(4,sales);
		int rows =pre.executeUpdate();
		if(rows>0) {
			System.out.println("Record inserted succesfully to sales");
		}
		else {
			System.out.println("Record not inserted succesfully to sales");
		}
		s1="delete from orders where carno=?";
		pre= con.prepareStatement(s1);
		pre.setString(1,no);
		rows =pre.executeUpdate();
		if(rows>0) {
			System.out.println(no+" deleted succesfully from orders");
		}
		else {
			System.out.println(no+" not deleted succesfully from orders");
		}
	}
	private static void updatecarRecord() throws Exception{
		System.out.println("Enter car number for which u wnt to update");
		String no=sc.next();
		System.out.println("Enter new car name");
		String name=sc.next();
		System.out.println("Enter new car brand");
		String brand=sc.next();
		String sq1="update Car_details set carname=?, carbrand=? where carno=?";
		PreparedStatement pq =con.prepareStatement(sq1);
		pq.setString(1, name);
		pq.setString(2,brand);
		pq.setString(3,no);
		int rows =pq.executeUpdate();
		if(rows>0) {
			System.out.println("Record updated succesfully in car details");
		}
		else {
			System.out.println("Record not updated succesfully in car details");
		}
	}
	private static void deletecarRecord() throws Exception{
		System.out.println("Enter the Car number whose record should be deleted:");
		String no=sc.next();
		String s1="select * from orders where carno=?";
		PreparedStatement pre= con.prepareStatement(s1);
		pre.setString(1,no);
		ResultSet rs=pre.executeQuery();
		int f=0;
		while(rs.next()) {
			if(no.equals(rs.getString("carno"))==true) {
				f=1;
				break;
			}
		}
		if(f==1) {
			System.out.println("Cannot delete this record...");
		}
		else {
		 s1="delete from Car_details where carno=?";
		 pre= con.prepareStatement(s1);
		pre.setString(1,no);
		int rows =pre.executeUpdate();
		if(rows>0) {
			System.out.println(no+" deleted succesfully from car details");
		}
		else {
			System.out.println(no+" not deleted succesfully from car details");
		}
	}
	}
	private static void seecardetails() throws Exception{
		String s1="select * from Car_details";
		Statement st=con.createStatement();
		ResultSet rs=st.executeQuery(s1);
		int rows=0;
		while(rs.next()) {
			rows=rows+1;
		}
		if(rows==0) {
			System.out.println("No records r there...");
		}
		else {
		System.out.println("CARS::");
		rs=st.executeQuery(s1);
		while(rs.next()) {
		System.out.println("Car number = "+rs.getString("carno")+" 	Car name = "+rs.getString("carname")+" 	Brand = "+rs.getString("carbrand"));
		}
		}
	}
	private static void seeorders() throws Exception{
		String s1="select * from orders";
		Statement st=con.createStatement();
		ResultSet rs=st.executeQuery(s1);
		int rows=0;
		while(rs.next()) {
			rows=rows+1;
		}
		if(rows==0) {
			System.out.println("No records r there...");
		}
		else {
		
		 rs=st.executeQuery(s1);
		System.out.println("Orders pending::");
		while(rs.next()) {
			System.out.println("Car ordered = "+rs.getString("carno")+" 	User ordered= "+rs.getString("emailid")+" 	Date = "+rs.getString("date_of_order"));

		}
		}
	}
	private static void seesales() throws Exception{
		String s1="select * from sales";
		Statement st=con.createStatement();
		ResultSet rs=st.executeQuery(s1);
		int rows=0;
		while(rs.next()) {
			rows=rows+1;
		}
		if(rows==0) {
			System.out.println("No records r there...");
		}
		else {
			rs=st.executeQuery(s1);
		System.out.println("Sales made::");
		while(rs.next()) {
			System.out.println("Car bought = "+rs.getString("carno")+" 	User bought= "+rs.getString("emailid")+" 	Date = "+rs.getString("date_of_purchase"));
		}
		}
	}
}
