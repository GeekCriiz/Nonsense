package services;

import java.util.Collection;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import domain.Actor;

@Service
@Transactional
public class ActorService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ActorRepository actorRepository;

	// Supporting services -----------------------------------------------------

	// Constructor -----------------------------------------------------

	public ActorService() {
		super();
	}

	// Simple CRUD methods -----------------------------------------------------

	public Collection<Actor> findAll() {
		Collection<Actor> result;

		result = actorRepository.findAll();

		return result;
	}

	public Actor findOne(int actorId) {
		Assert.notNull(actorId);
		Actor result;

		result = actorRepository.findOne(actorId);

		return result;
	}

	// Other business methods -----------------------------------------------------
	public Actor findByUsername(String username) {
		Assert.notNull(username);
		Actor result;

		result = actorRepository.findByUsername(username);

		return result;
	}

}
