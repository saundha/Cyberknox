package ca.sheridancollege.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.github.javafaker.Faker;

import ca.sheridancollege.beans.CreditCard;
import ca.sheridancollege.beans.DebitCard;
import ca.sheridancollege.beans.Employee;
import ca.sheridancollege.beans.User;
import ca.sheridancollege.beans.UserAccount;
import ca.sheridancollege.database.DatabaseAccess;
import ca.sheridancollege.extras.DummyRecords;
import ca.sheridancollege.extras.Generator;

@Controller
public class RootController {

	@Autowired @Lazy DatabaseAccess database;
	Generator generate = new Generator();

	@GetMapping("/")
	public String bootUp() {
		return "home.html";
	}

	@GetMapping("/generate")
	public String generate() {
		for (int i=0; i<10; i++) {
			DummyRecords dr = new DummyRecords();
			UserAccount userAcc = dr.generateRecords();
			UserAccount userD = new UserAccount();
			userD.setFirstName(userAcc.getFirstName());
			userD.setLastName(userAcc.getLastName());
			userD.setDateOfBirth(userAcc.getDateOfBirth());
			userD.setAddress(userAcc.getAddress());
			userD.setGender(userAcc.getGender());
			userD.setNumber(userAcc.getNumber());
			userD.setEmail(userAcc.getEmail());
			userD.setAge(userAcc.getAge());
			userD.setSinNumber(userAcc.getSinNumber());
			userD.setAccountNumber(userAcc.getAccountNumber());

			System.out.println(userAcc);
			long cardNum = 1000000000000000L + (long)(Math.random() * 9999999999999999L);
			int cvv = 100 + (int)(Math.random() * 999);

			DebitCard debit = new DebitCard(
					String.valueOf(cardNum),
					String.valueOf(cvv),
					String.valueOf("2018-10-10"),
					String.valueOf(0));
			database.addDebit(debit);

			//long cardNumCC = 1000000000000000L + (int)(Math.random() * 9999999999999999L);
			int cvvCC = 100 + (int)(Math.random() * 999);
			Faker fake = new Faker();
			CreditCard credit =  new CreditCard(
					String.valueOf(fake.number().numberBetween(1000000000000000L, 9999999999999999L)),
					String.valueOf(cvvCC),
					String.valueOf("2018-10-10"),
					String.valueOf(10000));
			database.addCredit(credit);

			int debitCardId = (database.getDebitByNumber(debit.getNumber())).getDebitCardId();
			int creditCardId = (database.getCreditByNumber(credit.getNumber())).getCreditCardId();

			userD.setDebitCardId(debitCardId);
			userD.setCreditCardId(creditCardId);

			String fname = userAcc.getFirstName();
			String lname = userAcc.getLastName();

			database.addUserAccount(userD);
			System.out.println(userAcc.getAccountNumber());

			int userAccountId = database.getUserAccountByNum(userD.getAccountNumber()).getUserAccountId();

			database.addUser(new User(fname+lname, "root", userAccountId, 0, 0));
			database.addUserRoles(database.findUserAccount(fname+lname).getUserId(), 3);
			
			Employee newEmp = dr.generateEmp();
			database.addEmployee(new Employee(newEmp.getFirstName(), newEmp.getLastName(), newEmp.getStartDate()));

			int employeeId = database.getEmployeeByName(newEmp.getFirstName(), newEmp.getLastName()).getEmployeeId();
			
			database.addUser(new User(newEmp.getFirstName()+newEmp.getLastName(), "root", 0, employeeId, 0));
			database.addUserRoles(database.findUserAccount(newEmp.getFirstName()+newEmp.getLastName()).getUserId(), 2);
		}
		database.addUser(new User("saund", "root", 0, 0, 1));
		database.addUserRoles(database.findUserAccount("saund").getUserId(), 1);
		database.addUser(new User("panesar", "root", 0, 0, 1));
		database.addUserRoles(database.findUserAccount("panesar").getUserId(), 1);
		database.addUser(new User("penava", "root", 0, 0, 1));
		database.addUserRoles(database.findUserAccount("penava").getUserId(), 1);
		return "home.html";
	}

	@GetMapping("/login")
	public String openLogin() {
		return "login.html";
	}

	@GetMapping("/signup")
	public String openSignup(Model web) {
		web.addAttribute("userAccount", new UserAccount());
		return "signup.html";
	}	

	@GetMapping("/access-denied")
	public String openAccessDenied() {
		return "access-denied.html";
	}

	@GetMapping("/addAdmin/{username}/{password}")
	public String add(@PathVariable String username, @PathVariable String password) {
		database.addUser(new User(username, password, 0,0,0));
		database.addUserRoles(database.findUserAccount(username).getUserId(), 1);
		return "redirect:/";
	}

}
