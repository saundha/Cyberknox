package ca.sheridancollege.controllers;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ca.sheridancollege.beans.CreditCard;
import ca.sheridancollege.beans.DebitCard;
import ca.sheridancollege.beans.User;
import ca.sheridancollege.beans.UserAccount;
import ca.sheridancollege.database.DatabaseAccess;
import ca.sheridancollege.extras.Generator;
import ca.sheridancollege.extras.Logger;
import ca.sheridancollege.extras.Transactions;

@Controller
public class UserViewController {

	@Autowired @Lazy DatabaseAccess database;
	Transactions transactions = new Transactions();
	Logger log = new Logger();
	Generator generate = new Generator();
	SimpleDateFormat SDFdate = new SimpleDateFormat("dd/MM/yyyy");

	
	@GetMapping("/account")
	public String openAccount(Model web, Authentication authentication)
			throws FileNotFoundException {
		User user = database.findUserAccount(authentication.getName());
		UserAccount userAccount = database.getUserAccountById(user.getUserAccountId());

		web.addAttribute("userAccount", userAccount);
		web.addAttribute("debit", database.getDebitById(userAccount.getDebitCardId()));
		web.addAttribute("credit", database.getCreditById(userAccount.getCreditCardId()));
		web.addAttribute("transactions", database.getAllEstatementsOfAccount(userAccount.getAccountNumber()));
		web.addAttribute("estatments", database.getEstatements(userAccount.getAccountNumber()));

		return "user/account.html";
	}

	@GetMapping("/userProfile")
	public String openUserProfile(Model web, Authentication authentication) {
		User user = database.findUserAccount(authentication.getName());
		UserAccount userAccount = database.getUserAccountById(user.getUserAccountId());

		DebitCard debit = database.getDebitById(userAccount.getDebitCardId());
		CreditCard credit = database.getCreditById(userAccount.getCreditCardId());

		String fullName = userAccount.getFirstName() +" "+ userAccount.getLastName();

		web.addAttribute("fullName", fullName);
		web.addAttribute("user", user);
		web.addAttribute("userAccount", userAccount);
		web.addAttribute("debit", debit);
		web.addAttribute("credit", credit);
		return "user/userProfile.html";
	}

}
