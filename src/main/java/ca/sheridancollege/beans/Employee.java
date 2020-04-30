package ca.sheridancollege.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Employee {

	private int employeeId;
	private String firstName;
	private String lastName;
	private String startDate;
	
	public Employee(String firstName, String lastName, String startDate) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.startDate = startDate;
	}
	
}
