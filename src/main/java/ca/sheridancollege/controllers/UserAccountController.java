package ca.sheridancollege.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.beans.CreditCard;
import ca.sheridancollege.beans.DebitCard;
import ca.sheridancollege.beans.User;
import ca.sheridancollege.beans.UserAccount;
import ca.sheridancollege.database.DatabaseAccess;
import ca.sheridancollege.extras.Generator;
import ca.sheridancollege.extras.Logger;

@Controller
public class UserAccountController {

	@Autowired @Lazy DatabaseAccess database;
	private Logger log = new Logger();

	@PostMapping("/processSignUp")
	public String createUserAccount(Authentication authentucation,
			@ModelAttribute UserAccount userAccount,
			@RequestParam("username") String username,
			@RequestParam("password") String password) throws IOException {
		Generator generator = new Generator();

		DebitCard debit = new DebitCard(
				String.valueOf(generator.getCardNumber()),
				String.valueOf(generator.getCVV()),
				String.valueOf(generator.getExpiryDate()),
				String.valueOf(0));

		CreditCard credit = new CreditCard(
				String.valueOf(generator.getCardNumber()),
				String.valueOf(generator.getCVV()),
				String.valueOf(generator.getExpiryDate()),
				String.valueOf(1000));

		database.addDebit(debit);
		database.addCredit(credit);		

		int debitCardId = database.getDebitByNumber(debit.getNumber()).getDebitCardId();
		int creditCardId = database.getCreditByNumber(credit.getNumber()).getCreditCardId();

		userAccount.setDebitCardId(debitCardId);
		userAccount.setCreditCardId(creditCardId);
		userAccount.setAccountNumber(String.valueOf(generator.getAccountNumber()));
		database.addUserAccount(userAccount);

		int userAccountId = database.getUserAccountByDC(debitCardId, creditCardId).getUserAccountId();
		database.addUser(new User(username, password, userAccountId, 0, 0));
		database.addUserRoles(database.findUserAccount(username).getUserId(), 3);

		log.print("ADDED new User Account - id: "+ userAccountId);
		return "redirect:/account";
	}

	@GetMapping("/getAllUserAccounts")
	public String getAllUserAccounts(Model web, Authentication authentication) {
		web.addAttribute("userAccounts", database.getAllUserAccounts());
		return "employee/userAccountList.html";
	}

	@GetMapping("/details/{id}")
	public String showUserAccountDetails(@PathVariable int id, Model web) {
		web.addAttribute("userAccount", database.getUserAccountById(id));
		return "userAccountDetails.html";
	}

	@GetMapping("/edit/{id}")
	public String editUserAccount(@PathVariable int id, Model web) {
		web.addAttribute("userAccount", database.getUserAccountById(id));
		return "employee/editUserAccount.html";
	}

	@PostMapping("processEdit")
	public String executeEdit(@ModelAttribute UserAccount userAccount,
			Authentication authentication) throws IOException {
		database.updateUserAccount(userAccount);
		log.print(authentication.getName() + " EDITED the user account - id: "+ userAccount.getUserAccountId());
		return "redirect:/getAllUserAccounts";
	}

	@GetMapping("/delete/{id}")
	public String deleteUserAccount(@PathVariable int id, Model web) {
		web.addAttribute("userAccount", database.getUserAccountById(id));
		return "administrator/deleteUserAccount.html";
	}

	@PostMapping("/processDelete")
	public String executeDelete(@ModelAttribute UserAccount userAccount,
			Authentication authentication) throws IOException {
		database.deleteUserAccount(userAccount);
		log.print(authentication.getName() + " DELETED the user account - id: "+ userAccount.getUserAccountId());
		return "redirect:/getAllUserAccounts";
	}


}
