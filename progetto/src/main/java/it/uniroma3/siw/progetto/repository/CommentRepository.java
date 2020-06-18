package it.uniroma3.siw.progetto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.progetto.model.Comment;
import it.uniroma3.siw.progetto.model.Task;
import it.uniroma3.siw.progetto.model.User;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {

	public Optional<Comment> findById(Long id);
	
	public List<Comment> findByOwner(User user);
	
	public List<Comment> findByTask(Task task);
	
}
