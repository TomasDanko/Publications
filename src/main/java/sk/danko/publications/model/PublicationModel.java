package sk.danko.publications.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import sk.danko.publications.entity.AuthorEntity;
import sk.danko.publications.enumerator.Language;
import sk.danko.publications.enumerator.PublicationType;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PublicationModel {

    private Long publicationId;

    private String title;

    private Set<AuthorEntity> authors = new HashSet<>();

    private PublicationType type;

    private Language language;

    private Date publishedDate;

    private String publisher;

    private String issn;

    private String abstractText;

    private Integer pageCount;

    private String edition;

    private Set<String> categories = new HashSet<>();

    public PublicationModel(Long publicationId, String title, Set<AuthorEntity> authors, PublicationType type, Language language, Date publishedDate, String publisher, String issn, String abstractText, Integer pageCount, String edition, Set<String> categories) {
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

    public PublicationModel() {
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

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
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

    public Set<String> getCategories() {
        return categories;
    }

    public void setCategories(Set<String> categories) {
        this.categories = categories;
    }
}
