package sk.danko.publications.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sk.danko.publications.entity.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    @Query("SELECT u FROM UserEntity u WHERE u.username = ?1")
    UserEntity findByUsername(String username);
}
