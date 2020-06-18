package it.uniroma3.siw.progetto.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.progetto.model.Task;

@Component
public class TaskValidator implements Validator {

	final Integer MIN_LENGTH = 2;
	final Integer MAX_LENGTH = 50;
	
	@Override
	public void validate(Object target, Errors errors) {
		Task task = (Task) target;
		String name = task.getName().trim();
		String description = task.getDescription().trim();
		
		if (name.isBlank())
			errors.rejectValue("name", "required");
		else if (name.length() < MIN_LENGTH || name.length() > MAX_LENGTH)
			errors.rejectValue("name", "size");
		
		else if (description.length() < MIN_LENGTH || description.length() > MAX_LENGTH)
			errors.rejectValue("description", "size");
		
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Task.class.equals(clazz);
	}

}
