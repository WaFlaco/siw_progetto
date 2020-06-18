package it.uniroma3.siw.progetto.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;


@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false, length = 100)
	private String firstName;
	
	@Column(nullable = false, length = 100)
	private String lastName;
	
	@Column(nullable = false, updatable = false)
	private LocalDateTime creationTimestamp;
	
	/* mapping con Project */
	//i propri progetti
	@OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	private List<Project> ownedProjects;
	
	//i progetti su cui si ha visibilita'
	@ManyToMany(mappedBy = "members", fetch = FetchType.EAGER)
	private List<Project> visibleProjects;
		
	/* mapping con Comment */
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	private List<Comment> ownedComments;
	
	/* mapping con Task */
	//Quando un utente viene cancellato, i suoi task potrebbero essere associati ad altri utenti?
	@OneToMany
	private List<Task> ownedTasks;
	
	
	public User() {
		this.ownedProjects = new ArrayList<>();
		this.visibleProjects = new ArrayList<>();
		this.ownedTasks = new ArrayList<>();
		this.ownedComments = new ArrayList<>();
	}
	
	public User(String firstname, String lastname) {
		this();
		this.firstName = firstname;
		this.lastName = lastname;		
	}
	
	//ogni volta che viene salvata una nuova istanza nel DB, viene eseguito il metodo
	@PrePersist
	protected void onPersist() {
		this.creationTimestamp = LocalDateTime.now();
	}
	
	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDateTime getCreationTimestamp() {
		return creationTimestamp;
	}

	public List<Project> getOwnedProjects() {
		return ownedProjects;
	}

	public List<Project> getVisibileProjects() {
		return visibleProjects;
	}
	
	public List<Task> getOwnedTasks() {
		return ownedTasks;
	}
	
	public List<Comment> getOwnedComments() {
		return ownedComments;
	}
	

	@Override
	public String toString() {
		return "User [id =" + id + ", firstName =" + firstName
				+ ", lastName =" + lastName + ", creationTimestamp =" + creationTimestamp + 
				", ownedProjects =" + ownedProjects + ", visibileProjects =" + visibleProjects + "]";
	}

}
