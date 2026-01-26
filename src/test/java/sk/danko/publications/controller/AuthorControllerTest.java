package sk.danko.publications.controller;

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
import sk.danko.publications.service.api.AuthorService;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthorController.class)
@Import(TestSecurityConfig.class)
class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    @Test
    void testGetAuthor_found() throws Exception {
        AuthorEntity author = new AuthorEntity();
        author.setAuthorId(1L);
        author.setFullName("Tomas Danko");

        Mockito.when(authorService.getAuthor(1L)).thenReturn(author);

        mockMvc.perform(get("/api/author/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Tomas Danko"));
    }

    @Test
    void testGetAuthor_notFound() throws Exception {
        Mockito.when(authorService.getAuthor(99L)).thenReturn(null);

        mockMvc.perform(get("/api/author/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetAllAuthors() throws Exception {
        AuthorEntity a1 = new AuthorEntity();
        a1.setAuthorId(1L);
        a1.setFullName("Janko Hrasko");

        AuthorEntity a2 = new AuthorEntity();
        a2.setAuthorId(2L);
        a2.setFullName("Janka Hraskova");

        Mockito.when(authorService.getAuthors()).thenReturn(Arrays.asList(a1, a2));

        mockMvc.perform(get("/api/authors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fullName").value("Janko Hrasko"));
    }

    @Test
    void testCreateAuthor_success() throws Exception {
        Mockito.when(authorService.save(any(AuthorEntity.class))).thenReturn(1L);

        mockMvc.perform(post("/api/author")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fullName\":\"Matus Test\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }

    @Test
    void testCreateAuthor_fail() throws Exception {
        Mockito.when(authorService.save(any(AuthorEntity.class))).thenReturn(null);

        mockMvc.perform(post("/api/author")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fullName\":\"Matus Test\"}"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testUpdateAuthor_found() throws Exception {
        AuthorEntity existing = new AuthorEntity();
        existing.setAuthorId(1L);
        existing.setFullName("Stare Meno");

        Mockito.when(authorService.getAuthor(1L)).thenReturn(existing);

        mockMvc.perform(put("/api/author/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fullName\":\"Matus Test\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    void testUpdateAuthor_notFound() throws Exception {
        Mockito.when(authorService.getAuthor(99L)).thenReturn(null);

        mockMvc.perform(put("/api/author/99")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fullName\":\"Matus Test\"}"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Author with id: 99 was not found"));
    }

    @Test
    void testDeleteAuthor_found() throws Exception {
        AuthorEntity existing = new AuthorEntity();
        existing.setAuthorId(1L);

        Mockito.when(authorService.getAuthor(1L)).thenReturn(existing);

        mockMvc.perform(delete("/api/author/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteAuthor_notFound() throws Exception {
        Mockito.when(authorService.getAuthor(99L)).thenReturn(null);

        mockMvc.perform(delete("/api/author/99"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Author with id: 99 was not found"));
    }
}
