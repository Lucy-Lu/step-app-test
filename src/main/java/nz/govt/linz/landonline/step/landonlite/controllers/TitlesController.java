package nz.govt.linz.landonline.step.landonlite.controllers;

import nz.govt.linz.landonline.step.landonlite.models.Title;
import nz.govt.linz.landonline.step.landonlite.models.TitleHistory;
import nz.govt.linz.landonline.step.landonlite.repositories.TitleHistoryRepository;
import nz.govt.linz.landonline.step.landonlite.repositories.TitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Optional;

@RestController
@RequestMapping("/api/titles")
public class TitlesController {
    private static ResponseEntity NOT_FOUND = ResponseEntity.notFound().build();

    @Autowired
    private TitleRepository titleRepository;

    @Autowired
    private TitleHistoryRepository titleHistoryRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Title> showTitle(@PathVariable long id) {
        Optional<Title> title = titleRepository.findById(id);
        return title.map(t -> ResponseEntity.ok().body(t)).orElse(NOT_FOUND);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Title> updateTitle(@PathVariable long id, @RequestBody Title body) {
        Title result = titleRepository.findById(id).get();
        if (titleHistoryRepository.findById(id).equals(Optional.empty())) {
            titleHistoryRepository.save(new TitleHistory(result, Instant.now()));
        }
        result.setOwnerName(body.getOwnerName());
        titleRepository.save(result);
        titleHistoryRepository.save(new TitleHistory(result, Instant.now()));
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/")
    public ResponseEntity<Title> addTitle(@RequestBody Title body) {
        Title result = new Title();
        result.setOwnerName(body.getOwnerName());
        result.setDescription(body.getDescription());

        titleRepository.save(result);
        titleHistoryRepository.save(new TitleHistory(result, Instant.now()));
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTitle(@PathVariable long id) {

        Optional<Title> title = titleRepository.findById(id);

        if (title.get() != null) {
            titleRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


}
