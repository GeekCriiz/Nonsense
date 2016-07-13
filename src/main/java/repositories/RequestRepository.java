package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer>{

	@Query("select a from Request a where a.player.id = ?1 and a.status = 'PENDING'")
	Collection<Request> findAllPendingByPrincipal(int id);

	
}
