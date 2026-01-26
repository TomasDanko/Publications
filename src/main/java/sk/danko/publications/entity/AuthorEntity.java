package sk.danko.publications.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "author")
public class AuthorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private Long authorId;

    @Column(name = "full_name", nullable = false, length = 200)
    private String fullName;

    @JsonBackReference
    @ManyToMany(mappedBy = "authors")
    private Set<PublicationEntity> publications = new HashSet<>();

    public AuthorEntity(Long author_id, String fullName, Set<PublicationEntity> publications) {
        this.authorId = author_id;
        this.fullName = fullName;
        this.publications = publications;
    }

    public AuthorEntity() {

    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long author_id) {
        this.authorId = author_id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorEntity that = (AuthorEntity) o;
        return Objects.equals(authorId, that.authorId) && Objects.equals(fullName, that.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorId, fullName);
    }
}

