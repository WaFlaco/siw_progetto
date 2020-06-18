package it.uniroma3.siw.progetto.validator;


import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.progetto.model.User;

import org.springframework.stereotype.Component;

@Component
public class UserValidator implements Validator {

	final Integer MIN_LENGTH = 2;
	final Integer MAX_LENGTH = 50;

	@Override
	public void validate(Object o, Errors errors) {
		User user = (User) o;
		String firstName = user.getFirstName().trim();
		String lastName = user.getLastName().trim();
		
		if (firstName.isBlank())
			errors.rejectValue("firstName", "required");
		else if (firstName.length() < MIN_LENGTH || firstName.length() > MAX_LENGTH)
			errors.rejectValue("firstName", "size");
		
		if (lastName.isBlank())
			errors.rejectValue("lastName", "required");
		else if (lastName.length() < MIN_LENGTH || lastName.length() > MAX_LENGTH)
			errors.rejectValue("lastName", "size");
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}
	
}
