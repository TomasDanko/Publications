package sk.danko.publications.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import sk.danko.publications.entity.PublicationEntity;

import java.util.HashSet;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorModel {

    private Long authorId;

    private String fullName;

    private Set<PublicationEntity> publications = new HashSet<>();

    public AuthorModel(Long authorId, String fullName, Set<PublicationEntity> publications) {
        this.authorId = authorId;
        this.fullName = fullName;
        this.publications = publications;
    }

    public AuthorModel() {
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Set<PublicationEntity> getPublications() {
        return publications;
    }

    public void setPublications(Set<PublicationEntity> publications) {
        this.publications = publications;
    }
}
