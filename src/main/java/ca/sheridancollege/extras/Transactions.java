package ca.sheridancollege.extras;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import org.springframework.context.annotation.Bean;

public class Transactions {

	@Bean public Transactions initialize() {
		return new Transactions();
	}
	
	private File transactions = new File("logs/userAccountTransactions.log");
	
	public void print(String event) throws IOException {
		PrintWriter log = new PrintWriter(new FileWriter(transactions, true));	
		log.println(new Date() + " - " + event);
		log.close();
	}
	
	public void clear() throws IOException {
		PrintWriter log = new PrintWriter(new FileWriter(transactions));
		log.print("");
		log.flush();
		log.close();
	}
	
	@SuppressWarnings("resource")
	public ArrayList<String> getTransactions() throws FileNotFoundException {
		ArrayList<String> userTransactions = new ArrayList<String>();
		Scanner scanFile = new Scanner(transactions);
		
		while (scanFile.hasNextLine())
			userTransactions.add(scanFile.nextLine());
		return userTransactions;
	}
	
}
