package be.hogent.programmeren5.connectfour.player.service.mapper;

import be.hogent.programmeren5.connectfour.player.business.PlayerEntity;
import be.hogent.programmeren5.connectfour.player.service.dto.Player;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlayerMapper {
    Player toDTO(PlayerEntity playerEntity);
    PlayerEntity toEntity(Player player);
}
