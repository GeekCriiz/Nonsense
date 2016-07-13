package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import security.UserAccount;
import domain.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer>{

	//Player who has a specific userAccount
	@Query("select a from Player a where a.userAccount = ?1")
	Player findByUserAccount(UserAccount userAccount);

	@Query("select p.nickname, p.id from Player p where ((p.name like %?1%) or (p.surname like %?1%) or (p.nickname like %?1%))")
	List<Player> findAllByName(String name);

	@Query("select p from Player p where p.blocked = true")
	Collection<Player> findAllBlocked();

	@Query("select p from Player p where p.bans >0")
	Collection<Player> findAllWithBans();

	@Query("select p.following from Player p where p.id = ?1")
	Collection<Player> findAllFollowingByPrincipal(int playerId);

	@Query("select p.nickname, p.id from Player p")
	Collection<String> findAllByNickname();

	@Query("select p.nickname, p.id from Player p where p.blocked = true")
	Collection<String> findAllBlockedByNickname();
	
	@Query("select p.nickname, p.id from Player p where p.bans >0")
	Collection<String> findAllWithBansByNickname();

	@Query("select p from Player p where p.nickname = ?1")
	Player findByNickname(String nickname);
	
	@Query("select p.nickname, p.id from Player p where p.negatives/p.games.size >= 3")
	Collection<String> findAllCheaters();
	
	@Query("select r.speaker from Game g join g.rounds r where g.id = ?1 and r.roundNumber = 1")
	Player findHost(int gameId);

	@Query("select a.player from AnswerPlayer a where a.id = ?1")
	Player findByAnswer(int answerPlayerId);

	@Query("select r.speaker from Game g join g.rounds r where g.id = ?1 and r.roundNumber = ?2")
	Player findSpeaker(int gameId, int roundNumber);

	@Query("select p.nickname, p.id from Player p join p.following f where p.id = ?1")
	Collection<String> findAllFollowers(int id);

	@Query("select p.nickname, p.id from Player p join p.followers f where p.id = ?1")
	Collection<String> findAllFollowing(int id);
	
	// Puntuación de cada categoría de las partidas acabadas de un jugador -----------------------------------------
	@Query("select sum(sc.definition) from Score sc join sc.game.rounds r where sc.player.id = ?1 and sc.game.roundNumber = sc.game.rounds.size and r.roundNumber = sc.game.roundNumber and r.ended = true")
	int findPlayerDefinition(int playerId);
	
	@Query("select sum(sc.date) from Score sc join sc.game.rounds r where sc.player.id = ?1 and sc.game.roundNumber = sc.game.rounds.size and r.roundNumber = sc.game.roundNumber and r.ended = true")
	int findPlayerDate(int playerId);
	
	@Query("select sum(sc.acronym) from Score sc join sc.game.rounds r where sc.player.id = ?1 and sc.game.roundNumber = sc.game.rounds.size and r.roundNumber = sc.game.roundNumber and r.ended = true")
	int findPlayerAcronym(int playerId);
	
	@Query("select sum(sc.character_) from Score sc join sc.game.rounds r where sc.player.id = ?1 and sc.game.roundNumber = sc.game.rounds.size and r.roundNumber = sc.game.roundNumber and r.ended = true")
	int findPlayerCharacter_(int playerId);
	
	@Query("select sum(sc.film) from Score sc join sc.game.rounds r where sc.player.id = ?1 and sc.game.roundNumber = sc.game.rounds.size and r.roundNumber = sc.game.roundNumber and r.ended = true")
	int findPlayerFilm(int playerId);
	// --------------------------------------------------------------------------------------------------------------
}
