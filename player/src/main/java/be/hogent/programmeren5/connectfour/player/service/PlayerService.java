package be.hogent.programmeren5.connectfour.player.service;

import be.hogent.programmeren5.connectfour.player.business.PlayerEntity;
import be.hogent.programmeren5.connectfour.player.business.repository.PlayerRepository;
import be.hogent.programmeren5.connectfour.player.service.dto.Player;
import be.hogent.programmeren5.connectfour.player.service.mapper.PlayerMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.Optional;

/**
 * Player service layer
 */
@Service
@AllArgsConstructor
public class PlayerService {

    /**
     * Player repository
     */
    @Autowired
    private PlayerRepository playerRepository;

    /**
     * The player mapper.
     */
    @Autowired
    private PlayerMapper playerMapper;

    /**
     * Get a paginated list of all persisted players.
     * @param pageNo Number of page requested
     * @param pageSize The size of the page
     * @param sortBy Sort by selection
     * @return A Page of Players
     */
    public Page<Player> getAll(Integer pageNo, Integer pageSize, String sortBy){
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, sortBy));
        return playerRepository.findAll(paging).map(playerMapper::toDTO);
    }

    /**
     * Get a player from the database via the user id
     * @param id Long value of the Player id
     * @return Player from the DB
     */
    public Player getById(Long id){
        Optional<PlayerEntity> playerEntityOptional = playerRepository.findById(id);
        if(playerEntityOptional.isPresent()){
            return playerMapper.toDTO(playerEntityOptional.get());
        }
        return null;
    }

    /**
     * Save a new player
     * @param player The player that needs to be persisted
     * @return Boolean indicates whether the operation succeeded
     */
    public boolean save(Player player) {
        player.setHighscore(0L);
        PlayerEntity playerToSave = playerMapper.toEntity(player);
       try {
           playerRepository.save(playerToSave);
       }
       catch (Exception e)
       {
           return false;
       }
     return true;
    }

    /**
     * Increase the highscore of a winning player
     * @param id Long value of the player id
     * @return Boolean true is returned if the action was a success
     */
    public boolean increaseScore(Long id){
    Optional<PlayerEntity> playerEntityOptional = playerRepository.findById(id);
        if(playerEntityOptional.isPresent()) {
            playerEntityOptional.get().increaseHighscore();
           playerRepository.save(playerEntityOptional.get());
           return true;
        }
        return false;
    }

}
