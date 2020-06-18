package it.uniroma3.siw.progetto.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false, length = 100)
	private String description;
	
	/* mapping con Task */
	@ManyToOne
	private Task task;
	
	/* mapping con User */
	@ManyToOne
	private User owner;	
	
	public Comment() {
	}

	public Long getId() {
		return id;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Task getTask() {
		return this.task;
	}
	
	public void setTask(Task task) {
		this.task = task;
	}

	public User getOwner() {
		return this.owner;
	}
	
	public void setOwner(User user) {
		this.owner = user;
	}
}
