package vttp.ssf.assessment.eventmanagement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import vttp.ssf.assessment.eventmanagement.models.Event;
import vttp.ssf.assessment.eventmanagement.services.DatabaseService;

@SpringBootApplication
public class EventmanagementApplication implements CommandLineRunner {

	@Autowired
	private DatabaseService dbService;

	public static void main(String[] args) {
		SpringApplication.run(EventmanagementApplication.class, args);
	}
	
	// TODO: Task 1
	@Override
	public void run(String... args) throws Exception {
		ApplicationArguments argsOptions = new DefaultApplicationArguments(args);
		if (argsOptions.containsOption("file")){
			String fileName = argsOptions.getOptionValues("file").get(0);
			List<Event> events = dbService.readFile(fileName);
			System.out.printf("Events>>>%s\n",events.toString());
			events.stream().forEach(event -> dbService.saveRecord(event));
		}
	}
}
