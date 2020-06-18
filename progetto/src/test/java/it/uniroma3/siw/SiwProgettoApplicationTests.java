package it.uniroma3.siw;

import it.uniroma3.siw.progetto.model.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import it.uniroma3.siw.progetto.service.CredentialService;
import it.uniroma3.siw.progetto.service.ProjectService;
import it.uniroma3.siw.progetto.service.UserService;
import it.uniroma3.siw.progetto.session.SessionData;



@SpringBootTest
class SiwProgettoApplicationTests {

	@Autowired
	SessionData sessionData;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ProjectService projectService;
	
	@Autowired
	CredentialService credentialService;
	
	
	@Test
	void addMemberToProject() {
		Project project = new Project("project1");
		
		User user1 = new User("Gianni", "Gian");
		User user2 = new User("Pino", "Pin");
		
		Credential cred1 = new Credential();
		cred1.setUser(user1);
		cred1.setUsername("GIA");
		cred1.setPassword("gianni1");
		cred1.setRole("DEFAULT_ROLE");
		Credential cred2 = new Credential();
		cred1.setUser(user2);
		cred1.setUsername("PIN");
		cred1.setPassword("pino2");
		cred1.setRole("DEFAULT_ROLE");
		
		
		
		
		
	}

}
