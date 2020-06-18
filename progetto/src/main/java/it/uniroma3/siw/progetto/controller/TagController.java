package it.uniroma3.siw.progetto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.progetto.model.Project;
import it.uniroma3.siw.progetto.service.ProjectService;
import it.uniroma3.siw.progetto.service.TagService;
import it.uniroma3.siw.progetto.session.SessionData;

@Controller
public class TagController {

	@Autowired
	SessionData sessionData;
	
	@Autowired
	TagService tagService;
	
	@Autowired
	ProjectService projectService;
	
	/* AGGIUNGERE UN TAG AD UN PROGETTO GET	*/
	/* da completare */
	@RequestMapping(value = {"/user/projects/owner/{projectId}/add/tag"}, method = RequestMethod.GET)
	public String showTags(Model model, @PathVariable Long projectId) {
		
		Project project = projectService.getProject(projectId);
		
		
		return "";
	}
}
