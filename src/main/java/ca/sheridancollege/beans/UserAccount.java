package ca.sheridancollege.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAccount {

	private int userAccountId;
	private String FirstName;
	private String LastName;
	private String dateOfBirth;
	private String address;
	private String gender;
	private String number;
	private String email;
	private String age;
	private String SinNumber;
	private String AccountNumber;
	private int debitCardId;
	private int creditCardId;
	
	public UserAccount(String firstName, String lastName, String dateOfBirth, String address, String gender,
			String number, String email, String age, String sinNumber, String accountNumber, int debitCardId,
			int creditCardId) {
		FirstName = firstName;
		LastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		this.gender = gender;
		this.number = number;
		this.email = email;
		this.age = age;
		SinNumber = sinNumber;
		AccountNumber = accountNumber;
		this.debitCardId = debitCardId;
		this.creditCardId = creditCardId;
	}

	public UserAccount(String firstName, String lastName, String dateOfBirth, String address, String gender,
			String number, String email, String age, String sinNumber, String accountNumber) {
		super();
		FirstName = firstName;
		LastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		this.gender = gender;
		this.number = number;
		this.email = email;
		this.age = age;
		SinNumber = sinNumber;
		AccountNumber = accountNumber;
	}
	
}
