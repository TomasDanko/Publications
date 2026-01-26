package sk.danko.publications.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.danko.publications.entity.PublicationEntity;
import sk.danko.publications.enumerator.Language;
import sk.danko.publications.enumerator.PublicationType;
import sk.danko.publications.service.api.PublicationService;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class PublicationController {

    public static final String PUBLICATIONS_URI = "/publications";

    public static final String CREATE_PUBLICATION_URI = "/publication";

    public static final String PUBLICATION_URI = "/publication/{id}";

    public static final String LANGUAGE_URI = "/publication/language";

    public static final String TYPE_URI = "/publication/type";

    private static final Logger LOG = LoggerFactory.getLogger(PublicationController.class);

    @Autowired
    private final PublicationService publicationService;


    public PublicationController(PublicationService publicationService) {
        this.publicationService = publicationService;
    }

    @GetMapping(PUBLICATION_URI)
    public ResponseEntity get(@PathVariable("id") Long id) {
        PublicationEntity entity = publicationService.getPublication(id);
        if (entity == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(entity, HttpStatus.OK);
        }
    }


    @GetMapping(PUBLICATIONS_URI)
    public ResponseEntity getAll(){
        Iterable<PublicationEntity> publicationsList = publicationService.getPubnlications();
        System.out.println(publicationsList);
        return new ResponseEntity<>(publicationsList, HttpStatus.OK);

    }


    @PostMapping(value = CREATE_PUBLICATION_URI,
            consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_JSON_UTF8_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity save(@RequestBody @Valid PublicationEntity entity){

        Long id = publicationService.save(entity);
        if(id != null){
            return new ResponseEntity<>(id, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @PutMapping(value = PUBLICATION_URI, consumes = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity update(@PathVariable("id") long id, @RequestBody PublicationEntity request){

        if(publicationService.getPublication(id) != null){
            publicationService.update(id, request);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Publication with id: " + id + " " +  "was not found");
        }

    }

    @GetMapping(LANGUAGE_URI)
    public ResponseEntity<List<PublicationEntity>> getByLanguage(
            @RequestParam("language") Language language) {
        List<PublicationEntity> list = publicationService.getPublicationByLanguage(language);
        return ResponseEntity.ok(list);
    }

    @GetMapping(TYPE_URI)
    public ResponseEntity<List<PublicationEntity>> getByType(
            @RequestParam("type") PublicationType type) {
        List<PublicationEntity> list = publicationService.getPublicationByType(type);
        return ResponseEntity.ok(list);
    }

    @DeleteMapping(PUBLICATION_URI)
    public ResponseEntity delete(@PathVariable("id") long id){
        if(publicationService.getPublication(id) != null){
            publicationService.delete(id);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Publication with id: " + id + " " + "was not found");
        }

    }

}


