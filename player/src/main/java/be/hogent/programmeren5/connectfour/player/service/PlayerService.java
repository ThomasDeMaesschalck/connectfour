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

@Service
@AllArgsConstructor
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PlayerMapper playerMapper;

    public Page<Player> getAll(Integer pageNo, Integer pageSize, String sortBy){
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, sortBy));
        return playerRepository.findAll(paging).map(playerMapper::toDTO);
    }

    public Player getById(Long id){
        Optional<PlayerEntity> playerEntityOptional = playerRepository.findById(id);
        if(playerEntityOptional.isPresent()){
            return playerMapper.toDTO(playerEntityOptional.get());
        }
        return null;
    }

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
