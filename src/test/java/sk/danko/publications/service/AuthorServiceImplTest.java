package sk.danko.publications.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sk.danko.publications.entity.AuthorEntity;
import sk.danko.publications.exception.AuthorNotFoundException;
import sk.danko.publications.repository.AuthorRepository;
import sk.danko.publications.service.impl.AuthorServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthorServiceImplTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorServiceImpl authorService;

    private AuthorEntity author;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

        author = new AuthorEntity();
        author.setAuthorId(1L);
        author.setFullName("Tomas Danko");
    }

    @Test
    void testForGetAuthors(){
        List<AuthorEntity> authors = List.of(author);
        when(authorRepository.findAll()).thenReturn(authors);

        Iterable<AuthorEntity> result = authorService.getAuthors();

        assertNotNull(result);
        assertEquals(1, ((List<AuthorEntity>) result).size());
        verify(authorRepository, times(1)).findAll();
    }

    @Test
    void testGetAuthorFound(){
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        AuthorEntity result = authorService.getAuthor(1L);

        assertNotNull(result);
        assertEquals("Tomas Danko", result.getFullName());
        verify(authorRepository).findById(1L);
    }

    @Test
    void testGetAuthorNotFound(){
        when(authorRepository.findById(99L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> {
                        authorService.getAuthor(99L);
                });

        assertEquals("Author not found with id: 99", exception.getMessage());
    }

    @Test
    void testSaveAuthor(){
        when(authorRepository.save(author)).thenReturn(author);

        Long id = authorService.save(author);

        assertEquals(1L, id);
        verify(authorRepository).save(author);
    }

    @Test
    void testUpdateAuthor(){
        AuthorEntity updatedRequest = new AuthorEntity();
        updatedRequest.setFullName("Updated Name");

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(authorRepository.save(any(AuthorEntity.class))).thenReturn(author);

        AuthorEntity result = authorService.update(1L, updatedRequest);

        assertEquals("Updated Name", result.getFullName());
        verify(authorRepository).save(author);
    }

    @Test
    void testUpdateAuthorNotFound() {
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());

        AuthorNotFoundException exception = assertThrows(AuthorNotFoundException.class, () -> {
            authorService.update(1L, author);
        });

        assertEquals("Author is not exists with given id1", exception.getMessage());
    }

    @Test
    void testDeleteAuthor() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        authorService.delete(1L);

        verify(authorRepository).deleteById(1L);
    }

    @Test
    void testDeleteAuthorNotFound() {
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());

        AuthorNotFoundException exception = assertThrows(AuthorNotFoundException.class, () -> {
            authorService.delete(1L);
        });

        assertEquals("Author does not exist with id 1", exception.getMessage());
    }


    }



