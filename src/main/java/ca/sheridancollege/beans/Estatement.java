package ca.sheridancollege.beans;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Estatement {

	private int statementId;
	private String accountNumber;
	private String firstName;
	private String lastName;
	private String address;
	private String transactionDate;
	private String ammount;

	public Estatement(String accountNumber, String firstName, String lastName, String address, String transactionDate,
			String ammount) {
		this.accountNumber = accountNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.transactionDate = transactionDate;
		this.ammount = ammount;
	}

}
