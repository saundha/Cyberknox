package ca.sheridancollege.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.beans.CreditCard;
import ca.sheridancollege.beans.DebitCard;
import ca.sheridancollege.beans.Employee;
import ca.sheridancollege.beans.Estatement;
import ca.sheridancollege.beans.User;
import ca.sheridancollege.beans.UserAccount;

@Repository
public class DatabaseAccess {
	
	@Autowired NamedParameterJdbcTemplate jdbc;
	@Autowired BCryptPasswordEncoder BCrypt;

	public void addStatment(Estatement stmt) {
		String query = "INSERT INTO estatement VALUES (null, :aNum, :fname, :lname, :address, "
					 + ":trDate, :amt)";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("aNum", stmt.getAccountNumber());
		parameters.addValue("fname", stmt.getFirstName());
		parameters.addValue("lname", stmt.getLastName());
		parameters.addValue("address", stmt.getAddress());
		parameters.addValue("trDate", stmt.getTransactionDate());
		parameters.addValue("amt", stmt.getAmmount());
		jdbc.update(query, parameters);
	}
	
	public void addDebit(DebitCard debit) {
		String query = "INSERT INTO debitcard VALUES (null, :number, :cvv, :exp, :bal)";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("number", debit.getNumber());
		parameters.addValue("cvv", debit.getCvv());
		parameters.addValue("exp", debit.getExpiryDate());
		parameters.addValue("bal", debit.getBalance());
		jdbc.update(query, parameters);
	}
	
	public void addCredit(CreditCard credit) {
		String query = "INSERT INTO creditcard VALUES (null, :number, :cvv, :exp, :bal)";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("number", credit.getNumber());
		parameters.addValue("cvv", credit.getCvv());
		parameters.addValue("exp", credit.getExpiryDate());
		parameters.addValue("bal", credit.getBalance());
		jdbc.update(query, parameters);
	}
	
	public void addUserAccount(UserAccount user) {
		String query = "INSERT INTO useraccount VALUES "
				     + "(null, :fname, :lname, :dob, :address, :gender, :number, :email, "
				     + ":age, :sNumber, :aNumber, :debitId, :creditId)";
		execute(query, user);
	}
	
	public void addEmployee(Employee employee) {
		String query = "INSERT INTO employee VALUES "
					 + "(null, :firstName, :lastName, :startDate)";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("firstName", employee.getFirstName());
		parameters.addValue("lastName", employee.getLastName());
		parameters.addValue("startDate", employee.getStartDate());
		jdbc.update(query, parameters);
	}
	
	public void updateDebitBalance(DebitCard debit) {
		String query = "UPDATE debitcard SET balance = :bal WHERE debitCardId = :id";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("bal", debit.getBalance());
		parameters.addValue("id", debit.getDebitCardId());		
		jdbc.update(query, parameters);
	}
	
	public void updateUserAccount(UserAccount user) {
		String query = "UPDATE useraccount SET "
				     + "firstName = :fname, lastNAme = :lname, dateOfBirth = :dob, "
				     + "address = :address, gender = :gender, number = :number, "
				     + "email = :email, age = :age, sinNumber = :sNumber, "
				     + "AccountNumber = :aNumber, debitCardId = :debitId, creditCardId = :creditId "
				     + "WHERE userAccountId = :id";
		execute(query, user);
	}
	
	public void deleteUserAccount(UserAccount user) {
		String query = "DELETE FROM useraccount WHERE userAccountId = :id";
		execute(query, user);
	}
	
	public void updateEmployee(Employee employee) {
		String query = "UPDATE employee SET firstName = :fname, lastName = :lname "
					 + "WHERE employeeId = :id";
		execute(query, employee);
	}
	
	public void deleteEmployee(Employee employee) {
		String query = "DELETE FROM employee WHERE employeeId = :id";
		execute(query, employee);
	}
	
	public void execute(String query, Employee employee) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("id", employee.getEmployeeId());
		parameters.addValue("fname", employee.getFirstName());
		parameters.addValue("lname", employee.getLastName());
		jdbc.update(query, parameters);		
	}
	
	public void execute(String query, UserAccount user) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("id", user.getUserAccountId());
		parameters.addValue("fname", user.getFirstName());
		parameters.addValue("lname", user.getLastName());
		parameters.addValue("dob", user.getDateOfBirth());
		parameters.addValue("address", user.getAddress());
		parameters.addValue("gender", user.getGender());
		parameters.addValue("number", user.getNumber());
		parameters.addValue("email", user.getEmail());
		parameters.addValue("age", user.getAge());
		parameters.addValue("sNumber", user.getSinNumber());
		parameters.addValue("aNumber", user.getAccountNumber());
		parameters.addValue("debitId", user.getDebitCardId());
		parameters.addValue("creditId", user.getCreditCardId());
		jdbc.update(query, parameters);
	}
	
//-------------------------------------------------------------------------------
	
	public ArrayList<Estatement> getEstatements(String accountNumber) {
		String query = "SELECT * FROM estatement WHERE accountNumber = '"+accountNumber+"'";
		ArrayList<Estatement> estatements = (ArrayList<Estatement>) jdbc.query
				(query, new BeanPropertyRowMapper<Estatement>(Estatement.class));
		return estatements;
	}
	
	public DebitCard getDebitByNumber(String number) {
		String query = "SELECT * FROM debitcard WHERE number = :number";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("number", number);
		ArrayList<DebitCard> debitDetails = (ArrayList<DebitCard>)jdbc.query
		(query, parameters, new BeanPropertyRowMapper<DebitCard>(DebitCard.class));
		
		if (debitDetails.size() > 0)
			return debitDetails.get(0);
		else
			return null;
	}
	
	public DebitCard getDebitById(int id) {
		String query = "SELECT * FROM debitcard WHERE debitCardId = :id";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("id", id);
		ArrayList<DebitCard> debitDetails = (ArrayList<DebitCard>)jdbc.query
		(query, parameters, new BeanPropertyRowMapper<DebitCard>(DebitCard.class));
		
		if (debitDetails.size() > 0)
			return debitDetails.get(0);
		else
			return null;
	}
	
	public CreditCard getCreditByNumber(String number) {
		String query = "SELECT * FROM creditcard WHERE number = :number";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("number", number);
		ArrayList<CreditCard> creditDetails = (ArrayList<CreditCard>)jdbc.query
		(query, parameters, new BeanPropertyRowMapper<CreditCard>(CreditCard.class));
		
		if (creditDetails.size() > 0)
			return creditDetails.get(0);
		else return null;
	}
	
	public CreditCard getCreditById(int id) {
		String query = "SELECT * FROM creditcard WHERE creditCardId = :id";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("id", id);
		ArrayList<CreditCard> creditDetails = (ArrayList<CreditCard>)jdbc.query
		(query, parameters, new BeanPropertyRowMapper<CreditCard>(CreditCard.class));
		
		if (creditDetails.size() > 0)
			return creditDetails.get(0);
		else return null;
	}
	
	public UserAccount getUserAccountByDC(int debitCardId, int creditCardId) {
		String query = "SELECT * FROM useraccount "
				+ "WHERE debitCardId = :debitId AND creditCardId = :creditId";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("debitId", debitCardId);
		parameters.addValue("creditId", creditCardId);
		ArrayList<UserAccount> userAccountDetails = (ArrayList<UserAccount>)jdbc.query
		(query, parameters, new BeanPropertyRowMapper<UserAccount>(UserAccount.class));
		
		if (userAccountDetails.size() > 0)
			return userAccountDetails.get(0);
		else return null;
	}
	
	public UserAccount getUserAccountById(int id) {
		String query = "SELECT * FROM useraccount WHERE userAccountId = :id";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("id", id);
		ArrayList<UserAccount> userAccountDetails = (ArrayList<UserAccount>) jdbc.query
		(query, parameters, new BeanPropertyRowMapper<UserAccount>(UserAccount.class));
		
		if (userAccountDetails.size() > 0)
			return userAccountDetails.get(0);
		else return null;
	}
	
	public UserAccount getUserAccountByNum(String accNum) {
		String query = "SELECT * FROM useraccount WHERE accountNumber = :acc";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("acc", accNum);
		ArrayList<UserAccount> userAccountDetails = (ArrayList<UserAccount>) jdbc.query
		(query, parameters, new BeanPropertyRowMapper<UserAccount>(UserAccount.class));
		
		if (userAccountDetails.size() > 0)
			return userAccountDetails.get(0);
		else return null;
	}
	
	public ArrayList<UserAccount> getAllUserAccounts() {
		String query = "SELECT * FROM useraccount";
		ArrayList<UserAccount> userAccounts = (ArrayList<UserAccount>) jdbc.query
			(query, new BeanPropertyRowMapper<UserAccount>(UserAccount.class));
		return userAccounts;
	}
	
	public ArrayList<Estatement> getAllEstatements() {
		String query = "SELECT * FROM estatement";
		ArrayList<Estatement> estatements = (ArrayList<Estatement>) jdbc.query
				(query, new BeanPropertyRowMapper<Estatement>(Estatement.class));
		return estatements;
	}

	public ArrayList<Estatement> getAllEstatementsOfAccount(String accNum) {
		String query = "SELECT * FROM estatement WHERE accountNumber = "+ accNum;
		ArrayList<Estatement> estatements = (ArrayList<Estatement>) jdbc.query
				(query, new BeanPropertyRowMapper<Estatement>(Estatement.class));
		return estatements;
	}
	
	public ArrayList<Employee> getAllEmployees() {
		String query = "SELECT * FROM employee";
		ArrayList<Employee> employees = (ArrayList<Employee>) jdbc.query(query,
				new BeanPropertyRowMapper<Employee>(Employee.class));
		return employees;
	}
	
	public Employee getEmployeeById(int id) {
		String query = "SELECT * FROM employee WHERE employeeId = "+ id;
		ArrayList<Employee> employees = (ArrayList<Employee>) jdbc.query(query,
				new BeanPropertyRowMapper<Employee>(Employee.class));
		return employees.get(0);
	}
	
	public Employee getEmployeeByName(String fname, String lname) {
		String query = "SELECT * FROM employee WHERE firstName = :fname AND lastName = :lname";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("fname", fname);
		parameters.addValue("lname", lname);
		ArrayList<Employee> employees = (ArrayList<Employee>) jdbc.query(query, parameters,
				new BeanPropertyRowMapper<Employee>(Employee.class));
		return employees.get(0);
	}
	
	
//-------------------------------------------------------------------------------
	
	/* SECURITY methods */
	
	public User findUserAccount(String username) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "SELECT * FROM user WHERE username=:username";
		parameters.addValue("username", username);
		ArrayList<User> users = (ArrayList<User>)jdbc.query(query, parameters,
				new BeanPropertyRowMapper<User>(User.class));
		if (users.size() > 0) 
			return users.get(0);
		else return null;
	}
	
	public List<String> getRolesById(long userId) {
		ArrayList<String> roles = new ArrayList<String>();
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "select userroles.userId, roles.roleName "
				+ "FROM userroles, roles "
				+ "WHERE userroles.roleId=roles.roleId "
				+ "and userId=:userId";
		parameters.addValue("userId", userId);
		List<Map<String, Object>> rows = jdbc.queryForList(query, parameters);
		for (Map<String, Object> row : rows) {
			roles.add((String)row.get("roleName"));
		}
		return roles;
	}
	
	public void addUser(User user) {
    	String query = "INSERT INTO user VALUES (null, :username, :password, :userAccountId, :employeeId, :adminId)";
    	MapSqlParameterSource parameters = new MapSqlParameterSource();
    	parameters.addValue("username", user.getUsername());
    	parameters.addValue("password", BCrypt.encode(user.getPassword()));
    	parameters.addValue("userAccountId", user.getUserAccountId());
    	parameters.addValue("employeeId", user.getEmployeeId());
    	parameters.addValue("adminId", user.getAdminId());
    	jdbc.update(query, parameters);
    }
    
    public void addUserRoles(long userId, long roleId) {
    	String query = "insert into userroles (userId, roleId)" + 
    			" values (:userId, :roleId)";
    	MapSqlParameterSource parameters = new MapSqlParameterSource();
    	parameters.addValue("userId", userId);
    	parameters.addValue("roleId", roleId);
    	jdbc.update(query, parameters);   	
    }
    
    public User getUserByUserAccountId(int id) {
    	String query = "SELECT * FROM user WHERE userAccountId = "+id;
    	ArrayList<User> users = (ArrayList<User>)jdbc.query(query,
				new BeanPropertyRowMapper<User>(User.class));
    	return users.get(0);
    }
    
//  SEARCH --------------------------------------------------------------------------------
    
    public ArrayList<UserAccount> searchDB(String table,String column, String item) {		
		String query = "SELECT * FROM "+table+" WHERE "+column+" LIKE "+"'%"+item+"%'";
		ArrayList<UserAccount> results = (ArrayList<UserAccount>)jdbc.query(query,
			new BeanPropertyRowMapper<UserAccount>(UserAccount.class));
		return results;
	}
    
    public ArrayList<Employee> searchDBE(String table,String column, String item) {		
		String query = "SELECT * FROM "+table+" WHERE "+column+" LIKE "+"'%"+item+"%'";
		ArrayList<Employee> results = (ArrayList<Employee>)jdbc.query(query,
			new BeanPropertyRowMapper<Employee>(Employee.class));
		return results;
	}
    
}
