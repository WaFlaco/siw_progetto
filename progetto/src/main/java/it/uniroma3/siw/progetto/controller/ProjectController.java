package it.uniroma3.siw.progetto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.progetto.model.Project;
import it.uniroma3.siw.progetto.model.Task;
import it.uniroma3.siw.progetto.model.User;
import it.uniroma3.siw.progetto.repository.ProjectRepository;
import it.uniroma3.siw.progetto.service.CredentialService;
import it.uniroma3.siw.progetto.service.ProjectService;
import it.uniroma3.siw.progetto.service.TaskService;
import it.uniroma3.siw.progetto.service.UserService;
import it.uniroma3.siw.progetto.session.SessionData;
import it.uniroma3.siw.progetto.validator.ProjectValidator;

@Controller
public class ProjectController {

	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	ProjectService projectService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CredentialService credentialService;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	ProjectValidator projectValidator;
	
	@Autowired
	SessionData sessionData;

	
	/* PROGETTI UTENTE */
	@RequestMapping(value = {"/user/projects"}, method = RequestMethod.GET)
	public String userProjects(Model model) {
		
		User loggedUser = sessionData.getLoggedUser();
		List<Project> ownedProjects = loggedUser.getOwnedProjects();
		List<Project> visibleProjects = loggedUser.getVisibileProjects();
		
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("ownedProjects", ownedProjects);
		model.addAttribute("visibleProjects", visibleProjects);
		
		return "userProjects";
	}
	
	/* VISUALIZZA UN TUO PROGETTO */
	//@PathVariable -> gestione di una richiesta parametrica
	@RequestMapping(value = {"/user/projects/owner/{projectId}"}, method = RequestMethod.GET)
	public String ownProject (Model model, @PathVariable Long projectId) {
		
		User loggedUser = sessionData.getLoggedUser();
		Project project = projectService.getProject(projectId);
		List<User> members = userService.getMembers(project);
		List<Task> tasks = taskService.getProjectTasks(project);
		
		//if (project == null || !project.getOwner().equals(loggedUser))
		//	return "redirect:/user/projects";
		
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("project", project);
		model.addAttribute("members", members);
		model.addAttribute("tasks", tasks);
		
		return "projectPageOwner";
	}
	
	/* VISUALIZZA UN PROGETTO SU CUI HAI VISIBILITA' */
	@RequestMapping(value = {"/user/projects/visible/{projectId}"}, method = RequestMethod.GET)
	public String visibleProject (Model model, @PathVariable Long projectId) {
		
		User loggedUser = sessionData.getLoggedUser();
		Project project = projectService.getProject(projectId);
		List<User> members = userService.getMembers(project);
		
		if (project == null || !members.contains(loggedUser))
			return "redirect:/user/projects";
		
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("project", project);
		model.addAttribute("members", members);
		
		return "projectPageVisible";
	}
	
	/* NUOVO PROGETTO GET */
	@RequestMapping(value = {"/user/projects/owner/new"}, method = RequestMethod.GET)
	public String showNewProjectForm(Model model) {
		
		User loggedUser = sessionData.getLoggedUser();
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("projectForm", new Project());
		
		return "userNewProject";
	}
	
	/* NUOVO PROGETTO POST */
	@RequestMapping(value = {"/user/projects/owner/new"}, method = RequestMethod.POST)
	public String createNewProject(@Validated @ModelAttribute("projectForm") Project project,
									BindingResult projectBindingResult, Model model) {
		
		User loggedUser = sessionData.getLoggedUser();
		List<Project> userProj = loggedUser.getOwnedProjects();
		projectValidator.validate(project, projectBindingResult);
		
		if(!projectBindingResult.hasErrors()) {
			project.setOwner(loggedUser);
			userProj.add(project);
			this.projectService.saveProject(project);
			return "redirect:/user/projects/";
		}
		model.addAttribute("loggedUser", loggedUser);
		
		return "userNewProject";
	}
	
	/* AGGIUNGI MEMBRO AD UN PROGETTO GET*/
	@RequestMapping(value = {"/user/projects/owner/{projectId}/add/member"}, method = RequestMethod.GET)
	public String showAddMember(Model model, @PathVariable Long projectId) {
		
		User loggedUser = sessionData.getLoggedUser();
		Project project = projectService.getProject(projectId);
		List<User> members = userService.getMembers(project);
		
		//creo una lista di utenti senza i membri che gia' hanno visibilita' del progetto
		List<User> users = userService.getAllUser();
		users.removeAll(members);
		
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("project", project);
		model.addAttribute("users", users);
		
		return "projectAddMember";
	}
	
	/* AGGIUNGI MEMBRO AD UN PROGETTO POST */
	//con la form, viene passato il valore del input "radio"?
	@RequestMapping(value = {"/user/projects/owner/{projectId}/add/member"}, method = RequestMethod.POST)
	public String addMemberToProject(Model model, @PathVariable Long projectId, @ModelAttribute("userId") Long userId) {
		
		Project project = projectService.getProject(projectId);
		User user = userService.getUser(userId);

		if (user == null)
			return "redirect:/user/projects/owner/" + project.getId();
		
		projectService.shareProjectWithUser(project, user);
		
		return "redirect:/user/projects/owner/" + project.getId();
	}
	
	
	/* CANCELLA PROGETTO */
	@RequestMapping(value = {"/user/projects/owner/{projectId}/delete"}, method = RequestMethod.POST)
	public String deleteProject(Model model, @PathVariable Long projectId) {
		
		Project project = projectService.getProject(projectId);
		
		//Serve o viene eliminato tramite cascade.remove?
		//loggedUser.getOwnedProjects().remove(project);
		
		this.projectService.deleteProject(project);
		
		return "redirect:/user/projects";
	}
	
}
