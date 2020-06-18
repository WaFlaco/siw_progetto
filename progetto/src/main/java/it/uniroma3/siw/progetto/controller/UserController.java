package it.uniroma3.siw.progetto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.progetto.model.Credential;
import it.uniroma3.siw.progetto.model.User;
import it.uniroma3.siw.progetto.service.CredentialService;
import it.uniroma3.siw.progetto.session.SessionData;

@Controller
public class UserController {

	@Autowired
	SessionData sessionData;
	
	@Autowired
	CredentialService credentialService;

	
	/* HOME UTENTE */
	@RequestMapping(value = {"/user/home"}, method = RequestMethod.GET)
	public String home(Model model) {
		
		User loggedUser = sessionData.getLoggedUser();
		model.addAttribute("user", loggedUser);
		
		return "userHome";
	}
	
	/* PROFILO UTENTE */
	@RequestMapping(value = {"/user/profile"}, method = RequestMethod.GET)
	public String myData (Model model) {
		
		User loggedUser = sessionData.getLoggedUser();
		Credential credential = sessionData.getLoggedCredentials();
		
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("credential", credential);
		
		return "userProfile";
	}
	
	/* AGGIORNA PROFILO UTENTE GET */
	/* AGGIORNA PROFILO POST -> AUTHENTICATION CONTROLLER */
	@RequestMapping(value = {"/user/profile/update"}, method = RequestMethod.GET)
	public String showUpdateUserForm(Model model) {
		
		User loggedUser = sessionData.getLoggedUser();
		Credential credentials = sessionData.getLoggedCredentials();
				
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("credentials", credentials);
		
		model.addAttribute("credentialForm", new Credential());
		model.addAttribute("userForm", new User());
		
		return "userUpdateProfile";
	}

	
	
	/* LISTA UTENTI - ADMIN*/
	@RequestMapping(value = {"/admin/users"}, method = RequestMethod.GET)
	public String usersList(Model model) {
		
		User loggedUser = sessionData.getLoggedUser();
		List<Credential> credentials = this.credentialService.getAllCredentials();
	
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("credentials", credentials);
		
		return "adminAllUsers";
	}
	
	/* RIMUOVI UTENTE - ADMIN */
	@RequestMapping(value = {"/admin/users/{username}/delete"}, method = RequestMethod.POST)
	public String removeUser(Model model, @PathVariable String username) {
		
		Credential credential = this.credentialService.getCredentials(username);
		this.credentialService.deleteCredentials(credential);
		
		return "redirect:/admin/users";
	}

	


}
