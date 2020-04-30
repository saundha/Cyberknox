package ca.sheridancollege.extras;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import com.github.javafaker.Faker;

import ca.sheridancollege.beans.Employee;
import ca.sheridancollege.beans.UserAccount;


enum fname
{
	f0("Jervis"),
	f1("Gino"),f2("Shaun"),f3("Yahya"),f4("Joana"),f5("Amedeo"),
	f6("Brendan"),f7("Andre"),f8("Cara"),f9("Ailish"),f10("Carina");
	
	String firstName;

	private fname(String firstName) {
		this.firstName = firstName;
	}
	
	String getFirstName()
	{
		return this.firstName;
	}
	
}
enum lname
{
	l0("Bridges"),
	l1("Clarke"),l2("Valanzuela"),l3("Watkins"),l4("George"),l5("Kumar"),
	l6("Saund"),l7("Panesar"),l8("Benipal"),l9("Oberoi"),l10("Bangar");
	
	String lastName;

	private lname(String lastName) {
		this.lastName = lastName;
	}
	
	String getlastName()
	{
		return this.lastName;
	}
	
}
enum dateOfBirth
{
	d0("2000-11-01"),d1("1988-11-01"),d2("1993-12-01"),d3("1999-09-14"),d4("2001-01-25")
	,d5("2000-04-11"),d6("1987-07-22"),d7("2000-03-08"),d8("2001-12-01"),d9("2004=01-12")
	,d10("2005-10-01");
	
	private String date;

	private dateOfBirth(String date) {
		this.date = date;
	}

	public String getDate() {
		return date;
	}

}
enum address
{
	a0("1146  Nelson Street"),a1("3362  rue St-Henri"),a2("2175  Port Washington Road"),
	a3("4430  Wascana Parkway"),a4("2072  Jones Road"),a5("4543  Ross Terrasse"),
	a6("1565  Toy Avenue"),a7("772  Bank St"),a8("3678  Sixth Street"),a9("1087  Dufferin Street"),
	a10("3058  Old House Drive");
	private String address;

	public String getAddress() {
		return address;
	}

	private address(String address) {
		this.address = address;
	}

}
enum gender
{
	g0("Male"), g1("Female"), g2("Rather not say");
	private String gen;

	public String getGen() {
		return gen;
	}

	private gender(String gen) {
		this.gen = gen;
	}
}
public class DummyRecords {
	
	public UserAccount generateRecords()
	{
		Random rr = new Random();
		Faker fake = new Faker();
		String firstName = fname.valueOf("f"+rr.nextInt(11)).getFirstName();
		String lastName = lname.valueOf("l"+rr.nextInt(11)).getlastName();
		String dob = dateOfBirth.valueOf("d"+rr.nextInt(11)).getDate();
		String add = address.valueOf("a"+rr.nextInt(11)).getAddress();
		String gen = gender.valueOf("g"+rr.nextInt(2)).getGen();
		String phone = String.valueOf(fake.phoneNumber().cellPhone());
		String email = firstName+lastName+"@something.com";
		String age = String.valueOf(rr.nextInt(80));
		String sinNum = String.valueOf(fake.number().numberBetween(100000000L, 999999999L));
		String accNum = String.valueOf(1000000000L + (long)(Math.random() * 9999999999L));
		
		UserAccount userAcc = new UserAccount(firstName,lastName, dob, add, gen,
				 phone,  email,  age,  sinNum,  accNum);
				return userAcc;
	}
	public Employee generateEmp()
	{
		Random rr = new Random();
		String firstName = fname.valueOf("f"+rr.nextInt(11)).getFirstName();
		String lastName = lname.valueOf("l"+rr.nextInt(11)).getlastName();
		 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");  
		   LocalDateTime now = LocalDateTime.now();  
		   System.out.println(dtf.format(now)); 
		Employee emp = new Employee(firstName, lastName, dtf.format(now));
		
		return emp;
		
	}

}
