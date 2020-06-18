package it.uniroma3.siw.progetto.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import it.uniroma3.siw.progetto.model.Credential;
import it.uniroma3.siw.progetto.model.User;
import it.uniroma3.siw.progetto.repository.CredentialRepository;

/* Creiamo un oggetto sessione per utilizzarlo come fosse una cache per i dati che ci servono
 * senza ricercarli ogni volta nel DB. 
 * 
 * @Scope -> ha una visuale limitata alla singola sessione e non per tutta la vita dell'applicazione
 */

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionData {
	
	private User user;
	private Credential credential;
	
	@Autowired
	private CredentialRepository credentialRepository;
	
	/* Ricerca dei dati dal DB */
	private void update() {
		Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails loggedUserDetails = (UserDetails) obj;
		
		this.credential = this.credentialRepository.findByUsername(loggedUserDetails.getUsername()).get();
		this.credential.setPassword("[PROTECTED]");
		this.user = this.credential.getUser();
	
	}
	
	public Credential getLoggedCredentials() {
		if (this.credential == null)
			this.update();
		return this.credential;
	}
	
	public User getLoggedUser() {
		if (this.user == null)
			this.update();
		return this.user;
	}

}
