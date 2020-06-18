package it.uniroma3.siw.progetto.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.progetto.model.Credential;
import it.uniroma3.siw.progetto.repository.CredentialRepository;

@Service
public class CredentialService {

	@Autowired
	protected CredentialRepository credentialRepository;
	
	//Dobbiamo dire al sistema di utilizzare la cifratura alla registrazione
	@Autowired
	protected PasswordEncoder passwordEncoder;
	
	
	@Transactional
	public Credential getCredentials(Long id) {
		Optional<Credential> result = this.credentialRepository.findById(id);
		return result.orElse(null);
	}
	
	@Transactional
	public Credential getCredentials(String username) {
		Optional<Credential> result = this.credentialRepository.findByUsername(username);
		return result.orElse(null);
	}
	
	@Transactional
	public List<Credential> getAllCredentials(){
		List<Credential> result = new ArrayList<>();
		Iterable<Credential> iterable = this.credentialRepository.findAll();
		for(Credential credential : iterable)
			result.add(credential);
		return result;
	}
	
	@Transactional
	public Credential saveCredentials(Credential credential) {
		credential.setRole(Credential.DEFAULT_ROLE);
		//cifratura
		credential.setPassword(this.passwordEncoder.encode(credential.getPassword()));
		
		return this.credentialRepository.save(credential);
	}
	
	@Transactional
	public void deleteCredentials(Credential credential) {
		this.credentialRepository.delete(credential);
	}
	

	
}
