package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Orderr;

@Repository
public interface OrderrRepository extends JpaRepository<Orderr, Integer>{
	
	@Query("select o from Orderr o where o.round.id = ?1 and o.QNumber = ?2")
	Orderr findOneByRound(int roundId, int qNumber);
	
}
