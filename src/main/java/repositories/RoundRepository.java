package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Round;

@Repository
public interface RoundRepository extends JpaRepository<Round, Integer>{

	@Query("select r from Round r where r.game.id = ?1 and r.roundNumber = ?2")
	Round findOneByGame(int gameId, int roundNumber);
	
}
