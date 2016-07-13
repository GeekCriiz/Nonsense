package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.GameRepository;
import repositories.PlayerRepository;
import domain.Alert;
import domain.AnswerPlayer;
import domain.Game;
import domain.Orderr;
import domain.Player;
import domain.Question;
import domain.Request;
import domain.Round;
import domain.Score;
import forms.AnswerForm;
import forms.ContinueGameForm;
import forms.QuestionForm;
import forms.SeeGameForm;
import forms.Tupla;

@Service
@Transactional
public class GameService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private GameRepository gameRepository;
	@Autowired
	private PlayerRepository playerRepository;

	// Supporting services -----------------------------------------------------
	@Autowired
	private PlayerService playerService;
	@Autowired
	private AnswerPlayerService answerPlayerService;
	@Autowired
	private RequestService requestService;
	@Autowired
	private ScoreService scoreService;
	@Autowired
	private RoundService roundService;
	@Autowired
	private OrderrService orderrService;
	@Autowired
	private AlertService alertService;
	@Autowired
	private VoteService voteService;
	@Autowired
	private QuestionService questionService;
	
	// Constructor -----------------------------------------------------

	public GameService() {
		super();
	}

	// Simple CRUD methods -----------------------------------------------------

	public Collection<Game> findAll() {
		Collection<Game> result;

		result = gameRepository.findAll();

		return result;
	}

	public Game findOne(int gameId) {
		Assert.notNull(gameId);
		Game result;

		result = gameRepository.findOne(gameId);

		return result;
	}
	
	public SeeGameForm seeGame(int gameId) {
		Assert.notNull(gameId);
		SeeGameForm result;
		Game game;
		
		game = findOne(gameId);

		result = new SeeGameForm();
		
		result.setRoundNumber(game.getRoundNumber());
		result.setScores(game.getScores());
		result.setPlayers(game.getPlayers());

		return result;
	}
	
	public int createCustom(int questions, Collection<Tupla> following) {
		Assert.notNull(following);
		Assert.notNull(questions);
		Assert.isTrue(following.size() > 1 && following.size() < 8);
		Assert.isTrue(questions > 0 && questions < 6);
		Game result;
		Game game;
		List<Score> scores;
		List<Round> rounds;
		List<Player> players;
		List<Request> requests;
			
		players = new ArrayList<Player>();
		requests = new ArrayList<Request>();
			
		players.add(playerService.findByPrincipal());
			
		game = new Game();
		scores = new ArrayList<Score>();
		rounds = new ArrayList<Round>();
		
		game.setAlerts(new ArrayList<Alert>());
		game.setCatNumber(questions);
		game.setCustom(true);
		game.setPlayers(players);
		game.setRoundNumber(0);
		game.setPlayersNumber(players.size());
		game.setRounds(rounds);
		game.setScores(scores);
			
		result = gameRepository.save(game);
		
		for(Tupla e: following){
			Assert.isTrue(checkIsFollowing(e.getPlayerId()));
			requests.add(requestService.create(e.getPlayerId(), result.getId()));
		}
			
		rounds.add(roundService.create(result, playerService.findByPrincipal(), 1));
		scores.add(scoreService.create(result, playerService.findByPrincipal()));

		result.setRequests(requests);
		result.setRounds(rounds);
		result.setScores(scores);
			
		result = gameRepository.save(result);
			
		// Comprobar que el número de rondas, peticiones y puntuaciones es el mismo para el juego creado
		Assert.isTrue(result.getRequests().size() + 1 == result.getPlayersNumber());
			
		return result.getId();
	}
	
	// Other business methods -----------------------------------------------------
	
	private boolean checkIsFollowing(int playerId) {
		Player main;
		Player player;
		
		boolean result = false;
		
		main = playerService.findByPrincipal();
		player = playerService.findOne(playerId);
		
		if(main.getFollowing().contains(player)){
			result = true;
		}
		
		return result;
	}

	public void kickPlayer(Game game, Player player) {
		Assert.notNull(game);
		Assert.notNull(player);
		List<Player> players;
		List<Game> games;
		
		games = new ArrayList<Game>(player.getGames());
		games.remove(game);
		players = new ArrayList<Player>(game.getPlayers());
		players.remove(player);
		
		player.setGames(games);
		player.setAbandoned(player.getAbandoned()+1);
		game.setPlayers(players);
		game.setPlayersNumber(players.size());
		
		playerRepository.save(player);
		gameRepository.save(game);
	}

	public Collection<SeeGameForm> findAllGamesByPrincipal() {
		Collection<Game> games;
		Collection<SeeGameForm> result;
		SeeGameForm s;
		Player player;
		int playerId;
		
		player = playerService.findByPrincipal();	
		Assert.notNull(player);
		playerId = player.getId();

		games = gameRepository.findAllGamesByPrincipal(playerId);
		
		s = new SeeGameForm();
		result = new ArrayList<SeeGameForm>();
		
		for (Game g : games){			
			s.setPlayers(g.getPlayers());
			s.setRoundNumber(g.getRoundNumber());
			s.setScores(g.getScores());
			result.add(s);
		}
		
		return result;
	}

	public Collection<Score> findScores(int gameId) {
		Collection<Score> result;
		
		result = gameRepository.findScores(gameId);
		
		return result;
	}

	public Collection<Round> findRounds(int gameId) {
		Collection<Round> result;
		
		result = gameRepository.findRounds(gameId);
		
		return result;
	}

	//cambiar por collection<string>
	public Collection<Player> findPlayers(int gameId) {
		Collection<Player> result;
		
		result = gameRepository.findPlayers(gameId);
		
		return result;
	}

	public Collection<Game> findGamesByPlayer(int playerId) {
		Collection<Game> result;
		
		result = gameRepository.findGamesByPlayer(playerId);
		
		return result;
	}

	// Buscar partidas normales y unirse a una, o crear si no hay ninguna ------------------------------------------
	public List<Integer> searchNormal(int questions, int players) {
		Assert.isTrue(players > 2 && players < 9);
		Assert.isTrue(questions > 0 && questions < 6);
		Game game;
		List<Game> games;
		List<Integer> result;
		Random rand;
		Player main;
			
		main = playerService.findByPrincipal();
		rand = new Random();
		result = new ArrayList<Integer>();
			
		games = new ArrayList<Game>(gameRepository.findOneByQuestionsAndPlayersNotMain(questions, players, main));
			
		if(!games.isEmpty()){
			game = games.get(rand.nextInt(games.size()));
			Game aux = joinNormal(game);
			if(aux.getPlayersNumber() == aux.getPlayers().size()){
				result.add(2);
			}else if(aux.getPlayersNumber() > aux.getPlayers().size()){
				result.add(1);
			}else{
				result.add(-1);
			}
			result.add(aux.getId());
		}else{
			game = new Game();
			result.add(3);
			result.add(createNormal(questions, players));
		}
			
		return result;
	}

	public Game joinNormal(Game game) {
		Game result;
		Assert.isTrue(game.getPlayers().size() < game.getPlayersNumber());
		List<Player> players;
			
		players = new ArrayList<Player>(game.getPlayers());
		players.add(playerService.findByPrincipal());
			
		game.setPlayers(players);
			
		result = gameRepository.save(game);
			
		return result;
	}
		
	public int createNormal(int questions, Integer playersNum) {
		Assert.notNull(playersNum);
		Assert.notNull(questions);
		Assert.isTrue(playersNum > 1 && playersNum < 8);
		Assert.isTrue(questions > 0 && questions < 6);
		Game result;
		Game game;
		List<Score> scores;
		List<Round> rounds;
		List<Player> players;
		int cont;
		
		players = new ArrayList<Player>();
		
		players.add(playerService.findByPrincipal());
			
		game = new Game();
		scores = new ArrayList<Score>();
		rounds = new ArrayList<Round>();
		
		game.setAlerts(new ArrayList<Alert>());
		game.setCatNumber(questions);
		game.setCustom(false);
		game.setPlayers(players);
		game.setRoundNumber(0);
		game.setPlayersNumber(playersNum);
		game.setRounds(rounds);
		game.setScores(scores);
			
		result = gameRepository.save(game);
			
		cont = 0;
			
		for(Player e: players){
			cont ++;
			rounds.add(roundService.create(result, e, cont));
			scores.add(scoreService.create(result, e));
		}
		result.setRounds(rounds);
		result.setScores(scores);
			
		result = gameRepository.save(result);
			
		// Comprobar que el número de rondas, jugadores y puntuaciones es el mismo para el juego creado
		Assert.isTrue(result.getPlayers().size() == result.getScores().size());
		Assert.isTrue(result.getPlayers().size() == result.getRounds().size());
			
		return result.getId();
	}

	public ContinueGameForm continueGame(int gameId) {
		Game game;
		ContinueGameForm result;
		
		result = new ContinueGameForm();
		game = findOne(gameId);
		result.setGameId(game.getId());
		
		// Comprobar que la partida no ha terminado aún:
		if(game.getRoundNumber() == game.getPlayersNumber()){
			Assert.isTrue(!roundService.findOneByGame(game.getId(), game.getRoundNumber()).isEnded());
		}
		
		// Ver en qué fase está la pregunta
		// 0: Partida sin empezar, sala de espera.
		// 1: Charlatán escogiendo categoría (se podrán ver los resultados de la pregunta anterior). Charlatán ve la lista de 
		// respuestas de la pregunta anterior, podrá seleccionar una Y SÓLO UNA que sea la que más se acerque a la correcta
		// según su criterio y un botón para seleccionar la siguiente categoría; los demás el nick del charlatán y la lista
		// de respuestas  de la anterior con su propietario.
		// 2: Jugadores respondiendo. Charlatán ve que los demás están respondiendo y ve la lista de las respuestas de cada uno. Los
		// demás ven la pregunta elegida en ese momento y pueden completar la respuesta.
		// 3: Jugadores votando. Charlatán ve las respuestas de los demás. Los demás podrán votar la respuesta que les parezca correcta. Pueden votarse
		// a sí mismos pero les saldá un indicador de que esa es su respuesta, para saber cuál es. 
		// 4: Partida acabada. Botón de ver puntuaciones.
		if(game.getRoundNumber() == 0){
			List<Tupla> tuplas = new ArrayList<Tupla>();
			result.setHost(playerService.findHost(game.getId()).getNickname());
			for(Player e: game.getPlayers()){
				Tupla tupla = new Tupla();
				tupla.setNickname(e.getNickname());
				tupla.setPlayerId(e.getId());
				tuplas.add(tupla);
			}
			result.setPlayers(tuplas);
			result.setPlayersNumber(game.getPlayersNumber());
			result.setPhase(0);
		}else{
			Round round;
			Orderr orderr;
			int currentRound;
			int currentQuestion;
			int phase;
			Player main;
			boolean speaker;
			
			main = playerService.findByPrincipal();
			speaker = playerService.isSpeaker(main.getId(), game.getId());
			round = roundService.findOneByGame(gameId, game.getRoundNumber());
			currentRound = round.getRoundNumber();
			orderr = orderrService.findOneByRound(round.getId(), round.getQNumber());
			currentQuestion = gameRepository.findQNumber(gameId);
			
			result.setCurrentRound(currentRound);
			result.setRounds(game.getRounds().size());
			result.setCurrentQuestion(currentQuestion);
			result.setQNumber(round.getOrderrs().size());
			
			phase = -100;// Si phase = -100, error por algún sitio
				
			// FASE 1 ------------------------------------------------------------------------------------------------------------
			if(orderr.getQuestion().equals(null) && !orderr.isAnswered() && !round.isEnded()){
				phase = 1;
				List<AnswerPlayer> lastAnswers;
				List<AnswerForm> lastAnswersForm;
				QuestionForm questionForm;
				
				// Pregunta de la ronda anterior (descripción y categoría)
				questionForm = new QuestionForm();
				
				if(orderr.getQNumber() == 1 && !orderr.isAnswered()){
					Question question = questionService.findByQNumber(gameId, round.getRoundNumber() - 1, game.getCatNumber());
					questionForm.setDescription(question.getDescription());
					questionForm.setCategory(question.getCategory());
				}else{
					Question question = questionService.findByQNumber(gameId, round.getRoundNumber(), orderr.getQNumber()- 1);
					questionForm.setDescription(question.getDescription());
					questionForm.setCategory(question.getCategory());
				}
				result.setQuestion(questionForm);
				
				lastAnswers = answerPlayerService.findLastAnswers(game.getId());
				//Si este lastAnswers es null, es que es la primera ronda (no hay todavía ninguna question elegida)
				
				lastAnswersForm = new ArrayList<AnswerForm>();
				
				if(lastAnswers != null){
					// Lista con las respuestas de cada jugador, el jugador al que pertenece (id y nick) y la puntuación que
					// consiguieron en esa pregunta
					for(AnswerPlayer ap: lastAnswers){
						int miniScore = voteService.findLastMiniScore(ap.getId(), game.getId());
						AnswerForm answerForm = new AnswerForm();
						answerForm.setAnswer(ap.getAnswerPlayer());
						answerForm.setNickname(ap.getPlayer().getNickname());
						answerForm.setPlayerId(ap.getPlayer().getId());
						answerForm.setMiniScore(miniScore);
						lastAnswersForm.add(answerForm);
					}
					result.setLastAnswers(lastAnswersForm);
				}
				
				if(main.getId() == round.getSpeaker().getId()){	// Fase 1: Cuando es el charlatán
					result.setSpeaker(speaker);
				}else{											// Fase 1: Cuando NO es el charlatán
					result.setSpeaker(speaker);
					result.setHost(playerService.findSpeaker(gameId).getNickname());
				}
			// FASE 2 ------------------------------------------------------------------------------------------------------------	
			}else if(!orderr.getQuestion().equals(null) && !orderr.isAnswered() && !round.isEnded()){
				phase = 2;
				List<AnswerPlayer> answers;
				List<AnswerForm> answersForm;
				QuestionForm questionForm;
				Question question;
				
				answersForm = new ArrayList<AnswerForm>();
				// Pregunta actual
				questionForm = new QuestionForm();
				question = questionService.findByQNumber(gameId, round.getRoundNumber(), orderr.getQNumber());
				questionForm.setDescription(question.getDescription());
				questionForm.setCategory(question.getCategory());
				result.setQuestion(questionForm);
				
				if(main.getId() == round.getSpeaker().getId()){	// Fase 2: Cuando es el charlatán
					answers = answerPlayerService.findAnswers(gameId);
					for(AnswerPlayer ap: answers){
						AnswerForm answerForm = new AnswerForm();
						answerForm.setAnswer(ap.getAnswerPlayer());
						answerForm.setNickname(ap.getPlayer().getNickname());
						answerForm.setPlayerId(ap.getPlayer().getId());
						answersForm.add(answerForm);
					}
					result.setAnswers(answersForm);
					result.setSpeaker(speaker);
				}else{											// Fase 2: Cuando NO es el charlatán
					List<Player> players;
					
					players = answerPlayerService.findPlayersByOrderr(gameId);
					if(!players.contains(main)){
						result.setAnswerPlayer("");// Respuesta en blanco para que la rellenen
					}else{
						result.setAnswerPlayer(answerPlayerService.findByPlayer(gameId, main.getId()).getAnswerPlayer());// La respuesta que ya ha dado (no podemos dejar que la vuelva a rellenar)
					}
					result.setSpeaker(speaker);
				}
			// FASE 3 ------------------------------------------------------------------------------------------------------------	
			}else if(!orderr.getQuestion().equals(null) && orderr.isAnswered() && !round.isEnded()){
				// El charlatán y los demás ven casi lo mismo pero con diferentes privilegios
				phase = 3;
				List<AnswerPlayer> answers;
				List<AnswerForm> answersForm;
				QuestionForm questionForm;
				Question question;
				
				answersForm = new ArrayList<AnswerForm>();
				// Pregunta actual
				questionForm = new QuestionForm();
				question = questionService.findByQNumber(gameId, round.getRoundNumber(), orderr.getQNumber());
				questionForm.setDescription(question.getDescription());
				questionForm.setCategory(question.getCategory());
				result.setQuestion(questionForm);
				
				// Lista de rsepuestas actual
				answers = answerPlayerService.findAnswers(gameId);
				for(AnswerPlayer ap: answers){
					AnswerForm answerForm = new AnswerForm();
					answerForm.setAnswer(ap.getAnswerPlayer());
					answerForm.setNickname(ap.getPlayer().getNickname());
					answerForm.setPlayerId(ap.getPlayer().getId());
					answersForm.add(answerForm);
				}
				result.setAnswers(answersForm);
				result.setSpeaker(speaker);
			// FASE 4 (partida acabada) ----------------------------------------------------------------------------------------------
			}else if((game.getRoundNumber() == round.getRoundNumber()) && round.isEnded()){
				phase = 4;
			}
			
			result.setPhase(phase);
		}
		
		return result;
	}

	public void deleteUnstarted(int gameId) {
		Assert.notNull(gameId);
		Game game;
		
		game = findOne(gameId);
		
		for(Score e: game.getScores()){
			scoreService.delete(e.getId());
		}
		
		for(Request h: game.getRequests()){
			requestService.delete(h.getId());
		}
		
		for(Alert a: game.getAlerts()){
			alertService.delete(a.getId());
		}
		
		gameRepository.delete(gameId);
	}
	
	public Collection<SeeGameForm> findPendingGamesByPrincipal() {
		Collection<Game> games;
		Collection<SeeGameForm> result;
		SeeGameForm s;
		Player player;
		int playerId;
		
		player = playerService.findByPrincipal();	
		Assert.notNull(player);
		playerId = player.getId();

		games = gameRepository.findPendingGamesByPrincipal(playerId);
		
		s = new SeeGameForm();
		result = new ArrayList<SeeGameForm>();
		
		for (Game g : games){			
			s.setPlayers(g.getPlayers());
			s.setRoundNumber(g.getRoundNumber());
			s.setScores(g.getScores());
			result.add(s);
		}
		
		return result;
	}

	public Collection<SeeGameForm> findEnCursoGamesByPrincipal() {
		Collection<Game> games;
		Collection<SeeGameForm> result;
		SeeGameForm s;
		Player player;
		int playerId;
		
		player = playerService.findByPrincipal();	
		Assert.notNull(player);
		playerId = player.getId();

		games = gameRepository.findEnCursoGamesByPrincipal(playerId);
		
		s = new SeeGameForm();
		result = new ArrayList<SeeGameForm>();
		
		for (Game g : games){			
			s.setPlayers(g.getPlayers());
			s.setRoundNumber(g.getRoundNumber());
			s.setScores(g.getScores());
			result.add(s);
		}
		
		return result;
	}

	
	//El sistema deberá elegir aleatoriamente cada ronda a un charlatán que no lo haya sido ya durante la partida 
	public int selectSpeaker(int gameId) {
		Assert.notNull(gameId);
		Game game;
		Collection<Player> hasBeenSpeakers;
		Collection<Player> playersGameNotSpeaker;
		int rand;
		Player speaker;
			
		game = findOne(gameId);
		hasBeenSpeakers = new ArrayList<Player>();
		playersGameNotSpeaker = game.getPlayers();
		
		for(Player p : game.getPlayers()){
			for(Round r: game.getRounds()){
				if (r.getSpeaker().equals(p)&& r.isEnded()){
					hasBeenSpeakers.add(p);
				}
			}
		}
			
		playersGameNotSpeaker.removeAll(hasBeenSpeakers);
		rand = (int) Math.floor(Math.random()*(playersGameNotSpeaker.size())); 
		speaker = ((List<Player>) playersGameNotSpeaker).get(rand);
		
		return speaker.getId();
	}

}
