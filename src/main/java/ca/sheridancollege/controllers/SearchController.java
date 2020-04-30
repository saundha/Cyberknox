package ca.sheridancollege.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.database.DatabaseAccess;

@Controller
public class SearchController {

	@Autowired @Lazy DatabaseAccess db;
	
	@GetMapping("/searchUserAccountA")
	public String searchUserAccount(Authentication authentication, Model web,
			@RequestParam(required=false, defaultValue="0") int id,
			@RequestParam(required=false) String firstName, 
			@RequestParam(required=false) String lastName,
			@RequestParam(required=false) String address,
			@RequestParam(required=false) String accountNumber) {
		if(id!=0) {
			System.out.println("yep");
			web.addAttribute("results", db.searchDB("useraccount", "userAccountId", String.valueOf(id)));
		}
		
		if(firstName!=null) {
			web.addAttribute("results", db.searchDB("useraccount", "firstName", String.valueOf(firstName)));
		}
		
		if(lastName!=null) {
			web.addAttribute("results", db.searchDB("useraccount", "lastName", String.valueOf(lastName)));
		}
		
		if(address!=null) {
			web.addAttribute("results", db.searchDB("useraccount", "address", String.valueOf(address)));
		}

		if(accountNumber!=null) {
			web.addAttribute("results", db.searchDB("useraccount", "accountNumber", String.valueOf(accountNumber)));
		}
		return "admin/searchUserAccount.html";
	}
	
	@GetMapping("/searchEmployeeA")
	public String searchEmployee(Authentication authentication, Model web,
			@RequestParam(required=false, defaultValue="0") int id,
			@RequestParam(required=false) String firstName, 
			@RequestParam(required=false) String lastName,
			@RequestParam(required=false) String startDate) {
		if(id!=0) {
			web.addAttribute("results", db.searchDBE("employee", "employeeId", String.valueOf(id)));
		}
		
		if(firstName!=null) {
			web.addAttribute("results", db.searchDBE("employee", "firstName", String.valueOf(firstName)));
		}
		
		if(lastName!=null) {
			web.addAttribute("results", db.searchDBE("employee", "lastName", String.valueOf(lastName)));
		}
		
		if(startDate!=null) {
			web.addAttribute("results", db.searchDBE("employee", "startDate", String.valueOf(startDate)));
		}
		return "admin/searchEmployee.html";
	}
	

	@GetMapping("/searchUserAccountE")
	public String searchUserAccountE(Authentication authentication, Model web,
			@RequestParam(required=false, defaultValue="0") int id,
			@RequestParam(required=false) String firstName, 
			@RequestParam(required=false) String lastName,
			@RequestParam(required=false) String address,
			@RequestParam(required=false) String accountNumber) {
		if(id!=0) {
			System.out.println("yep");
			web.addAttribute("results", db.searchDB("useraccount", "userAccountId", String.valueOf(id)));
		}
		
		if(firstName!=null) {
			web.addAttribute("results", db.searchDB("useraccount", "firstName", String.valueOf(firstName)));
		}
		
		if(lastName!=null) {
			web.addAttribute("results", db.searchDB("useraccount", "lastName", String.valueOf(lastName)));
		}
		
		if(address!=null) {
			web.addAttribute("results", db.searchDB("useraccount", "address", String.valueOf(address)));
		}

		if(accountNumber!=null) {
			web.addAttribute("results", db.searchDB("useraccount", "accountNumber", String.valueOf(accountNumber)));
		}
		return "employee/searchUserAccount.html";
	}
	
	@GetMapping("/searchEmployeeE")
	public String searchEmployeeE(Authentication authentication, Model web,
			@RequestParam(required=false, defaultValue="0") int id,
			@RequestParam(required=false) String firstName, 
			@RequestParam(required=false) String lastName,
			@RequestParam(required=false) String startDate) {
		if(id!=0) {
			web.addAttribute("results", db.searchDBE("employee", "employeeId", String.valueOf(id)));
		}
		
		if(firstName!=null) {
			web.addAttribute("results", db.searchDBE("employee", "firstName", String.valueOf(firstName)));
		}
		
		if(lastName!=null) {
			web.addAttribute("results", db.searchDBE("employee", "lastName", String.valueOf(lastName)));
		}
		
		if(startDate!=null) {
			web.addAttribute("results", db.searchDBE("employee", "startDate", String.valueOf(startDate)));
		}
		return "employee/searchEmployee.html";
	}
	
	
}
