package zcd.ts4u.kanban;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
public class Ts4uKanbanApplication{

	public static void main(String[] args) {
		SpringApplication.run(Ts4uKanbanApplication.class, args);
	}

//	@Autowired
//	private PasswordEncoder passwordEncoder;
//	@Override
//	public void run(String... args) throws Exception {
////
////		System.out.println(passwordEncoder.encode("asdzxc"));
//	}
}
