package sk.danko.publications.service.api;

import jakarta.annotation.Nullable;
import sk.danko.publications.entity.PublicationEntity;
import sk.danko.publications.enumerator.Language;
import sk.danko.publications.enumerator.PublicationType;

import java.util.List;

public interface PublicationService {

    Iterable<PublicationEntity> getPubnlications();

    @Nullable
    PublicationEntity getPublication(Long id); //returns generated id

    Long save(PublicationEntity publicationEntity);

    PublicationEntity update(Long id, PublicationEntity request);

    List<PublicationEntity> getPublicationByLanguage(Language language);

    List<PublicationEntity> getPublicationByType(PublicationType publicationType);

    void delete(Long id);
}
