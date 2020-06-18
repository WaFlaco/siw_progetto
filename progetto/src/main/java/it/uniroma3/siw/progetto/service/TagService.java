package it.uniroma3.siw.progetto.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.progetto.model.Project;
import it.uniroma3.siw.progetto.model.Tag;
import it.uniroma3.siw.progetto.model.Task;
import it.uniroma3.siw.progetto.repository.TagRepository;

@Service
public class TagService {

	@Autowired
	TagRepository tagRepository;
	
	@Transactional
	public Tag getTag(Long id) {
		Optional<Tag> tag = this.tagRepository.findById(id);
		return tag.orElse(null);
	}
	
	@Transactional
	public List<Tag> getTagsByColour(String colour){
		List<Tag> tags = this.tagRepository.findByColour(colour);
		return tags;
	}
	
	@Transactional
	public List<Tag> getProjectTags(Project project){
		List<Tag> tags = this.tagRepository.findByProject(project);
		return tags;
	}
	
	@Transactional
	public List<Tag> getTaskTags(Task task){
		List<Tag> tags = this.tagRepository.findByTask(task);
		return tags;
	}
	
	
}
