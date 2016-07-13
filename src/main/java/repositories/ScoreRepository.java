package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import domain.Score;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Integer>{
	
	@Query("select s from Score s where s.player.id = ?1 and s.game.id = ?2")
	Score findScore(int playerId, int gameId);

	@Query("select s from Score s where s.game.id = ?1 and s.current >= all(select sc.current from Score sc where sc.game.id = ?1)")
	List<Score> findBest(int gameId);
	
}
