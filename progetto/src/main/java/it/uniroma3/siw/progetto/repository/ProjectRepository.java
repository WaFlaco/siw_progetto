package it.uniroma3.siw.progetto.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.progetto.model.Project;
import it.uniroma3.siw.progetto.model.User;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {

		//ricerca tutti i progetti associati ad un membro, quindi di un utente che ne ha visibilita'
		public List<Project> findByMembers (User members);
		
		//ricerca tutti i progetti di cui un utente ne e' proprietario
		public List<Project> findByOwner (User owner);
		
}
