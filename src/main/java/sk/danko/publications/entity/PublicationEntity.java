package sk.danko.publications.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import sk.danko.publications.enumerator.Language;
import sk.danko.publications.enumerator.PublicationType;

import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "publication")
public class PublicationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "publication_id")
    private Long publicationId;

    @NotNull(message = "Title is required")
    @NotBlank(message = "Title not be empty")
    @Column(name = "title")
    private String title;


//    @JsonManagedReference
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "publication_author",
            joinColumns = @JoinColumn(name = "publication_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<AuthorEntity> authors = new HashSet<>();

    @NotNull(message = "Publication type is required")
    @Column(name = "publicationType")
    @Enumerated(EnumType.STRING)
    private PublicationType type;

    @NotNull(message = "Language type is required")
    @Column(name = "language")
    @Enumerated(EnumType.STRING)
    private Language language;

    @NotNull(message = "Date of publication is required")
    @Column(name = "publishedDate", unique = true)
    private LocalDate publishedDate;

    @NotNull(message = "Publisher of publication is required")
    @NotBlank(message = "Publisher of publication not be empty")
    @Column(name = "publisher")
    private String publisher;


    @NotNull(message = "ISSN is required")
    @NotBlank(message = "ISSN not be empty")
    @Column(name = "issn")
    private String issn;

    @NotNull(message = "Abstract text is required")
    @NotBlank(message = "Abstract text not be empty")
    @Column(name = "abstractText")
    private String abstractText;
    @NotNull(message = "Page count text is required")
    @Column(name = "pageCount")
    private Integer pageCount;
    @NotNull(message = "Edition count text is required")
    @NotBlank(message = "Edition count text not be empty")
    @Column(name = "edition")
    private String edition;

    @ManyToMany
    @JoinTable(
            name = "publication_category",
            joinColumns = @JoinColumn(name = "publication_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<CategoryEntity> categories = new HashSet<>();


    public PublicationEntity() {
    }


    public PublicationEntity(Long publicationId, String title, Set<AuthorEntity> authors, PublicationType type, Language language, LocalDate publishedDate, String publisher, String issn, String abstractText, Integer pageCount, String edition, Set<CategoryEntity> categories) {
        this.publicationId = publicationId;
        this.title = title;
        this.authors = authors;
        this.type = type;
        this.language = language;
        this.publishedDate = publishedDate;
        this.publisher = publisher;
        this.issn = issn;
        this.abstractText = abstractText;
        this.pageCount = pageCount;
        this.edition = edition;
        this.categories = categories;
    }

    public Long getPublicationId() {
        return publicationId;
    }

    public void setPublicationId(Long publicationId) {
        this.publicationId = publicationId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<AuthorEntity> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<AuthorEntity> authors) {
        this.authors = authors;
    }

    public PublicationType getType() {
        return type;
    }

    public void setType(PublicationType type) {
        this.type = type;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public String getAbstractText() {
        return abstractText;
    }

    public void setAbstractText(String abstractText) {
        this.abstractText = abstractText;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public Set<CategoryEntity> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoryEntity> categories) {
        this.categories = categories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PublicationEntity entity = (PublicationEntity) o;
        return Objects.equals(publicationId, entity.publicationId) && Objects.equals(title, entity.title) && type == entity.type && language == entity.language && Objects.equals(publishedDate, entity.publishedDate) && Objects.equals(publisher, entity.publisher) && Objects.equals(issn, entity.issn) && Objects.equals(abstractText, entity.abstractText) && Objects.equals(pageCount, entity.pageCount) && Objects.equals(edition, entity.edition) && Objects.equals(categories, entity.categories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(publicationId, title, type, language, publishedDate, publisher, issn, abstractText, pageCount, edition, categories);
    }
}
