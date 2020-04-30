package ca.sheridancollege.beans;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data		
public class Receipt {

	private int receiptId;
	private String bankName = "CYBER KNOX";
	private String address = "7899 McLaughlin Rd, Brampton, ON L6Y 5H9";
	private String reference;
	private String cardNumber;
	private String amount;
	private String accountBalance;
	
	public Receipt(String reference, String cardNumber, String amount,
			String accountBalance) {
		this.reference = reference;
		this.cardNumber = cardNumber;
		this.amount = amount;
		this.accountBalance = accountBalance;
	}
	
}
