package ca.sheridancollege.controllers;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.beans.DebitCard;
import ca.sheridancollege.beans.Estatement;
import ca.sheridancollege.beans.User;
import ca.sheridancollege.beans.UserAccount;
import ca.sheridancollege.database.DatabaseAccess;
import ca.sheridancollege.email.EmailServiceImpl;
import ca.sheridancollege.extras.Generator;
import ca.sheridancollege.extras.Logger;

@Controller
public class EmailController {

	@Autowired
	@Lazy
	DatabaseAccess database;
	@Autowired
	EmailServiceImpl mailSender;
	Logger log = new Logger();
	Generator generate = new Generator();

	@GetMapping("/processInterac")
	public String sendMail(@RequestParam String reciever, Authentication authentication, Model web,
			@RequestParam int amount) throws MessagingException, IOException {
		int userAccountId = database.findUserAccount(authentication.getName()).getUserAccountId();
		UserAccount userAccountcc = database.getUserAccountById(userAccountId);
		String nameOfSender = userAccountcc.getFirstName() + " " + userAccountcc.getLastName();

		mailSender.sendMail(nameOfSender, String.valueOf(amount), reciever, "Test");
		User user = database.findUserAccount(authentication.getName());
		UserAccount userAccount = database.getUserAccountById(user.getUserAccountId());

		String balance = database.getDebitById(userAccount.getDebitCardId()).getBalance();

		String withdraw = String.valueOf(amount);

		if (Double.parseDouble(balance) < Double.parseDouble(withdraw)) {
			web.addAttribute("error", true);
			return "redirect:/account";
		}

		else {
			double newBal = Double.parseDouble(balance) - Double.parseDouble(withdraw);

			DebitCard debit = database.getDebitById(userAccount.getDebitCardId());
			debit.setBalance(String.valueOf(newBal));

			Estatement stmt = new Estatement(userAccount.getAccountNumber(), userAccount.getFirstName(),
					userAccount.getLastName(), userAccount.getAddress(), generate.getTransactionDate(), withdraw);

			database.updateDebitBalance(debit);
			database.addStatment(stmt);
		}
		log.print(authentication.getName() + " SENT funds to " + reciever);
		return "redirect:/account";
	}
}
