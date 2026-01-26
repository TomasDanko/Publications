package sk.danko.publications.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.danko.publications.entity.PublicationEntity;
import sk.danko.publications.enumerator.Language;
import sk.danko.publications.enumerator.PublicationType;

import java.util.List;

@Repository
public interface PublicationRepository extends JpaRepository<PublicationEntity, Long> {

    List<PublicationEntity> findByLanguage(Language language);
    List<PublicationEntity> findByType(PublicationType type);
}
