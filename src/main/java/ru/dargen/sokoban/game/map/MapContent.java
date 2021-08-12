package ru.dargen.sokoban.game.map;

import lombok.Getter;

@Getter
public class MapContent {

    private final String name;
    private final String[] pattern;

    public MapContent(String name, String... pattern) {
        this.name = name;
        this.pattern = pattern;
    }

}
