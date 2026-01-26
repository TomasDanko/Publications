package sk.danko.publications.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import sk.danko.publications.TestSecurityConfig;
import sk.danko.publications.entity.AuthorEntity;
import sk.danko.publications.entity.PublicationEntity;
import sk.danko.publications.enumerator.Language;
import sk.danko.publications.enumerator.PublicationType;
import sk.danko.publications.service.api.PublicationService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PublicationController.class)
@Import(TestSecurityConfig.class)
class PublicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PublicationService publicationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetPublication_found() throws Exception {
        PublicationEntity entity = new PublicationEntity();
        entity.setPublicationId(1L);
        entity.setTitle("Test Publication");

        when(publicationService.getPublication(1L)).thenReturn(entity);

        mockMvc.perform(get("/api/publication/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Publication"));
    }

    @Test
    void testGetPublication_notFound() throws Exception {
        when(publicationService.getPublication(1L)).thenReturn(null);

        mockMvc.perform(get("/api/publication/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetAllPublications() throws Exception {
        PublicationEntity p1 = new PublicationEntity();
        p1.setPublicationId(1L);
        p1.setTitle("First");

        PublicationEntity p2 = new PublicationEntity();
        p2.setPublicationId(2L);
        p2.setTitle("Second");

        when(publicationService.getPubnlications()).thenReturn(Arrays.asList(p1, p2));

        mockMvc.perform(get("/api/publications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("First"))
                .andExpect(jsonPath("$[1].title").value("Second"));
    }

    @Test
    void testSavePublication_success() throws Exception {
        PublicationEntity entity = new PublicationEntity();
        entity.setTitle("New Publication");
        entity.setPublisher("New Publisher");
        entity.setType(PublicationType.BOOK);
        entity.setEdition("Fiest Edition");
        entity.setLanguage(Language.SK);
//        entity.setCategories(Collections.singleton("Daily"));
        entity.setPublicationId(1L);
        entity.setAbstractText("Abstract");
        entity.setPageCount(100);
        entity.setAuthors(Collections.singleton(new AuthorEntity()));
        entity.setIssn("123-45");
        entity.setPublishedDate(LocalDate.now());
        when(publicationService.save(any(PublicationEntity.class))).thenReturn(10L);

        mockMvc.perform(post("/api/publication")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(entity)))
                .andExpect(status().isCreated())
                .andExpect(content().string("10"));
    }

    @Test
    void testSavePublication_internalError() throws Exception {
        PublicationEntity entity = new PublicationEntity();
        entity.setTitle("New Publication");
        entity.setPublisher("New Publisher");
        entity.setType(PublicationType.BOOK);
        entity.setEdition("Fiest Edition");
        entity.setLanguage(Language.SK);
//        entity.setCategories(Collections.singleton("Daily"));
        entity.setPublicationId(1L);
        entity.setAbstractText("Abstract");
        entity.setPageCount(100);
        entity.setAuthors(Collections.singleton(new AuthorEntity()));
        entity.setIssn("123-45");
        entity.setPublishedDate(LocalDate.now());
       when(publicationService.save(any(PublicationEntity.class))).thenReturn(null);

        mockMvc.perform(post("/api/publication")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(entity)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testUpdatePublication_success() throws Exception {
        PublicationEntity entity = new PublicationEntity();
        entity.setTitle("New Publication");
        entity.setPublisher("New Publisher");
        entity.setType(PublicationType.BOOK);
        entity.setEdition("Fiest Edition");
        entity.setLanguage(Language.SK);
//        entity.setCategories(Collections.singleton("Daily"));
        entity.setPublicationId(1L);
        entity.setAbstractText("Abstract");
        entity.setPageCount(100);
        entity.setAuthors(Collections.singleton(new AuthorEntity()));
        entity.setIssn("123-45");
        entity.setPublishedDate(LocalDate.now());

        when(publicationService.getPublication(1L)).thenReturn(entity);

        mockMvc.perform(put("/api/publication/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(entity)))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    void testUpdatePublication_notFound() throws Exception {
        PublicationEntity entity = new PublicationEntity();
        entity.setTitle("Updated Pub");

        when(publicationService.getPublication(1L)).thenReturn(null);

        mockMvc.perform(put("/api/publication/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(entity)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Publication with id: 1 was not found"));
    }

    @Test
    void testGetByLanguage() throws Exception {
        PublicationEntity p1 = new PublicationEntity();
        p1.setPublicationId(1L);
        p1.setLanguage(Language.SK);

        List<PublicationEntity> list = List.of(p1);

        when(publicationService.getPublicationByLanguage(Language.SK)).thenReturn(list);

        mockMvc.perform(get("/api/publication/language")
                        .param("language", "SK"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].language").value("SK"));
    }

    @Test
    void testGetByType() throws Exception {
        PublicationEntity p1 = new PublicationEntity();
        p1.setPublicationId(1L);
        p1.setType(PublicationType.MAGAZINE);

        List<PublicationEntity> list = List.of(p1);

        when(publicationService.getPublicationByType(PublicationType.MAGAZINE)).thenReturn(list);

        mockMvc.perform(get("/api/publication/type")
                        .param("type", "MAGAZINE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type").value("MAGAZINE"));
    }

    @Test
    void testDeletePublication_success() throws Exception {
        PublicationEntity entity = new PublicationEntity();
        entity.setPublicationId(1L);

        when(publicationService.getPublication(1L)).thenReturn(entity);

        mockMvc.perform(delete("/api/publication/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeletePublication_notFound() throws Exception {
        when(publicationService.getPublication(1L)).thenReturn(null);

        mockMvc.perform(delete("/api/publication/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Publication with id: 1 was not found"));
    }
}

