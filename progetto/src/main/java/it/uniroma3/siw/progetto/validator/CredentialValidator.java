package it.uniroma3.siw.progetto.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.progetto.model.Credential;
import it.uniroma3.siw.progetto.service.CredentialService;

@Component
public class CredentialValidator implements Validator {

	@Autowired
	CredentialService credentialService;

	final Integer MIN_UN_LENGTH = 2;
	final Integer MIN_PWD_LENGTH = 5;
	final Integer MAX_LENGTH = 50;
	
	@Override
	public void validate(Object o, Errors errors) {
		Credential credential = (Credential) o;
		String username = credential.getUsername().trim();
		String password = credential.getPassword().trim();
		
		if (username.isBlank())
			errors.rejectValue("username", "required");
		else if (username.length() < MIN_UN_LENGTH || username.length() > MAX_LENGTH)
			errors.rejectValue("username", "size");
		else if (this.credentialService.getCredentials(username) != null)
			errors.rejectValue("username", "duplicate");
		
		if (password.isBlank())
			errors.rejectValue("password", "required");
		else if (password.length() < MIN_PWD_LENGTH || password.length() > MAX_LENGTH)
			errors.rejectValue("password", "size");
		}
	/*
	public void pwdValidate(Object o, Errors errors) {
		
	}
	*/	
		@Override
		public boolean supports(Class<?> clazz) {
			return Credential.class.equals(clazz);
		}
}
