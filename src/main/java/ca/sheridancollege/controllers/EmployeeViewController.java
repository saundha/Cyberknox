package ca.sheridancollege.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ca.sheridancollege.beans.CreditCard;
import ca.sheridancollege.beans.DebitCard;
import ca.sheridancollege.database.DatabaseAccess;
import ca.sheridancollege.extras.Logger;

@Controller
public class EmployeeViewController {

	@Autowired @Lazy DatabaseAccess database;
	Logger log = new Logger();
	SimpleDateFormat SDFdate = new SimpleDateFormat("dd/MM/yyyy");

	
	@GetMapping("/employee")
	public String openEmployee(Authentication authentication, Model web) {
		web.addAttribute("username", authentication.getName());
		return "employee/employee.html";
	}

	@GetMapping("/userAccountListE")
	public String userAccountList(Authentication authentication, Model web) throws IOException {
		web.addAttribute("userAccounts", database.getAllUserAccounts());
		log.print(authentication.getName() +" ACCESSED the User Account List");
		return "employee/userAccountList.html";
	}

	@GetMapping("/userAccountDetailsE/{id}")
	public String userAccountDetails(@PathVariable int id,
			Authentication authentication, Model web) {
		DebitCard debit = database.getDebitById(database.getUserAccountById(id).getDebitCardId());
		CreditCard credit = database.getCreditById(database.getUserAccountById(id).getCreditCardId());
		
		web.addAttribute("debit", debit);
		web.addAttribute("credit", credit);
		web.addAttribute("user", database.getUserByUserAccountId(id));
		web.addAttribute("userAccount", database.getUserAccountById(id));
		return "employee/userAccountDetails.html";
	}
	
	@GetMapping("/editUserAccountE/{id}")
	public String editUserAccount(@PathVariable int id, Authentication authentication, Model web) {
		web.addAttribute("userAccount", database.getUserAccountById(id));
		return "employee/editUserAccount.html";
	}
	
	@GetMapping("/estatementListE")
	public String estatements(Authentication authentication, Model web) throws IOException {
		web.addAttribute("statements", database.getAllEstatements());
		log.print(authentication.getName() +" ACCESSED the estatements");
		return "employee/estatements.html";
	}
	
	@GetMapping("/employeeProfile")
	public String openEmployeeProfile(Model web, Authentication authentication) {		
		int employeeId = database.findUserAccount(authentication.getName()).getEmployeeId();
		web.addAttribute("employee", database.getEmployeeById(employeeId));
		return "employee/employeeProfile.html";
	}

}
