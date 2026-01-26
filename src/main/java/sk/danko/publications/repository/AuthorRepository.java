package sk.danko.publications.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.danko.publications.entity.AuthorEntity;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {
}
