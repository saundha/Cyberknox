package ca.sheridancollege.beans;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DebitCard {

	private int debitCardId;
	private String number;
	private String cvv;
	private String expiryDate;
	private String balance;
	
	public DebitCard(String number, 
			String cvv, String expiryDate, String balance) {
		this.number = number;
		this.cvv = cvv;
		this.expiryDate = expiryDate;
		this.balance = balance;
	}
	
}
