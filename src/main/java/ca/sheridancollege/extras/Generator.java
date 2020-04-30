package ca.sheridancollege.extras;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import com.github.javafaker.Faker;

import ca.sheridancollege.beans.CreditCard;
import ca.sheridancollege.beans.DebitCard;
import ca.sheridancollege.beans.User;
import ca.sheridancollege.beans.UserAccount;
import ca.sheridancollege.database.DatabaseAccess;

public class Generator {

	@Autowired @Lazy DatabaseAccess database;
	Faker fake = new Faker();	
	SimpleDateFormat SDFdate = new SimpleDateFormat("dd/MM/yyyy");

	public Long getAccountNumber() {
		return fake.number().numberBetween(1000000000L, 9999999999L);
	}

	public Long getSinNumber() {
		return fake.number().numberBetween(100000000L, 999999999L);
	}

	public Long getCardNumber() {
		return fake.number().numberBetween(1000000000000000L, 9999999999999999L);
	}

	public int getCVV() {
		return fake.number().numberBetween(100, 999);
	}

	@SuppressWarnings("deprecation")
	public String getExpiryDate() {
		Date from = new Date();
		from.setDate(1);
		from.setMonth(1);
		from.setYear(2025);

		Date to = new Date();
		to.setDate(30);
		to.setMonth(12);
		to.setYear(2030);

		return String.valueOf(fake.date().between(from, to));
	}

	public String getTransactionDate() {
		return SDFdate.format(new Date());
	}

	public DebitCard getDebit() {
		return new DebitCard(
				String.valueOf(getCardNumber()),
				String.valueOf(getCVV()),
				String.valueOf(getExpiryDate()),
				String.valueOf(10000));
	}

	public CreditCard getCredit() {
		return new CreditCard(
				String.valueOf(getCardNumber()),
				String.valueOf(getCVV()),
				String.valueOf(getExpiryDate()),
				String.valueOf(10000));
	}


	//  USER ACCOUNT --------------------------------------------------------

	public void userAccount() {    	
		DummyRecords dr = new DummyRecords();
		UserAccount userAcc = dr.generateRecords(); 

		long cardNum = 1000000000000000L + (long)(Math.random() * 9999999999999999L);
		int cvv = 100 + (int)(Math.random() * 999);

		DebitCard debit = new DebitCard(
				String.valueOf(cardNum),
				String.valueOf(cvv),
				String.valueOf("2018-10-10"),
				String.valueOf(10000));
		
		cardNum = 1000000000000000L + (int)(Math.random() * 9999999999999999L);
		cvv = 100 + (int)(Math.random() * 999);

		CreditCard credit =  new CreditCard(
				String.valueOf(cardNum),
				String.valueOf(cvv),
				String.valueOf("2018-10-10"),
				String.valueOf(10000));
		
		database.addDebit(new DebitCard(
				String.valueOf(cardNum),
				String.valueOf(cvv),
				String.valueOf("2018-10-10"),
				String.valueOf(10000)));
		database.addCredit(credit);

		int debitCardId = database.getDebitByNumber(debit.getNumber()).getDebitCardId();
		int creditCardId = database.getCreditByNumber(credit.getNumber()).getCreditCardId();

		String fname = userAcc.getFirstName();
		String lname = userAcc.getLastName();

		database.addUserAccount(dr.generateRecords());

		int userAccountId = database.getUserAccountByDC(debitCardId,creditCardId).getUserAccountId();

		database.addUser(new User(fname+lname, "root", userAccountId, 0, 0));
		database.addUserRoles(database.findUserAccount(fname+lname).getUserId(), 3);
	}

	public void administrator() {
		database.addUser(new User("jon", "root", 0, 0, 0));
		database.addUserRoles(database.findUserAccount("jon").getUserId(), 1);
	}

}
