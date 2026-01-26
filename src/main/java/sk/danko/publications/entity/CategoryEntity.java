package sk.danko.publications.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "category")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "genre")
    @NotNull(message = "Genre is required")
    @NotBlank(message = "Genre not be empty")
    private String genre;

    @ManyToMany(mappedBy = "categories")
    private Set<PublicationEntity> publications = new HashSet<>();

    public CategoryEntity() {
    }

    public CategoryEntity(Long categoryId, String genre, Set<PublicationEntity> publications) {
        this.categoryId = categoryId;
        this.genre = genre;
        this.publications = publications;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
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
        CategoryEntity that = (CategoryEntity) o;
        return Objects.equals(categoryId, that.categoryId) && Objects.equals(genre, that.genre) && Objects.equals(publications, that.publications);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, genre, publications);
    }
}
