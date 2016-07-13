package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.OrderrRepository;
import domain.AnswerPlayer;
import domain.Orderr;
import domain.Round;

@Service
@Transactional
public class OrderrService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private OrderrRepository orderrRepository;

	// Supporting services -----------------------------------------------------

	// Constructor -----------------------------------------------------

	public OrderrService() {
		super();
	}

	// Simple CRUD methods -----------------------------------------------------

	public Collection<Orderr> findAll() {
		Collection<Orderr> result;

		result = orderrRepository.findAll();

		return result;
	}

	public Orderr findOne(int voteId) {
		Assert.notNull(voteId);
		Orderr result;

		result = orderrRepository.findOne(voteId);

		return result;
	}

	public Orderr create(Round round, int i) {
		Orderr result;
		Orderr orderr;
		Collection<AnswerPlayer> answerPlayers;
		
		orderr = new Orderr();
		answerPlayers = new ArrayList<AnswerPlayer>();
		
		orderr.setAnswered(false);
		orderr.setQNumber(i);
		orderr.setRound(round);
		orderr.setAnswerPlayers(answerPlayers);
		
		result = orderrRepository.save(orderr);
		
		return result;
	}

	// Other business methods -----------------------------------------------------

	public Orderr findOneByRound(int roundId, int qNumber) {
		Assert.notNull(roundId);
		Assert.notNull(qNumber);
		Orderr result;

		result = orderrRepository.findOneByRound(roundId, qNumber);

		return result;
	}
}
