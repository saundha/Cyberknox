package ca.sheridancollege.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.beans.DebitCard;
import ca.sheridancollege.beans.Employee;
import ca.sheridancollege.beans.Estatement;
import ca.sheridancollege.beans.Receipt;
import ca.sheridancollege.beans.User;
import ca.sheridancollege.beans.UserAccount;
import ca.sheridancollege.database.DatabaseAccess;
import ca.sheridancollege.extras.Generator;
import ca.sheridancollege.extras.Logger;
import ca.sheridancollege.extras.Transactions;

@Controller
public class ProcessController {
    
    @Autowired @Lazy DatabaseAccess database;
    Transactions transactions = new Transactions();
    Logger log = new Logger();
    Generator generate = new Generator();
    SimpleDateFormat SDFdate = new SimpleDateFormat("dd/MM/yyyy");
    
//  EMPLOYEE --------------------------------------------------------------------
    
	@GetMapping("/processHireEmployeeA")
	public String processHireEmployee(@ModelAttribute Employee employee,
			@RequestParam String username, @RequestParam String password,
			Authentication authentication) throws IOException {	
		String startDate = SDFdate.format(new Date());
		employee.setStartDate(startDate);
		database.addEmployee(employee);

		int employeeId = database.getEmployeeByName(employee.getFirstName(), employee.getLastName()).getEmployeeId();
		
		database.addUser(new User(username, password, 0, employeeId, 0));
		database.addUserRoles(database.findUserAccount(username).getUserId(), 2);
		
		log.print(authentication.getName() +" ADDED new employee: "+ employee.getFirstName());
		return "redirect:/administrator";
	}
	
	@GetMapping("/processEditEmployeeA")
	public String processEditEmployee(@ModelAttribute Employee employee,
			Authentication authentication) throws IOException {
		database.updateEmployee(employee);
		log.print(authentication.getName() +" EDITED employee: "+ employee.getFirstName());
		return "redirect:/employeeListA";
	}
	
	@GetMapping("/processDeleteEmployeeA")
	public String processDeleteEmployee(@ModelAttribute Employee employee,
			Authentication authentication) throws IOException {
		database.deleteEmployee(employee);
		log.print(authentication.getName() +" DELETED employee: "+ employee.getFirstName());
		return "redirect:/employeeListA";
	}
	
	@GetMapping("/processEditUserAccountE")
	public String processEditUserAccountE(@ModelAttribute UserAccount userAccount,
			Authentication authentication) throws IOException {
		database.updateUserAccount(userAccount);
		log.print(authentication.getName() + " EDITED account: " + userAccount.getFirstName());
		return "redirect:/userAccountListE";
	}
	
	@GetMapping("/processEditEmployeeProfile")
	public String processEditEmployeeProfile(@ModelAttribute Employee employee) {
		database.updateEmployee(employee);
		return "redirect:/employee";
	}


//  USER ACCOUNT ---------------------------------------------------------------------	

	@GetMapping("/processEditUserAccountA")
	public String processEditUserAccount(@ModelAttribute UserAccount userAccount,
			Authentication authentication) throws IOException {
		database.updateUserAccount(userAccount);
		log.print(authentication.getName() + " EDITED account: " + userAccount.getFirstName());
		return "redirect:/userAccountListA";
	}
	
	@GetMapping("/processDeleteUserAccountA")
	public String processDeleteUserAccount(@ModelAttribute UserAccount userAccount,
			Authentication authentication) throws IOException {
		database.deleteUserAccount(userAccount);
		log.print(authentication.getName() + " DELETED account: " + userAccount.getFirstName());
		return "redirect:/userAccountListA";
	}

	@PostMapping("/processDeposit")
	public String depositAmount(@RequestParam String deposit,
			Authentication authentication, Model web) throws IOException {
		User user = database.findUserAccount(authentication.getName());
		UserAccount userAccount = database.getUserAccountById(user.getUserAccountId());

		String balance = database.getDebitById(userAccount.getDebitCardId()).getBalance();
		double newBal = Double.parseDouble(balance) + Double.parseDouble(deposit);
		DebitCard debit = database.getDebitById(userAccount.getDebitCardId());
		debit.setBalance(String.valueOf(newBal));

		Estatement stmt = new Estatement(
				userAccount.getAccountNumber(),
				userAccount.getFirstName(),
				userAccount.getLastName(), 
				userAccount.getAddress(),
				generate.getTransactionDate(),
				deposit);

		database.updateDebitBalance(debit);
		database.addStatment(stmt);

		Receipt rec = new Receipt(
				String.valueOf(generate.getCardNumber()),
				String.valueOf(debit.getNumber()),
				String.valueOf(deposit),
				String.valueOf(debit.getBalance()));
		System.out.println(rec.getBankName());
		web.addAttribute("receipt", rec);
		log.print(authentication.getName() + " deposited " + deposit);
		return "receipt.html";
	}

	@PostMapping("processWithdraw")
	public String withdrawAmount(@RequestParam String withdraw,
			Authentication authentication, Model web) throws IOException {
		User user = database.findUserAccount(authentication.getName());
		UserAccount userAccount = database.getUserAccountById(user.getUserAccountId());

		String balance = database.getDebitById(userAccount.getDebitCardId()).getBalance();

		if (Double.parseDouble(balance) < Double.parseDouble(withdraw)) {
			web.addAttribute("error", true);
			return "redirect:/account";
		}

		else {
			double newBal = Double.parseDouble(balance) - Double.parseDouble(withdraw);

			DebitCard debit = database.getDebitById(userAccount.getDebitCardId());
			debit.setBalance(String.valueOf(newBal));

			Estatement stmt = new Estatement(
					userAccount.getAccountNumber(),
					userAccount.getFirstName(),
					userAccount.getLastName(), 
					userAccount.getAddress(),
					generate.getTransactionDate(),
					withdraw);

			database.updateDebitBalance(debit);
			database.addStatment(stmt);

			Receipt rec = new Receipt(
					String.valueOf(generate.getCardNumber()),
					String.valueOf(debit.getNumber()),
					String.valueOf(withdraw),
					String.valueOf(debit.getBalance()));
			System.out.println(rec.getBankName());
			web.addAttribute("receipt", rec);
			log.print(authentication.getName() + " withdrawed " + withdraw);
			return "receipt.html";
		}
	}

    
}
