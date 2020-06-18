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
import it.uniroma3.siw.progetto.model.Task;
import it.uniroma3.siw.progetto.model.User;
import it.uniroma3.siw.progetto.service.CommentService;
import it.uniroma3.siw.progetto.service.TaskService;
import it.uniroma3.siw.progetto.session.SessionData;
import it.uniroma3.siw.progetto.validator.CommentValidator;

@Controller
public class CommentController {

	@Autowired
	SessionData sessionData;
	
	@Autowired
	CommentService commentService;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	CommentValidator commentValidator;
	
	/* VISUALIZZA I COMMENTI UTENTE */
	@RequestMapping(value = {"/user/comments"}, method = RequestMethod.GET)
	public String userComments(Model model) {
		
		User loggedUser = sessionData.getLoggedUser();
		List<Comment> userComments = commentService.getUserComments(loggedUser);
		
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("userComments", userComments);
		
		return "userComments";
	}
	
	/* AGGIUNGI UN COMMENTO AL TASK GET*/
	@RequestMapping(value = {"/tasks/{taskId}/add/comment"}, method = RequestMethod.GET)
	public String addComment(Model model, @PathVariable Long taskId) {
		
		Task task = taskService.getTask(taskId);
		
		model.addAttribute("task", task);
		model.addAttribute("comment", new Comment());
		
		return "taskAddComment";
	}
	
	/* AGGIUNGI UN COMMENTO AL POST */
	@RequestMapping(value = {"/tasks/{taskId}/add/comment"}, method = RequestMethod.POST)
	public String createNewProject(Model model, @PathVariable Long taskId,
			@Validated @ModelAttribute("commentForm") Comment comment, BindingResult commentBindingResult) {
		
		Task task = taskService.getTask(taskId);
		List<Comment> taskComments = task.getComments();
		commentValidator.validate(comment, commentBindingResult);
		
		if(!commentBindingResult.hasErrors()) {
			taskComments.add(comment);
			commentService.saveComment(comment);
			return "redirect:/tasks/" + task.getId();
		}
		return "redirect:/tasks/" + task.getId();
	}
		
}
