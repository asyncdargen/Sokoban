package ru.dargen.sokoban.game.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.dargen.sokoban.render.Resource;
import ru.dargen.sokoban.util.V2;

@Getter
@AllArgsConstructor
public enum Direction {

    UP(0, -1, 87),
    DOWN(0, 1, 83),
    RIGHT(1, 0, 68),
    LEFT(-1, 0, 65),
    ;

    private final int x, y, key;

    public Resource getResource() {
        return MapObject.PLAYER.getResources()[ordinal()];
    }

    public static Direction directionByKey(int key) {
        for (Direction direction : values()) {
            if (direction.key == key)
                return direction;
        }
        return null;
    }

    public static Direction directionByV2(V2 playerV2, V2 selected) {
        if (selected == null)
            return null;
        for (Direction direction : values())
            if (playerV2.clone().add(direction.getV2()).equals(selected))
                return direction;
        return null;
    }

    public V2 getV2() {
        return new V2(x, y);
    }
}
