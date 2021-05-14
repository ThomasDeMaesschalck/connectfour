package be.hogent.programmeren5.connectfour.player.service.mapper;

import be.hogent.programmeren5.connectfour.player.business.PlayerEntity;
import be.hogent.programmeren5.connectfour.player.service.dto.Player;
import org.mapstruct.Mapper;

/**
 * Mapping class for mapping DTO to Entity and other way around
 */
@Mapper(componentModel = "spring")
public interface PlayerMapper {

    /**
     * Map to DTO
     * @param playerEntity PlayerEntity that needs to be mapped
     * @return The mapped Player DTO
     */
    Player toDTO(PlayerEntity playerEntity);

    /**
     * Map to Entity
     * @param player Player object that needs to be mapped
     * @return The PlayerEntity
     */
    PlayerEntity toEntity(Player player);
}
