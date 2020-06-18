package it.uniroma3.siw.progetto.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.progetto.model.Project;
import it.uniroma3.siw.progetto.model.Task;
import it.uniroma3.siw.progetto.model.User;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long>{

	public List<Task> findByUserAssigned (User user);
	
	public List<Task> findByProject (Project project);
	
}
