package it.uniroma3.siw.progetto.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;



import it.uniroma3.siw.progetto.model.Credential;

@Repository
public interface CredentialRepository extends CrudRepository<Credential, Long> {

	public Optional<Credential> findByUsername(String username);
	
	
}
