package it.uniroma3.siw.progetto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.progetto.model.Credential;
import it.uniroma3.siw.progetto.model.User;
import it.uniroma3.siw.progetto.service.CredentialService;
import it.uniroma3.siw.progetto.session.SessionData;
import it.uniroma3.siw.progetto.validator.CredentialValidator;
import it.uniroma3.siw.progetto.validator.UserValidator;


@Controller
public class AuthenticationController {

	@Autowired
	CredentialService credentialService;
	
	@Autowired
	UserValidator userValidator;
	
	@Autowired
	CredentialValidator credentialValidator;
	
	@Autowired
	SessionData sessionData;
	
	/* REGISTRAZIONE UTENTE GET */
	@RequestMapping(value = {"/register/user"}, method = RequestMethod.GET)
	public String showRegisterForm(Model model) {
		model.addAttribute("credentialForm", new Credential());
		model.addAttribute("userForm", new User());
		
		return "register";
	}
	
	/* REGISTRAZIONE UTENTE POST */
	@RequestMapping(value = {"/register/user"}, method = RequestMethod.POST)
	public String registerUser(@Validated @ModelAttribute("userForm") User user,
								BindingResult userBindingResult,
								@Validated @ModelAttribute("credentialForm") Credential credential,
								BindingResult credentialBindingResult,
								Model model) {
		this.userValidator.validate(user, userBindingResult);
		this.credentialValidator.validate(credential, credentialBindingResult);
		
		if(!userBindingResult.hasErrors() && !credentialBindingResult.hasErrors()) {
			credential.setUser(user);
			credentialService.saveCredentials(credential);
			
			return "registrationSuccessful";
		}
		return "register";
	}
	
	/* AGGIORNA PROFILO POST */
	@RequestMapping(value = {"/user/profile/update"}, method = RequestMethod.POST)
	public String updateUser(@Validated @ModelAttribute("userForm") User user,
								BindingResult userBindingResult,
								@Validated @ModelAttribute("credentialForm") Credential credentials,
								BindingResult credentialsBindingResult,
								Model model) {
		
		User loggedUser = sessionData.getLoggedUser();
		Credential loggedCredential = sessionData.getLoggedCredentials();
		
		this.userValidator.validate(user, userBindingResult);
		this.credentialValidator.validate(credentials, credentialsBindingResult);
		
		/* Si puo' fare? */
		if(!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
			if (user.getFirstName() != null)
				loggedUser.setFirstName(user.getFirstName());
			if (user.getLastName() != null)
				loggedUser.setLastName(user.getLastName());
			if (credentials.getUsername() != null)
				loggedCredential.setUsername(credentials.getUsername());
			if(credentials.getPassword() != null)
				loggedCredential.setPassword(credentials.getPassword());
			
			credentialService.saveCredentials(loggedCredential);
		}
		return "redirect:/user/profile";
	}
}
