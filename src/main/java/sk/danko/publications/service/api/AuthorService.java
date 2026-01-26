package sk.danko.publications.service.api;

import jakarta.annotation.Nullable;
import sk.danko.publications.entity.AuthorEntity;
import sk.danko.publications.entity.PublicationEntity;

public interface AuthorService {

    Iterable<AuthorEntity> getAuthors();

    @Nullable
    AuthorEntity getAuthor(Long id); //returns generated id

    Long save(AuthorEntity authorEntity);

    AuthorEntity update(Long id, AuthorEntity request);

    void delete(Long id);
}
