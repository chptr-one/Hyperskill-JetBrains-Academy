package engine.repository;

import engine.model.Completion;
import engine.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompletionRepository extends PagingAndSortingRepository<Completion, Long> {

    @Query("SELECT c FROM Completion c where c.user.email = :username order by c.completedAt desc")
    Page<Completion> findByUser(String username, Pageable pageable);
}
