package it.uniroma3.siw.progetto.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

@Entity
public class Task {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false, length = 100)
	private String name;
	
	@Column(nullable = false, length = 100)
	private String description;
	
	@Column(nullable = false, updatable = false)
	private LocalDateTime creationTimestamp;
	
	/* mapping con Project */
	@ManyToOne
	private Project project;
	
	/* mapping con User */
	@ManyToOne
	private User userAssigned;	
	
	/* mapping con Comment */
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "task_id")
	private List<Comment> comments;	
	
	/* mapping con Tag */
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "task_id")
	private List<Tag> tags;
	
	public Task() {
		this.comments = new ArrayList<>();
		this.tags = new ArrayList<>();
	}
	
	public Task(String name, String description) {
		this();
		this.name = name;
		this.description = description;
	}
	
	
	//ogni volta che viene salvata una nuova istanza nel DB, viene eseguito il metodo
	@PrePersist
	protected void onPersist() {
		this.creationTimestamp = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getCreationTimestamp() {
		return creationTimestamp;
	}
	
	public User getUserAssigned() {
		return this.userAssigned;
	}
	
	public void setUserAssigned(User user) {
		this.userAssigned = user;
	}
	
	public List<Comment> getComments(){
		return this.comments;
	}
	
	public List<Tag> getTags(){
		return this.tags;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
	
}
