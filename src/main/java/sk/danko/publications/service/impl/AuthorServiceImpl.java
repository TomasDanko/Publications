package sk.danko.publications.service.impl;

import jakarta.annotation.Nullable;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.danko.publications.entity.AuthorEntity;
import sk.danko.publications.exception.AuthorNotFoundException;
import sk.danko.publications.repository.AuthorRepository;
import sk.danko.publications.service.api.AuthorService;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Iterable<AuthorEntity> getAuthors() {
        return authorRepository.findAll();
    }

    @Nullable
    @Override
    public AuthorEntity getAuthor(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author not found with id: " + id));
    }

    @Override
    public Long save(AuthorEntity authorEntity) {
        return authorRepository.save(authorEntity).getAuthorId();
    }

    @Override
    public AuthorEntity update(Long id, AuthorEntity request) {
        AuthorEntity entity = authorRepository.findById(id).orElseThrow(
                ()-> new AuthorNotFoundException("Author is not exists with given id" + id));

        if(request.getFullName() != null)
            entity.setFullName(request.getFullName());

        if(request.getPublications() != null)
            entity.setPublications(request.getPublications());

        return authorRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        AuthorEntity entity = authorRepository.findById(id).orElseThrow(
                () -> new AuthorNotFoundException("Author does not exist with id: " + id));
        authorRepository.deleteById(id);

    }
}
