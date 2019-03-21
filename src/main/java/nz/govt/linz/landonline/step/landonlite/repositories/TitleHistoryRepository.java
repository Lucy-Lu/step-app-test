package nz.govt.linz.landonline.step.landonlite.repositories;

import nz.govt.linz.landonline.step.landonlite.models.TitleHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TitleHistoryRepository extends JpaRepository<TitleHistory, Long> {
}
