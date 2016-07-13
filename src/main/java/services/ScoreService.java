package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ScoreRepository;
import repositories.VoteRepository;
import domain.AnswerPlayer;
import domain.Game;
import domain.Orderr;
import domain.Player;
import domain.Round;
import domain.Score;
import domain.Vote;
import forms.GameRankingForm;
import forms.Tripla;

@Service
@Transactional
public class ScoreService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ScoreRepository scoreRepository;
	
	@Autowired
	private VoteRepository voteRepository;

	// Supporting services -----------------------------------------------------
	@Autowired
	private GameService gameService;
	@Autowired
	private RoundService roundService;
	@Autowired
	private OrderrService orderrService;
	@Autowired
	private AnswerPlayerService answerPlayerService;
	
	// Constructor -----------------------------------------------------

	public ScoreService() {
		super();
	}

	// Simple CRUD methods -----------------------------------------------------

	public Collection<Score> findAll() {
		Collection<Score> result;

		result = scoreRepository.findAll();

		return result;
	}

	public Score findOne(int scoreId) {
		Assert.notNull(scoreId);
		Score result;

		result = scoreRepository.findOne(scoreId);

		return result;
	}

	public Score create(Game game, Player player) {
		Score result;
		Score score;
		
		score = new Score();
		
		score.setCurrent(0);
		score.setLast(0);
		score.setAcronym(0);
		score.setDate(0);
		score.setFilm(0);
		score.setCharacter_(0);
		score.setDefinition(0);
		score.setGame(game);
		score.setPlayer(player);
		
		result = scoreRepository.save(score);
		
		return result;
	}

	public void delete(int id) {
		Assert.notNull(id);
		scoreRepository.delete(id);
	}

	// Other business methods -----------------------------------------------------

	public GameRankingForm getGameRanking(int gameId) {
		GameRankingForm result;
		Game game;
		List<Tripla> scores;
		
		game = gameService.findOne(gameId);
		result = new GameRankingForm();
		scores = new ArrayList<Tripla>();
		
		for(Score e: game.getScores()){
			Tripla tripla = new Tripla();
			tripla.setCurrent(e.getCurrent());
			tripla.setLast(e.getLast());
			tripla.setNickname(e.getPlayer().getNickname());
			
			scores.add(tripla);
		}
		
		result.setScores(scores);
		
		return result;
	}
	
	//Actualizar puntuaciones de la 鷏tima Orderr de esta partida
	public void updateScores(int gameId){
		Game game;
		Round round;
		Orderr orderr;
		String category;
		List<AnswerPlayer> answerPlayers;
		int miniScore;
		
		game = gameService.findOne(gameId);
		round = roundService.findOneByGame(game.getId(), game.getRoundNumber());
		orderr = orderrService.findOneByRound(round.getId(), round.getQNumber());
		category = orderr.getQuestion().getCategory();
		answerPlayers = new ArrayList<AnswerPlayer>(orderr.getAnswerPlayers());
		
		for(AnswerPlayer e: answerPlayers){
			Score score;
			
			score = findScore(e.getPlayer().getId(), game.getId());
			miniScore = findMiniScore(e.getId(), game.getId());
			if(category == "DEFINITION"){
				score.setDefinition(score.getDefinition() + miniScore);
			}else if(category == "FILM"){
				score.setFilm(score.getFilm() + miniScore);
			}else if(category == "CHARACTER_"){
				score.setCharacter_(score.getCharacter_() + miniScore);
			}else if(category == "ACRONYM"){
				score.setAcronym(score.getAcronym() + miniScore);
			}else if(category == "DATE"){
				score.setDate(score.getDate() + miniScore);
			}
			
			score.setLast(score.getCurrent());
			score.setCurrent(score.getCurrent() + miniScore);
			
			//Comprobar que la suma de todas las puntuaciones de cada categor铆a es igual a la puntuaci贸n total
			Assert.isTrue(score.getCurrent() == (score.getDefinition() + score.getDate() + score.getFilm() + score.getAcronym() + score.getCharacter_()));
			
			scoreRepository.save(score);
		}
		
	}
	
	// Encontrar la puntuaci贸n ganada de un jugador en una pregunta determinada de una partida determinada
	public int findMiniScore(int answerPlayerId, int gameId) {
		int result;
		AnswerPlayer answerPlayer;
		Player player;
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
		
		answerPlayer = answerPlayerService.findOne(answerPlayerId);
		player = answerPlayer.getPlayer();
		result = 0;
		
		if(player.equals(round.getSpeaker()) && answerPlayer.isCorrect() && answerPlayer.getVotes().isEmpty()){
			// Si la pregunta es del speaker y nadie le ha votado
			result = 3;
		}else{
			// Averiguar qu茅 ha votado el jugador
			Vote vote = voteRepository.findVoteByMoment(player.getId(), game.getId(), roundNumber, qNumber);
			if(!player.equals(round.getSpeaker()) && answerPlayer.isPerfect()){
				// Si la respuesta es la m谩s cercana a la correcta y no es speaker
				result = result + 3;
			}
			if(vote.getPlayer().equals(player) && vote.getAnswerPlayer().equals(answerPlayer)){
				// Si te votas a ti mismo
				result = result + 0;
			}else if(vote.getAnswerPlayer().isCorrect()){
				// Si votas la correcta
				result = result + 2;
			}
			// Un punto por cada votaci贸n a tu respuesta
			result = result + answerPlayer.getVotes().size();
		}
		
		return result;
	}
	
	public Score findScore(int playerId, int gameId){
		Score result;
		
		result = scoreRepository.findScore(playerId, gameId);
		
		return result;
	}
	
	
	// Mayor puntuaci贸n de una partida (varias si hay empate)
	public List<Score> findBest(int gameId){
		List<Score> result;
		
		result = scoreRepository.findBest(gameId);
		
		return result;
	}
	
}