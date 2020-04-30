package ca.sheridancollege.extras;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Logger {
	
	private File logs = new File("logs/CyberKnox.txt");
	
	public void print(String event) throws IOException {
		PrintWriter log = new PrintWriter(new FileWriter(logs, true));	
		log.println(new Date() + " - " + event);
		log.close();
	}
	
	public void clear() throws IOException {
		PrintWriter log = new PrintWriter(new FileWriter(logs));
		log.print("");
		log.flush();
		log.close();
	}

	@SuppressWarnings("resource")
	public ArrayList<String> getLogs() throws FileNotFoundException {
		ArrayList<String> logList = new ArrayList<String>();
		Scanner scanFile = new Scanner(logs);
		
		while (scanFile.hasNextLine())
			logList.add(scanFile.nextLine());
		return logList;
	}
	
}
