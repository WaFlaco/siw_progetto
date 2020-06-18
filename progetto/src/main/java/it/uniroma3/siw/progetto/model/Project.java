package it.uniroma3.siw.progetto.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
//import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false, length = 100)
	private String name;
	
	@Column(nullable = false, updatable = false)
	private LocalDateTime creationTimestamp;
	
	/* mapping con User */
	//il proprietario
	@ManyToOne(fetch = FetchType.EAGER)
	private User owner;
	
	//i membri che ne hanno visibilita'
	@ManyToMany
	private List<User> members;
	
	/* mapping con Task */
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "project_id")
	private List<Task> projectTasks;
	
	/* mapping con Tag */
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "project_id")
	private List<Tag> projectTags;
	
	
	public Project() {
		this.members = new ArrayList<>();
		this.projectTasks = new ArrayList<>();
		this.projectTags = new ArrayList<>();
	}
	
	public Project(String name) {
		this();
		this.name = name;
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

	public LocalDateTime getCreationTimestamp() {
		return creationTimestamp;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public List<User> getMembers() {
		return members;
	}

	public void addMember(User member) {
		this.members.add(member);
	}
	
	public List<Task> getTasks(){
		return this.projectTasks;
	}
	
	public void addTask(Task task) {
		this.projectTasks.add(task);
	}
	
	public List<Tag> getTags(){
		return this.projectTags;
	}
	
	public void addTag(Tag tag) {
		this.projectTags.add(tag);
	}
	
	
}
