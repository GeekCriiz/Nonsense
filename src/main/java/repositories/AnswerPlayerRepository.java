package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.AnswerPlayer;
import domain.Player;

@Repository
public interface AnswerPlayerRepository extends JpaRepository<AnswerPlayer, Integer>{

	@Query("select o.answerPlayers from Orderr o where o.QNumber = ?3 and o.round.roundNumber = ?2 and o.round.game.id = ?1")
	List<AnswerPlayer> findAnswers(int gameId, int roundNumber, int qNumber);
	
	@Query("select ap.player from Orderr o join o.answerPlayers ap where o.QNumber = ?3 and o.round.roundNumber = ?2 and o.round.game.id = ?1")
	List<Player> findPlayersByOrderr(int gameId, int roundNumber, int qNumber);
	
	@Query("select ap from Orderr o join o.answerPlayers ap where ap.player = ?4 and o.QNumber = ?3 and o.round.roundNumber = ?2 and o.round.game.id = ?1")
	AnswerPlayer findByPlayer(int gameId, int roundNumber, int qNumber, int playerId);
	
}
