package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.RoundRepository;
import domain.Game;
import domain.Orderr;
import domain.Player;
import domain.Round;

@Service
@Transactional
public class RoundService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private RoundRepository roundRepository;

	// Supporting services -----------------------------------------------------
	@Autowired
	private OrderrService orderrService;
	
	// Constructor -----------------------------------------------------

	public RoundService() {
		super();
	}

	// Simple CRUD methods -----------------------------------------------------

	public Collection<Round> findAll() {
		Collection<Round> result;

		result = roundRepository.findAll();

		return result;
	}

	public Round findOne(int roundId) {
		Assert.notNull(roundId);
		Round result;

		result = roundRepository.findOne(roundId);

		return result;
	}

	public Round create(Game game, Player player, int cont) {
		Round result;
		Round round;
		List<Orderr> orderrs;
		
		round = new Round();
		orderrs = new ArrayList<Orderr>();
		
		round.setEnded(false);
		round.setGame(game);
		round.setOrderrs(orderrs);
		round.setRoundNumber(cont);
		round.setQNumber(0);
		round.setSpeaker(player);
		
		result = roundRepository.save(round);
		
		for(int i = 1; i <= game.getCatNumber(); i++){
			orderrs.add(orderrService.create(result, i));
		}
		
		result = roundRepository.save(round);
		
		return result;
	}

	// Other business methods -----------------------------------------------------
	
	public Round findOneByGame(int gameId, int roundNumber) {
		Assert.notNull(gameId);
		Assert.notNull(roundNumber);
		Round result;

		result = roundRepository.findOneByGame(gameId, roundNumber);

		return result;
	}
}
