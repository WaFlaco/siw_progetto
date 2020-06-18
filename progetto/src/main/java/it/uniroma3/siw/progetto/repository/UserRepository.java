package it.uniroma3.siw.progetto.repository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.progetto.model.User;
import it.uniroma3.siw.progetto.model.Project;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{

	public Optional<User> findById(Long id);
	
	public List<User> findByVisibleProjects (Project project);
	
	
}
