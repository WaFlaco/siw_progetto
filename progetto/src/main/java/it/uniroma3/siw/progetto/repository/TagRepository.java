package it.uniroma3.siw.progetto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.progetto.model.Project;
import it.uniroma3.siw.progetto.model.Tag;
import it.uniroma3.siw.progetto.model.Task;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long> {

	public Optional<Tag> findById(Long id);
	
	public List<Tag> findByColour(String colour);
	
	public List<Tag> findByProject(Project project);
	
	public List<Tag> findByTask(Task task);
	
}
