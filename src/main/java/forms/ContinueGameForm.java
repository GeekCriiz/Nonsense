package forms;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

public class ContinueGameForm {
											// Fases en las que se usan (y si es el charlatán o el resto):
	private int gameId;						// Todas
	private String host;					// 0, 1 resto
	private List<Tupla> players;			// 0
	private int playersNumber;				// 0
	private int currentRound;				// 1, 2, 3
	private int rounds;						// 1, 2, 3
	private int currentQuestion;			// 1, 2, 3
	private int qNumber;					// 1, 2, 3
	private List<AnswerForm> lastAnswers;	// 1
	private QuestionForm question;			// 1, 2, 3
	private List<AnswerForm> answers;		// 2, 3
	private String answerPlayer;			// 2 resto
	private boolean speaker;				// 1, 2, 3
	private int phase;						// 0, 1, 2, 3
	
	@NotNull
	@Min(0)
	public int getGameId() {
		return gameId;
	}
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	
	public List<Tupla> getPlayers() {
		return players;
	}
	public void setPlayers(List<Tupla> players) {
		this.players = players;
	}
	
	@Min(0)
	public int getPlayersNumber() {
		return playersNumber;
	}
	public void setPlayersNumber(int playersNumber) {
		this.playersNumber = playersNumber;
	}
	
	@Range(min=0, max=8)
	public int getCurrentRound() {
		return currentRound;
	}
	public void setCurrentRound(int currentRound) {
		this.currentRound = currentRound;
	}
	
	@Range(min=0, max=8)
	public int getRounds() {
		return rounds;
	}
	public void setRounds(int rounds) {
		this.rounds = rounds;
	}
	 
	@Range(min=1, max=5)
	public int getCurrentQuestion() {
		return currentQuestion;
	}
	public void setCurrentQuestion(int currentQuestion) {
		this.currentQuestion = currentQuestion;
	}
	
	@Range(min=1, max=5)
	public int getQNumber() {
		return qNumber;
	}
	public void setQNumber(int qNumber) {
		this.qNumber = qNumber;
	}
	
	public List<AnswerForm> getLastAnswers() {
		return lastAnswers;
	}
	public void setLastAnswers(List<AnswerForm> lastAnswers) {
		this.lastAnswers = lastAnswers;
	}
	
	public QuestionForm getQuestion() {
		return question;
	}
	public void setQuestion(QuestionForm question) {
		this.question = question;
	}
	
	public List<AnswerForm> getAnswers() {
		return answers;
	}
	public void setAnswers(List<AnswerForm> answers) {
		this.answers = answers;
	}
	
	public String getAnswerPlayer() {
		return answerPlayer;
	}
	public void setAnswerPlayer(String answerPlayer) {
		this.answerPlayer = answerPlayer;
	}
	
	public boolean isSpeaker() {
		return speaker;
	}
	public void setSpeaker(boolean speaker) {
		this.speaker = speaker;
	}
	
	// 5 Fases:
	// 0: Partida sin empezar, sala de espera
	// 1: Charlatán escogiendo categoría (se podrán ver los resultados de la pregunta anterior)
	// 2: Jugadores respondiendo
	// 3: Jugadores votando
	// 4: Partida terminada
	@NotNull
	@Range(min=0, max=4)
	public int getPhase() {
		return phase;
	}
	public void setPhase(int phase) {
		this.phase = phase;
	}
}