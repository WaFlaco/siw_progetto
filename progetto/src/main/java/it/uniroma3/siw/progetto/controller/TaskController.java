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

import it.uniroma3.siw.progetto.model.Comment;
import it.uniroma3.siw.progetto.model.Project;
import it.uniroma3.siw.progetto.model.Task;
import it.uniroma3.siw.progetto.model.User;
import it.uniroma3.siw.progetto.service.CommentService;
import it.uniroma3.siw.progetto.service.ProjectService;
import it.uniroma3.siw.progetto.service.TaskService;
import it.uniroma3.siw.progetto.service.UserService;
import it.uniroma3.siw.progetto.session.SessionData;
import it.uniroma3.siw.progetto.validator.TaskValidator;

@Controller
public class TaskController {

	@Autowired
	SessionData sessionData;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	ProjectService projectService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CommentService commentService;
	
	@Autowired
	TaskValidator taskValidator;
	
	
	
	/* AGGIUNGI TASK AL PROGETTO GET */
	@RequestMapping(value = {"/user/projects/owner/{projectId}/add/task"}, method = RequestMethod.GET)
	public String showTaskForm(Model model, @PathVariable Long projectId) {
		
		Project project = projectService.getProject(projectId);
		List<User> members = userService.getMembers(project);
		
		model.addAttribute("project", project);
		model.addAttribute("members", members);
		model.addAttribute("task", new Task());
		
		return "projectAddTask";
	}
	
	/* AGGIUNGI TASK AL PROGETTO POST */
	@RequestMapping(value = {"/user/projects/owner/{projectId}/add/task"}, method = RequestMethod.POST)
	public String addTask(Model model, @PathVariable Long projectId,
			@Validated @ModelAttribute ("taskForm") Task task, BindingResult taskBindingResult) {
		
		Project project = projectService.getProject(projectId);
		
		this.taskValidator.validate(task, taskBindingResult);
		
		if(!taskBindingResult.hasErrors()) {
			project.addTask(task);
			task.setProject(project);
			this.taskService.saveTask(task);
			
			model.addAttribute("project", project);
			model.addAttribute("task", task);
			
			return "projectAddTaskSuccess";
		}
		return "redirect:/user/projects/owner/" + project.getId();
	}
	
	/* VISUALIZZA TASK */
	///project/{projectId} / @PathVariable Long projectId
	@RequestMapping(value = {"/tasks/{taskId}"}, method = RequestMethod.GET)
	public String task (Model model, @PathVariable Long taskId) {
		
		//Project project = projectService.getProject(projectId);
		Task task = taskService.getTask(taskId);
		Project project = task.getProject();
		List<Comment> comments = commentService.getTaskComments(task);
		
		//rivedi
		User userAssigned = task.getUserAssigned();
		
		model.addAttribute("project", project);
		model.addAttribute("task", task);
		model.addAttribute("comments", comments);
		model.addAttribute("userAssigned", userAssigned);
		
		return "taskPage";		
	}
	
	/* VISUALIZZA TUTTI I TASK DI UN UTENTE */
	@RequestMapping(value = {"/user/tasks"}, method = RequestMethod.GET)
	public String showUserTasks(Model model) {
		
		User loggedUser = sessionData.getLoggedUser();
		List<Task> userTasks = taskService.getUserTasks(loggedUser);
		
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("userTasks", userTasks);
		
		return "userTasks";
	}
	
	/* ASSEGNA MEMBRO AL TASK GET */
	@RequestMapping(value = {"/tasks/{taskId}/assign/member"}, method = RequestMethod.GET)
	public String showMembersToAssign(Model model, @PathVariable Long taskId) {
		
		Task task = taskService.getTask(taskId);
		
		//possibile?
		Project project = task.getProject();
		List<User> members = userService.getMembers(project);
		
		model.addAttribute("project", project);
		model.addAttribute("members", members);
		model.addAttribute("task", task);
				
		return "taskAssignMember";
	}
	
	/* ASSEGNA MEMBRO AL TASK POST */
	@RequestMapping(value = {"/tasks/{taskId}/assign/member"}, method = RequestMethod.POST)
	public String assignMember(Model model, @PathVariable Long taskId, @ModelAttribute("memberId") Long memberId) {
	
		User member = userService.getUser(memberId);
		Task task = taskService.getTask(taskId);
		Project project = task.getProject();
		
		if(member == null)
			return "redirect:/user/projects/owner/" + project.getId();
	
		userService.assignTaskToUser(task, member);
		
		return "redirect:/user/projects/owner/" + project.getId();
	}
	
	/* MODIFICA TASK GET */
	@RequestMapping(value = {"/tasks/{taskId}/update"}, method = RequestMethod.GET)
	public String showUpdateTaskForm(Model model, @PathVariable Long taskId) {
		
		Task task = taskService.getTask(taskId);
		
		model.addAttribute("task", task);
		model.addAttribute("taskForm", new Task());
		
		return "taskUpdate";
	}
	
	/* MODIFICA TASK POST */
	@RequestMapping(value = {"/tasks/{taskId}/update"}, method = RequestMethod.POST)
	public String updateTask(Model model, @PathVariable Long taskId, 
			@Validated @ModelAttribute("taskForm") Task task, BindingResult taskBindingResult) {
		
		this.taskValidator.validate(task, taskBindingResult);
		
		Task oldTask = this.taskService.getTask(taskId);
	
		if(!taskBindingResult.hasErrors()) {
			if (task.getName() != null)
				oldTask.setName(task.getName());
			if (task.getDescription() != null)
				oldTask.setDescription(task.getDescription());
			
			this.taskService.saveTask(oldTask);
		}
		
		return "redirect:/tasks/" + task.getId();
	}
	
	/* CANCELLA TASK */
	@RequestMapping(value = {"/user/projects/owner/{projectId}/tasks/{taskId}/delete"}, method = RequestMethod.POST)
	public String deleteTask(Model model,@PathVariable Long projectId, @PathVariable Long taskId) {
		
		Project project = projectService.getProject(projectId);
		Task task = taskService.getTask(taskId);
		taskService.deleteTask(task);
		
		return "redirect:/user/projects/owner/" + project.getId();
	}
	
}

