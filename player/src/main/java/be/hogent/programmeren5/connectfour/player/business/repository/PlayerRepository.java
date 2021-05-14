package be.hogent.programmeren5.connectfour.player.business.repository;

import be.hogent.programmeren5.connectfour.player.business.PlayerEntity;
import be.hogent.programmeren5.connectfour.player.service.dto.Player;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Boot repository for the Player microservice
 */
@Repository
public interface PlayerRepository extends PagingAndSortingRepository<PlayerEntity, Long> {
 }
