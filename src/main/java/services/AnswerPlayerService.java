package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AnswerPlayerRepository;
import repositories.OrderrRepository;
import domain.AnswerPlayer;
import domain.Game;
import domain.Orderr;
import domain.Player;
import domain.Question;
import domain.Round;
import domain.Vote;
import forms.AnswerPlayerForm;

@Service
@Transactional
public class AnswerPlayerService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private AnswerPlayerRepository answerPlayerRepository;
	@Autowired
	private OrderrRepository orderrRepository;

	// Supporting services -----------------------------------------------------
	@Autowired
	private PlayerService playerService;
	@Autowired
	private QuestionService questionService;
	@Autowired
	private GameService gameService;
	@Autowired
	private RoundService roundService;
	@Autowired
	private OrderrService orderrService;
	
	// Constructor -----------------------------------------------------

	public AnswerPlayerService() {
		super();
	}

	// Simple CRUD methods -----------------------------------------------------

	public Collection<AnswerPlayer> findAll() {
		Collection<AnswerPlayer> result;

		result = answerPlayerRepository.findAll();

		return result;
	}

	public AnswerPlayer findOne(int answerPlayerId) {
		Assert.notNull(answerPlayerId);
		AnswerPlayer result;

		result = answerPlayerRepository.findOne(answerPlayerId);

		return result;
	}

	// Other business methods -----------------------------------------------------

	// Create y save
	public int answer(AnswerPlayerForm answerPlayerForm) {
		//Comprobar que no es una cadena en blanco (notblank)
		Assert.isTrue(answerPlayerForm.getAnswer() != "");
		AnswerPlayer result;
		Player player;
		Question question;
		List<Vote> votes;
		Game game;
		Round round;
		Orderr orderr;
				
		game = gameService.findOne(answerPlayerForm.getGameId());
		round = roundService.findOneByGame(game.getId(), game.getRoundNumber());
		orderr = orderrService.findOneByRound(round.getId(), round.getQNumber());
		player = playerService.findOne(answerPlayerForm.getPlayerId());
		question = questionService.findOne(answerPlayerForm.getQuestionId());
		
		votes = new ArrayList<Vote>();
		
		result = new AnswerPlayer();
		result.setAnswerPlayer(answerPlayerForm.getAnswer());
		result.setCorrect(false);
		result.setPerfect(false);
		result.setPlayer(player);
		result.setQuestion(question);
		result.setVotes(votes);
		result.setOrderr(orderr);
		
		result = answerPlayerRepository.save(result);
		
		// Comprobar si esta es la última respuesta del orderr que faltaba, si es así, poner answered a true (pregunta respondida, paso a fase 3)
		if(findAnswers(game.getId()).size() == game.getPlayersNumber()){
			orderr.setAnswered(true);
		}
		
		orderrRepository.save(orderr);
		
		return result.getId();
	}

	// Encontrar todas las respuestas a la pregunta anterior a la actual
	public List<AnswerPlayer> findLastAnswers(int gameId) {
		List<AnswerPlayer> result;
		Game game;
		Round round;
		Orderr orderr;
		int roundNumber;
		int qNumber;
		
		game = gameService.findOne(gameId);
		round = roundService.findOneByGame(gameId, game.getRoundNumber());
		orderr = orderrService.findOneByRound(round.getId(), round.getQNumber());
		roundNumber = round.getRoundNumber();
		qNumber = orderr.getQNumber();
		
		if(roundNumber == 1 && qNumber == 1 && !orderr.isAnswered()){
			result = null;
		}else if(qNumber == 1 && !orderr.isAnswered()){
			result = answerPlayerRepository.findAnswers(gameId, roundNumber - 1, game.getCatNumber());
		}else{
			result = answerPlayerRepository.findAnswers(gameId, roundNumber, qNumber - 1);
		}
		
		return result;
	}
	
	// Encontrar todas las respuestas a la pregunta actual
	public List<AnswerPlayer> findAnswers(int gameId) {
		List<AnswerPlayer> result;
		Game game;
		Round round;
		Orderr orderr;
		int roundNumber;
		int qNumber;
		
		game = gameService.findOne(gameId);
		round = roundService.findOneByGame(gameId, game.getRoundNumber());
		orderr = orderrService.findOneByRound(round.getId(), round.getQNumber());
		roundNumber = round.getRoundNumber();
		qNumber = orderr.getQNumber();
		
		result = answerPlayerRepository.findAnswers(gameId, roundNumber, qNumber);
		
		return result;
	}
	
	// Encontrar jugadores que han respondido en la pregunta actual
	public List<Player> findPlayersByOrderr(int gameId) {
		List<Player> result;
		Game game;
		Round round;
		Orderr orderr;
		int roundNumber;
		int qNumber;
		
		game = gameService.findOne(gameId);
		round = roundService.findOneByGame(gameId, game.getRoundNumber());
		orderr = orderrService.findOneByRound(round.getId(), round.getQNumber());
		roundNumber = round.getRoundNumber();
		qNumber = orderr.getQNumber();
		
		result = answerPlayerRepository.findPlayersByOrderr(gameId, roundNumber, qNumber);
		
		return result;
	}
	
	// Encontrar respuesta del jugador X a la pregunta actual
	public AnswerPlayer findByPlayer(int gameId, int playerId) {
		AnswerPlayer result;
		Game game;
		Round round;
		Orderr orderr;
		int roundNumber;
		int qNumber;
		
		game = gameService.findOne(gameId);
		round = roundService.findOneByGame(gameId, game.getRoundNumber());
		orderr = orderrService.findOneByRound(round.getId(), round.getQNumber());
		roundNumber = round.getRoundNumber();
		qNumber = orderr.getQNumber();
		
		result = answerPlayerRepository.findByPlayer(gameId, roundNumber, qNumber, playerId);
		
		return result;
	}

}
