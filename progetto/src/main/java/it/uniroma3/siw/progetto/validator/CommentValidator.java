package it.uniroma3.siw.progetto.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.progetto.model.Comment;
import it.uniroma3.siw.progetto.model.Task;

@Component
public class CommentValidator implements Validator {
	
	final Integer MIN_LENGTH = 2;
	final Integer MAX_LENGTH = 50;
	
	@Override
	public void validate(Object target, Errors errors) {
		Comment comment = (Comment) target;
		String description = comment.getDescription().trim();
		
		if (description.isBlank())
			errors.rejectValue("description", "required");
		else if (description.length() < MIN_LENGTH || description.length() > MAX_LENGTH)
			errors.rejectValue("description", "size");
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Task.class.equals(clazz);
	}
}
