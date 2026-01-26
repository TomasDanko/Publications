package sk.danko.publications.service.request;

import org.springframework.lang.NonNull;
import sk.danko.publications.enumerator.Language;
import sk.danko.publications.enumerator.PublicationType;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class UpdatePublicationRequest {

    @NonNull
    private String title;

    @NonNull
    private Set<String> authors;

    @NonNull
    private PublicationType type;

    @NonNull
    private Language language;

    @NonNull
    private Date publishedDate;

    @NonNull
    private String publisher;

    @NonNull
    private String abstractText;

    @NonNull
    private Integer pageCount;

    @NonNull
    private String edition;

    @NonNull
    private Set<String> categories;

    public UpdatePublicationRequest(@NonNull String title, @NonNull Set<String> authors, @NonNull PublicationType type, @NonNull Language language, @NonNull Date publishedDate, @NonNull String publisher, @NonNull String abstractText, @NonNull Integer pageCount, @NonNull String edition, @NonNull Set<String> categories) {
        this.title = title;
        this.authors = authors;
        this.type = type;
        this.language = language;
        this.publishedDate = publishedDate;
        this.publisher = publisher;
        this.abstractText = abstractText;
        this.pageCount = pageCount;
        this.edition = edition;
        this.categories = categories;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public Set<String> getAuthors() {
        return authors;
    }

    public void setAuthors(@NonNull Set<String> authors) {
        this.authors = authors;
    }

    @NonNull
    public PublicationType getType() {
        return type;
    }

    public void setType(@NonNull PublicationType type) {
        this.type = type;
    }

    @NonNull
    public Language getLanguage() {
        return language;
    }

    public void setLanguage(@NonNull Language language) {
        this.language = language;
    }

    @NonNull
    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(@NonNull Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    @NonNull
    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(@NonNull String publisher) {
        this.publisher = publisher;
    }

    @NonNull
    public String getAbstractText() {
        return abstractText;
    }

    public void setAbstractText(@NonNull String abstractText) {
        this.abstractText = abstractText;
    }

    @NonNull
    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(@NonNull Integer pageCount) {
        this.pageCount = pageCount;
    }

    @NonNull
    public String getEdition() {
        return edition;
    }

    public void setEdition(@NonNull String edition) {
        this.edition = edition;
    }

    @NonNull
    public Set<String> getCategories() {
        return categories;
    }

    public void setCategories(@NonNull Set<String> categories) {
        this.categories = categories;
    }
}
