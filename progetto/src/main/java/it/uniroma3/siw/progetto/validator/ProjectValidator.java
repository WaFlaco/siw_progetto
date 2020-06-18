package it.uniroma3.siw.progetto.validator;

import org.springframework.validation.Validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import it.uniroma3.siw.progetto.model.Project;

@Component
public class ProjectValidator implements Validator {
	
		final Integer MIN_LENGTH = 2;
		final Integer MAX_LENGTH = 50;
	
		@Override
		public void validate(Object o, Errors errors) {
			
			Project project = (Project) o;
			String name = project.getName().trim();
			
			if (name.isBlank())
				errors.rejectValue("name", "required");
			else if (name.length() < MIN_LENGTH || name.length() > MAX_LENGTH)
				errors.rejectValue("name", "size");
		}
			
		@Override
		public boolean supports(Class<?> clazz) {
			return Project.class.equals(clazz);
		}
}

