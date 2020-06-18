package it.uniroma3.siw.progetto.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.progetto.model.Comment;
import it.uniroma3.siw.progetto.model.Task;
import it.uniroma3.siw.progetto.model.User;
import it.uniroma3.siw.progetto.repository.CommentRepository;

@Service
public class CommentService {

	@Autowired
	CommentRepository commentRepository;
	
	@Transactional
	public Comment getComment(Long id) {
		Optional<Comment> comment = commentRepository.findById(id);
		return comment.orElse(null);
	}
	
	@Transactional
	public List<Comment> getUserComments(User user){
		List<Comment> result = commentRepository.findByOwner(user);
		return result;
	}
	
	@Transactional
	public List<Comment> getTaskComments(Task task){
		List<Comment> result = commentRepository.findByTask(task);
		return result;
	}
	
	@Transactional
	public Comment saveComment(Comment comment) {
		return this.commentRepository.save(comment);
	}
	
	@Transactional
	public void deleteComment(Comment comment) {
		this.commentRepository.delete(comment);
	}
}
