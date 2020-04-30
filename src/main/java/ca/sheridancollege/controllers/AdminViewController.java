package ca.sheridancollege.controllers;

import java.io.FileNotFoundException;
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
import ca.sheridancollege.beans.Employee;
import ca.sheridancollege.database.DatabaseAccess;
import ca.sheridancollege.extras.Logger;

@Controller
public class AdminViewController {

	@Autowired @Lazy DatabaseAccess database;
	Logger log = new Logger();
	SimpleDateFormat SDFdate = new SimpleDateFormat("dd/MM/yyyy");


	@GetMapping("/administrator")
	public String enterAdministrator(Model web,
			Authentication authentication) {
		web.addAttribute("username", authentication.getName());
		return "admin/administrator.html";
	}

//  USER ACCOUNT CRUD ------------------------------------------------------------	
	
	@GetMapping("/userAccountListA")
	public String showUserAccounts(Model web) {
		web.addAttribute("userAccounts", database.getAllUserAccounts());
		return "admin/userAccountList.html";
	}

	@GetMapping("/userAccountDetailsA/{id}")
	public String showDetails(@PathVariable int id, Model web,
			Authentication authentication) {
		DebitCard debit = database.getDebitById(database.getUserAccountById(id).getDebitCardId());
		CreditCard credit = database.getCreditById(database.getUserAccountById(id).getCreditCardId());
		
		web.addAttribute("debit", debit);
		web.addAttribute("credit", credit);
		web.addAttribute("user", database.findUserAccount(authentication.getName()));
		web.addAttribute("userAccount", database.getUserAccountById(id));
		return "admin/userAccountDetails.html";
	}
	
	@GetMapping("/editUserAccountA/{id}")
	public String editUserAccount(@PathVariable int id, Model web) {
		web.addAttribute("userAccount", database.getUserAccountById(id));
		return "admin/editUserAccount.html";
	}
	
	@GetMapping("/deleteUserAccountA/{id}")
	public String deleteUserAccount(@PathVariable int id, Model web) {
		web.addAttribute("userAccount", database.getUserAccountById(id));
		return "admin/deleteUserAccount.html";
	}
	
//  EMPLOYEE CRUD ----------------------------------------------------------------

	@GetMapping("/hireEmployeeA")
	public String hireEmployee(Model web) {
		web.addAttribute("employee", new Employee());
		return "admin/hireEmployee.html";
	}

	@GetMapping("/employeeListA")
	public String getAllEmployees(Model web) {
		web.addAttribute("employees", database.getAllEmployees());
		return "admin/employeeList.html";
	}

	@GetMapping("/editEmployeeA/{id}")
	public String openEditEmployee(@PathVariable int id, Model web) {
		web.addAttribute("employee", database.getEmployeeById(id));
		return "admin/editEmployee.html";
	}
	
	@GetMapping("/deleteEmployeeA/{id}")
	public String openDeleteEmployee(@PathVariable int id, Model web) {
		web.addAttribute("employee", database.getEmployeeById(id));
		return "admin/deleteEmployee.html";
	}
	
//  EXTRAS ---------------------------------------------------------------------

	@GetMapping("/estatementListA")
	public String openStatments(Model web) {
		web.addAttribute("statements", database.getAllEstatements());
		return "admin/estatements.html";
	}

	@GetMapping("/logListA")
	public String showLogs(Model web) throws FileNotFoundException {
		web.addAttribute("logList", log.getLogs());
		return "admin/logs.html";
	}

	@GetMapping("/clearLogsA")
	public String clearLogs() throws IOException {
		log.clear();
		return "redirect:/logListA";
	}
	
	@GetMapping("/getMeLogs")
	public String theLogs() throws FileNotFoundException {
		return log.getLogs().toString();
	}
	

}
