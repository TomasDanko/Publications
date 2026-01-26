package sk.danko.publications.service.request;

import sk.danko.publications.entity.PublicationEntity;

import java.util.Set;

public class UpdateAuthorRequest {

    private Set<PublicationEntity> publications;

    public UpdateAuthorRequest(Set<PublicationEntity> publications) {
        this.publications = publications;
    }

    public Set<PublicationEntity> getPublications() {
        return publications;
    }

    public void setPublications(Set<PublicationEntity> publications) {
        this.publications = publications;
    }
}
