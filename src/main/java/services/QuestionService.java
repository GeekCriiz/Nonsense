package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.QuestionRepository;
import domain.AnswerPlayer;
import domain.Game;
import domain.Orderr;
import domain.Player;
import domain.Question;
import domain.Round;

@Service
@Transactional
public class QuestionService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private QuestionRepository questionRepository;

	// Supporting services -----------------------------------------------------
	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private AnswerPlayerService answerPlayerService;
	
	@Autowired
	private RoundService roundService;

	// Constructor -----------------------------------------------------

	public QuestionService() {
		super();
	}

	// Simple CRUD methods -----------------------------------------------------

	public Collection<Question> findAll() {
		Collection<Question> result;

		result = questionRepository.findAll();

		return result;
	}

	public Question findOne(int questionId) {
		Assert.notNull(questionId);
		Question result;

		result = questionRepository.findOne(questionId);

		return result;
	}

	// Other business methods -----------------------------------------------------

	//Método que devuelve una pregunta de la categoría seleccionada por el charlatán
	public int selectCategory(String category, int roundId) {
		Question question;
		Collection<Question> questions;
		Player principal;
		int rand;
		Round round;
		Orderr orderr;
		int qNumberCurrent;
		
		principal = playerService.findByPrincipal();
		Assert.notNull(principal);		
		round = roundService.findOne(roundId);
		qNumberCurrent = round.getQNumber();
				
		// Comprobrar que el player es el charlatan de la ronda actual (que aún no ha acabado) 
		Assert.isTrue(round.getSpeaker().equals(principal) && !round.isEnded());			
		
		questions = questionRepository.findQuestionByCategory(category);
		
		rand = (int) Math.floor(Math.random()*(questions.size())); 

		question = ((List<Question>) questions).get(rand);
		
		for (Orderr o : round.getOrderrs()){
			if (o.getQNumber()==round.getQNumber()){
				orderr = o;
				//Asignarle la pregunta a la ordeer correspondiente de la ronda.
				orderr.setQuestion(question);
			}
		}
			
		//A la ronda se le aumenta en 1 qnumber
		round.setQNumber(qNumberCurrent+1);

		return question.getId();
	}
	
	//Método que muestra las categorías sin elegir de una ronda	
	public Collection<String> categoriesAvailable(Round round){
		Collection<String> result;
		Collection<String> categoriesUsed;
		Collection<Orderr> orderrs;
		String category;
		
		orderrs = round.getOrderrs();
		result = questionRepository.allCategories();	
		categoriesUsed = new ArrayList<String>();
		
		for (Orderr o : orderrs){
			category = o.getQuestion().getCategory();
			categoriesUsed.add(category);
		}
		
		result.removeAll(categoriesUsed);
		
		return result;		
		
	}
	
	//Método que controla que un mismo juego no te puedan salir una pregunta mas de una vez
	public Collection<Question> questionsNotUsed(Game game){
		Collection<Question> result;
		Collection<Question> questionsUsed;
		Collection<Round> rounds;
		Question question;
		
		rounds = game.getRounds();
		result = questionRepository.findAll();	
		questionsUsed = new ArrayList<Question>();
		
		for (Round r : rounds){
			for (Orderr o : r.getOrderrs()){
				question = o.getQuestion();
				questionsUsed.add(question);
			}
		
		}
		
		result.removeAll(questionsUsed);
		
		return result;		
		
	}
	
	//Charlatan: Elegir la respuesta de un jugador que más se parezca (a su criterio) a la correcta
	public int choosePerfect(int answerPlayerId, int roundId){
		Player principal;
		AnswerPlayer answerPlayer;
		Round round;
		
		principal = playerService.findByPrincipal();
		Assert.notNull(principal);	
		round = roundService.findOne(roundId);

		// Comprobrar que el player es el charlatan de la ronda actual (que aún no ha acabado) 
		Assert.isTrue(round.getSpeaker().equals(principal) && !round.isEnded());		
		
		answerPlayer = answerPlayerService.findOne(answerPlayerId);
		
		answerPlayer.setPerfect(true);

		//comprobar que solo una answerPlayer tiene el perfect a true
		boolean isUnique = true;
		for(Orderr o : round.getOrderrs()){
			if(o.getQuestion()!=null){
				for(AnswerPlayer a : o.getQuestion().getAnswerPlayers()){
					if(!a.isPerfect()){
						//El 1º que cumpla el if cumple el assert, el 2º ya no
						Assert.isTrue(isUnique);
						isUnique = false;
					}
				}
			}
		}
		
		return answerPlayer.getId();

	}

	public Question findByQNumber(int gameId, int roundNumber, int qNumber) {
		Question result;
		
		result = questionRepository.findByQNumber(gameId, roundNumber, qNumber);
		
		return result;
	}
}
