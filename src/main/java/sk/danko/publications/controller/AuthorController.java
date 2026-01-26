package sk.danko.publications.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.danko.publications.entity.AuthorEntity;
import sk.danko.publications.entity.PublicationEntity;
import sk.danko.publications.service.api.AuthorService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthorController {

    public static final String AUTHORS_URI = "/authors";
    public static final String AUTHOR_URI = "/author/{id}";
    public static final String CREATE_AUTHOR_URI = "/author";

    private static final Logger LOG = LoggerFactory.getLogger(AuthorController.class);

    @Autowired
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping(AUTHOR_URI)
    public ResponseEntity get(@PathVariable("id") Long id) {
        AuthorEntity entity = authorService.getAuthor(id);
        if (entity == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(entity, HttpStatus.OK);
        }
    }

    @GetMapping(AUTHORS_URI)
    public ResponseEntity getAll() {
        Iterable<AuthorEntity> authorList = authorService.getAuthors();
        System.out.println(authorList);
        return new ResponseEntity<>(authorList, HttpStatus.OK);

    }

    @PostMapping(value = CREATE_AUTHOR_URI,
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity save(@RequestBody @Valid AuthorEntity entity) {

        Long id = authorService.save(entity);
        if (id != null) {
            return new ResponseEntity<>(id, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @PutMapping(value = AUTHOR_URI, consumes = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity update(@PathVariable("id") long id, @RequestBody AuthorEntity request){

        if(authorService.getAuthor(id) != null){
            authorService.update(id, request);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author with id: " + id + " " +  "was not found");
        }

    }

    @DeleteMapping(AUTHOR_URI)
    public ResponseEntity delete(@PathVariable("id") long id) {
        if (authorService.getAuthor(id) != null) {
            authorService.delete(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author with id: " + id + " " + "was not found");
        }
    }
}


