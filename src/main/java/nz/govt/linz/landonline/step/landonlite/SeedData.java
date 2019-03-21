package nz.govt.linz.landonline.step.landonlite;

import nz.govt.linz.landonline.step.landonlite.models.Title;
import nz.govt.linz.landonline.step.landonlite.models.TitleHistory;
import nz.govt.linz.landonline.step.landonlite.repositories.TitleHistoryRepository;
import nz.govt.linz.landonline.step.landonlite.repositories.TitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
class SeedData implements CommandLineRunner {

    @Autowired
    private TitleRepository titleRepository;

    @Autowired
    private TitleHistoryRepository titleHistoryRepository;

    @Override
    public void run(String... strings) {
        // Create the seed titles if the database is empty
        var t1 = new Title("Lot 1 on Block 1", "Jane Doe");
        var t2 = new Title("Lot 2 on Block 1", "Bob Smith");
        if(titleRepository.count() == 0) {
            titleRepository.save(t1);
            titleRepository.save(t2);
        }

        if(titleHistoryRepository.count() == 0){
            titleHistoryRepository.save(new TitleHistory(t1, Instant.now()));
            titleHistoryRepository.save(new TitleHistory(t2,Instant.now()));
        }


        // Print a list of titles in the database
        titleRepository.findAll().forEach(System.out::println);
        titleHistoryRepository.findAll().forEach(System.out::println);
    }
}