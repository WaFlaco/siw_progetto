package it.uniroma3.siw.progetto.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.progetto.model.Project;
import it.uniroma3.siw.progetto.model.Task;
import it.uniroma3.siw.progetto.model.User;
import it.uniroma3.siw.progetto.repository.UserRepository;


@Service
public class UserService {
	
	//Autowired -> lasciare a springboot la creazione e l'instanziamento dell'oggetto
	@Autowired
	protected UserRepository userRepository;
	
	//Transactional "apre e chiude" automaticamente la transazione, effettuando il commit
	@Transactional
	public User getUser(long id) {
		Optional<User> result = this.userRepository.findById(id);
		return result.orElse(null);
	}
	
	//Essendo l'username univoco, se nel DB esiste gia' un username uguale, sollevera' una eccezione
	//DataIntegrityViolationException
	@Transactional
	public User saveUser(User user) {
		return this.userRepository.save(user);
	}
	
	@Transactional
	public List<User> getAllUser(){
		List<User> result = new ArrayList<>();
		Iterable<User> iterable = this.userRepository.findAll();
		for(User user : iterable)
			result.add(user);
		return result;
	}
	
	@Transactional
	public List<User> getMembers(Project project){
		List<User> result = new ArrayList<>();
		Iterable<User> iterable = this.userRepository.findByVisibleProjects(project);
		for(User user : iterable)
			result.add(user);
		return result;
	}
	
	@Transactional
	public User assignTaskToUser(Task task, User user) {
		user.getOwnedTasks().add(task);
		return this.userRepository.save(user);
	}
	
}
