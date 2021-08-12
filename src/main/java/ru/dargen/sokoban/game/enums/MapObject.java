package ru.dargen.sokoban.game.enums;

import lombok.Getter;
import ru.dargen.sokoban.render.Resource;

@Getter
public enum MapObject {

    BLOCK('#', Resource.getResource("objects/map/block.png")),
    FLOOR('F', Resource.getResource("objects/map/floor.png")),
    BOX('B', Resource.getResource("objects/map/box.png")),
    PLAYER('@',
            Resource.getResource("player/up.png"),
            Resource.getResource("player/down.png"),
            Resource.getResource("player/right.png"),
            Resource.getResource("player/left.png")
    ),
    POINT('P', Resource.getResource("objects/map/point.png"));

    private final Resource[] resources;
    private final char id;

    MapObject(char id, Resource... resources) {
        this.resources = resources;
        this.id = id;
    }

    public static MapObject forChar(char c) {
        for (MapObject v : values())
            if (v.id == c) return v;
        return null;
    }
}
