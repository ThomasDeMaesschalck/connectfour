package be.hogent.programmeren5.connectfour.player.business.repository;

import be.hogent.programmeren5.connectfour.player.business.PlayerEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends PagingAndSortingRepository<PlayerEntity, Long> {
}
