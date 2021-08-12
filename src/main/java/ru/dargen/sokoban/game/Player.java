package ru.dargen.sokoban.game;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.dargen.sokoban.game.enums.Direction;
import ru.dargen.sokoban.util.V2;

@Getter @Setter
@AllArgsConstructor
public class Player {

    private V2 location;
    private Direction direction;

}
