package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.GameRepository;
import repositories.OrderrRepository;
import repositories.RoundRepository;
import repositories.VoteRepository;
import domain.AnswerPlayer;
import domain.Game;
import domain.Orderr;
import domain.Player;
import domain.Round;
import domain.Vote;

@Service
@Transactional
public class VoteService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private VoteRepository voteRepository;
	@Autowired
	private GameRepository gameRepository;
	@Autowired
	private RoundRepository roundRepository;
	@Autowired
	private OrderrRepository orderrRepository;

	// Supporting services -----------------------------------------------------
	@Autowired
	private GameService gameService;
	@Autowired
	private AnswerPlayerService answerPlayerService;
	@Autowired
	private PlayerService playerService;
	@Autowired
	private RoundService roundService;
	@Autowired
	private OrderrService orderrService;
	@Autowired
	private ScoreService scoreService;
	
	// Constructor -----------------------------------------------------

	public VoteService() {
		super();
	}

	// Simple CRUD methods -----------------------------------------------------

	public Collection<Vote> findAll() {
		Collection<Vote> result;

		result = voteRepository.findAll();

		return result;
	}

	public Vote findOne(int voteId) {
		Assert.notNull(voteId);
		Vote result;

		result = voteRepository.findOne(voteId);

		return result;
	}
	
	// Votar (create + save + comprobación de si es el último voto)
	public int vote(int answerPlayerId, int gameId) {
		Assert.notNull(answerPlayerId);
		Assert.notNull(gameId);
		Vote result;
		AnswerPlayer answerPlayer;
		Game game;
		Round round;
		Orderr orderr;
		Player player;
		Collection<Vote> votes;
		Player main;
		
		main = playerService.findByPrincipal();
		answerPlayer = answerPlayerService.findOne(answerPlayerId);
		player = playerService.findByAnswer(answerPlayer.getId());
		game = gameService.findOne(gameId);
		round = roundService.findOneByGame(gameId, game.getRoundNumber());
		orderr = orderrService.findOneByRound(round.getId(), round.getQNumber());
		Assert.isTrue(game.getPlayers().contains(player));// Comprobar que es jugador de la partida
		Assert.isTrue(voteRepository.hasVoted(main.getId(), orderr.getId()) != null); // Comprobar si ya ha votado
		
		result = new Vote();
		
		result.setAnswerPlayer(answerPlayer);
		result.setPlayer(player);
		
		result = voteRepository.save(result);
		
		//Comprobar si es la última votación o no para pasar a la siguiente pregunta. 
		votes = voteRepository.allOrderrVotesByVote(result.getId());
		
		if(votes.size() == game.getPlayersNumber() - 1){// Si es la última votación del Orderr
			scoreService.updateScores(gameId);
			if(orderr.getQNumber() == game.getCatNumber()){//Si es la última Orderr de la ronda
				round.setEnded(true);
				if(round.getRoundNumber() == game.getPlayersNumber()){//Si es la última ronda de la partida
					playerService.updateProfiles(gameId);
				}else{
					game.setRoundNumber(game.getRoundNumber() + 1);
				}
			}else{
				round.setQNumber(round.getQNumber() + 1);
			}
		}
		
		orderrRepository.save(orderr);
		roundRepository.save(round);
		gameRepository.save(game);
		
		return result.getId();
	}

	// Other business methods -----------------------------------------------------
	
	// Encontrar la puntuación ganada de un jugador en la pregunta anterior a la actual de una partida determinada
	public int findLastMiniScore(int answerPlayerId, int gameId) {
		int result;
		AnswerPlayer answerPlayer;
		Player player;
		Game game;
		Round round;
		Orderr orderr;
		Round lastRound;
		int roundNumber;
		int qNumber;
		
		game = gameService.findOne(gameId);
		round = roundService.findOneByGame(gameId, game.getRoundNumber());
		orderr = orderrService.findOneByRound(round.getId(), round.getQNumber());
		roundNumber = round.getRoundNumber();
		qNumber = orderr.getQNumber();
		
		if(qNumber == 1 && !orderr.isAnswered()){
			roundNumber = roundNumber - 1;
			qNumber = game.getCatNumber();
		}else{
			qNumber = qNumber - 1;
		}
		
		lastRound = roundService.findOneByGame(gameId, roundNumber);
		
		answerPlayer = answerPlayerService.findOne(answerPlayerId);
		player = answerPlayer.getPlayer();
		result = 0;
		
		if(player.equals(lastRound.getSpeaker()) && answerPlayer.isCorrect() && answerPlayer.getVotes().isEmpty()){
			// Si la pregunta es del speaker y nadie le ha votado
			result = 3;
		}else{
			// Averiguar qué ha votado el jugador
			Vote vote = voteRepository.findVoteByMoment(player.getId(), game.getId(), roundNumber, qNumber);
			if(!player.equals(lastRound.getSpeaker()) && answerPlayer.isPerfect()){
				// Si la respuesta es la más cercana a la correcta y no es speaker
				result = result + 3;
			}
			if(vote.getPlayer().equals(player) && vote.getAnswerPlayer().equals(answerPlayer)){
				// Si te votas a ti mismo
				result = result + 0;
			}else if(vote.getAnswerPlayer().isCorrect()){
				// Si votas la correcta
				result = result + 2;
			}
			// Un punto por cada votación a tu respuesta
			result = result + answerPlayer.getVotes().size();
		}
		
		return result;
	}

	
}
