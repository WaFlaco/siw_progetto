package it.uniroma3.siw.progetto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.progetto.model.User;
import it.uniroma3.siw.progetto.session.SessionData;

@Controller
public class AdminController {

	@Autowired
	SessionData sessionData;
	
	@RequestMapping(value = {"/admin/home"}, method = RequestMethod.GET)
	public String home(Model model) {
		User loggedUser = sessionData.getLoggedUser();
		model.addAttribute("user", loggedUser);
		return "adminHome";
	}
	
}
