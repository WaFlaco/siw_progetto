package it.uniroma3.siw.progetto.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.progetto.model.Project;
import it.uniroma3.siw.progetto.model.Task;
import it.uniroma3.siw.progetto.model.User;
import it.uniroma3.siw.progetto.repository.TaskRepository;

@Service
public class TaskService {
	
	@Autowired
	protected TaskRepository taskRepository;
	
	@Transactional
	public Task getTask(long id) {
		Optional<Task> result = this.taskRepository.findById(id);
		return result.orElse(null);
	}
	
	@Transactional
	public List<Task> getUserTasks(User user){
		List<Task> result = this.taskRepository.findByUserAssigned(user);
		return result;
	}
	
	@Transactional
	public List<Task> getProjectTasks(Project project){
		List<Task> result = this.taskRepository.findByProject(project);
		return result;
	}
	
	@Transactional
	public Task saveTask(Task task) {
		return this.taskRepository.save(task);
	}
	
	@Transactional
	public void deleteTask(Task task) {
		this.taskRepository.delete(task);
	}
	
}
