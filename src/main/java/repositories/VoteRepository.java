package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Integer>{

	// Todos los votos de una pregunta (orderr)
	@Query("select vo from Vote a join a.answerPlayer.orderr.answerPlayers ap join ap.votes vo where a.id = ?1")
	Collection<Vote> allOrderrVotesByVote(int voteId);
	
	// Voto de un jugador en una pregunta (si es null, aun no ha votado)
	@Query("select vo from Vote vo where vo.player.id = ?1 and vo.answerPlayer.orderr.id = ?2")
	Vote hasVoted(int playerId, int orderrId);
	
	// Voto del jugador que sea según la partida, ronda y pregunta
	@Query("select a from Vote a join a.player.games g join g.rounds r join r.orderrs o where a.player.id = ?1 and "
			+ "g.id = ?2 and r.roundNumber = ?3 and o.QNumber = ?4")
	Vote findVoteByMoment(int playerId, int gameId, int roundNumber, int qNumber);
	
}
