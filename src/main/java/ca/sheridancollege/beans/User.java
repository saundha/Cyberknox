package ca.sheridancollege.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

	private int userId;
	private String username;
	private String password;
	private int userAccountId;
	private int employeeId;
	private int adminId;
	
	public User(String username, String password, int userAccountId, int employeeId, int adminId) {
		super();
		this.username = username;
		this.password = password;
		this.userAccountId = userAccountId;
		this.employeeId = employeeId;
		this.adminId = adminId;
	}
	
	
}
