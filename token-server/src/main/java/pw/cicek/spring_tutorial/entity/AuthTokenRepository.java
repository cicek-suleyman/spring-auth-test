package pw.cicek.spring_tutorial.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface AuthTokenRepository extends JpaRepository<AuthToken, Long> {
	Optional<AuthToken> findByToken(String token);
	Set<AuthToken> findByUser(User user);
	Page<AuthToken> findByUser(User user, Pageable pageable);
}
