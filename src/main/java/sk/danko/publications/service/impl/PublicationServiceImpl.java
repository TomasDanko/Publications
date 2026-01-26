package sk.danko.publications.service.impl;

import jakarta.annotation.Nullable;
import org.springframework.stereotype.Service;
import sk.danko.publications.entity.PublicationEntity;
import sk.danko.publications.enumerator.Language;
import sk.danko.publications.enumerator.PublicationType;
import sk.danko.publications.exception.PublicationNotFoundException;
import sk.danko.publications.repository.PublicationRepository;
import sk.danko.publications.service.api.PublicationService;

import java.util.List;
import java.util.Optional;

@Service
public class PublicationServiceImpl implements PublicationService {

    private final PublicationRepository publicationRepository;

    public PublicationServiceImpl(PublicationRepository publicationRepository) {
        this.publicationRepository = publicationRepository;
    }

    @Override
    public Iterable<PublicationEntity> getPubnlications() {
        return publicationRepository.findAll();
    }

    @Nullable
    @Override
    public PublicationEntity getPublication(Long id) {
        Optional<PublicationEntity> result = publicationRepository.findById(id);
        return result.orElse(null);
    }

    @Override
    public Long save(PublicationEntity publicationEntity) {
        return publicationRepository.save(publicationEntity).getPublicationId();
    }

    @Override
    public PublicationEntity update(Long id, PublicationEntity request) {
        PublicationEntity entity = publicationRepository.findById(id).orElseThrow(
                ()-> new PublicationNotFoundException("Publication is not exists with given id" + id));

        if(request.getAuthors() != null)
            entity.setAuthors(request.getAuthors());

        if(request.getCategories() != null)
            entity.setCategories(request.getCategories());

        if(request.getEdition() != null)
            entity.setEdition(request.getEdition());

        if(request.getPageCount() != null)
            entity.setPageCount(request.getPageCount());

        if(request.getAbstractText() != null)
            entity.setAbstractText(request.getAbstractText());

        if(request.getPublisher() != null)
            entity.setPublisher(request.getPublisher());

        if(request.getPublishedDate() != null)
            entity.setPublishedDate(request.getPublishedDate());

        if(request.getLanguage() != null)
            entity.setLanguage(request.getLanguage());

        if(request.getType() != null)
            entity.setType(request.getType());

        if(request.getTitle() != null)
            entity.setTitle(request.getTitle());


        return publicationRepository.save(entity);
    }

    @Override
    public List<PublicationEntity> getPublicationByLanguage(Language language) {
        return publicationRepository.findByLanguage(language);
    }

    @Override
    public List<PublicationEntity> getPublicationByType(PublicationType publicationType) {
        return publicationRepository.findByType(publicationType);
    }

    @Override
    public void delete(Long id) {
        PublicationEntity entity = publicationRepository.findById(id).orElseThrow(
                ()-> new PublicationNotFoundException("Publication is not exists with given id" + id));
        publicationRepository.deleteById(id);


    }


}
