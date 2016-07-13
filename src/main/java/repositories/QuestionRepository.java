package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer>{

	@Query("select q from Question q where q.category = ?1")
	Collection<Question> findQuestionByCategory(String category);

	@Query("select distinct q.category from Question q")
	Collection<String> allCategories();

	@Query("select o.question from Orderr o where o.QNumber = ?3 and o.round.roundNumber = ?2 and o.round.game.id = ?1")
	Question findByQNumber(int gameId, int roundNumber, int qNumber);
	
}
