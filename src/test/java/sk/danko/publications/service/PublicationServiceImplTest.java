package sk.danko.publications.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import sk.danko.publications.entity.PublicationEntity;
import sk.danko.publications.enumerator.Language;
import sk.danko.publications.enumerator.PublicationType;
import sk.danko.publications.exception.PublicationNotFoundException;
import sk.danko.publications.repository.PublicationRepository;
import sk.danko.publications.service.impl.PublicationServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PublicationServiceImplTest {

    @Mock
    private PublicationRepository publicationRepository;

    @InjectMocks
    private PublicationServiceImpl publicationService;

    private PublicationEntity publicationEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        publicationEntity = new PublicationEntity();
        publicationEntity.setPublicationId(1L);
        publicationEntity.setTitle("Test Title");
        publicationEntity.setLanguage(Language.EN);
        publicationEntity.setType(PublicationType.BOOK);
    }

    @Test
    void testGetPublications() {
        when(publicationRepository.findAll()).thenReturn(List.of(publicationEntity));

        Iterable<PublicationEntity> result = publicationService.getPubnlications();

        assertNotNull(result);
        assertEquals(1, ((List<PublicationEntity>) result).size());
        verify(publicationRepository, times(1)).findAll();
    }

    @Test
    void testGetPublication_Found() {
        when(publicationRepository.findById(1L)).thenReturn(Optional.of(publicationEntity));

        PublicationEntity result = publicationService.getPublication(1L);

        assertNotNull(result);
        assertEquals("Test Title", result.getTitle());
        verify(publicationRepository, times(1)).findById(1L);
    }

    @Test
    void testGetPublication_NotFound() {
        when(publicationRepository.findById(99L)).thenReturn(Optional.empty());

        PublicationEntity result = publicationService.getPublication(99L);

        assertNull(result);
        verify(publicationRepository, times(1)).findById(99L);
    }

    @Test
    void testSave() {
        when(publicationRepository.save(any(PublicationEntity.class))).thenReturn(publicationEntity);

        Long id = publicationService.save(publicationEntity);

        assertEquals(1L, id);
        verify(publicationRepository, times(1)).save(publicationEntity);
    }

    @Test
    void testUpdate_Found() {
        PublicationEntity updated = new PublicationEntity();
        updated.setTitle("Updated Title");

        when(publicationRepository.findById(1L)).thenReturn(Optional.of(publicationEntity));
        when(publicationRepository.save(any(PublicationEntity.class))).thenReturn(publicationEntity);

        PublicationEntity result = publicationService.update(1L, updated);

        assertEquals("Updated Title", result.getTitle());
        verify(publicationRepository, times(1)).findById(1L);
        verify(publicationRepository, times(1)).save(publicationEntity);
    }

    @Test
    void testUpdate_NotFound() {
        when(publicationRepository.findById(99L)).thenReturn(Optional.empty());

        PublicationNotFoundException ex = assertThrows(
                PublicationNotFoundException.class,
                () -> publicationService.update(99L, new PublicationEntity())
        );

        assertTrue(ex.getMessage().contains("Publication is not exists with given id99"));
        verify(publicationRepository, times(1)).findById(99L);
    }

    @Test
    void testGetPublicationByLanguage() {
        when(publicationRepository.findByLanguage(Language.EN)).thenReturn(List.of(publicationEntity));

        List<PublicationEntity> result = publicationService.getPublicationByLanguage(Language.EN);

        assertEquals(1, result.size());
        assertEquals(Language.EN, result.get(0).getLanguage());
        verify(publicationRepository, times(1)).findByLanguage(Language.EN);
    }

    @Test
    void testGetPublicationByType() {
        when(publicationRepository.findByType(PublicationType.BOOK)).thenReturn(List.of(publicationEntity));

        List<PublicationEntity> result = publicationService.getPublicationByType(PublicationType.BOOK);

        assertEquals(1, result.size());
        assertEquals(PublicationType.BOOK, result.get(0).getType());
        verify(publicationRepository, times(1)).findByType(PublicationType.BOOK);
    }

    @Test
    void testDelete_Found() {
        when(publicationRepository.findById(1L)).thenReturn(Optional.of(publicationEntity));
        doNothing().when(publicationRepository).deleteById(1L);

        publicationService.delete(1L);

        verify(publicationRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDelete_NotFound() {
        when(publicationRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(PublicationNotFoundException.class, () -> publicationService.delete(99L));

        verify(publicationRepository, never()).deleteById(any());
    }
}
