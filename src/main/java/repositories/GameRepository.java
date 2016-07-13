package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Game;
import domain.Player;
import domain.Round;
import domain.Score;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer>{

	@Query("select p.games from Player p where p.id = ?1")
	Collection<Game> findAllGamesByPrincipal(int playerId);

	@Query("select g.scores from Game g where g.id = ?1")
	Collection<Score> findScores(int gameId);

	@Query("select g.rounds from Game g where g.id = ?1")
	Collection<Round> findRounds(int gameId);

	@Query("select g.players from Game g where g.id = ?1")
	Collection<Player> findPlayers(int gameId);

	@Query("select p.games from Player p where p.id = ?1")
	Collection<Game> findGamesByPlayer(int playerId);

	@Query("select g from Game g where g.catNumber = ?1 and g.playersNumber = ?2 and g.custom = false and ?3 not member of g.players and size(g.players) < g.playersNumber")
	Collection<Game> findOneByQuestionsAndPlayersNotMain(int questions, int players, Player main);

	@Query("select r.QNumber from Round r where r.game.id = ?1 and r.roundNumber = r.game.roundNumber")
	int findQNumber(int gameId);
	
	@Query("select p.games from Player p join p.requests r where r.status = 'PENDING' and p.id = ?1")
	Collection<Game> findPendingGamesByPrincipal(int playerId);

	@Query("select p.games from Player p join p.requests r where r.status = 'ACCEPTED' and p.id = ?1")
	Collection<Game> findEnCursoGamesByPrincipal(int playerId);
	

}
