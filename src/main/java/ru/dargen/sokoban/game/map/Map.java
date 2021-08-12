package ru.dargen.sokoban.game.map;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import ru.dargen.sokoban.Sokoban;
import ru.dargen.sokoban.game.Player;
import ru.dargen.sokoban.game.enums.Direction;
import ru.dargen.sokoban.game.enums.MapObject;
import ru.dargen.sokoban.util.V2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter @Setter
public class Map {

    private final long start = System.currentTimeMillis();
    private final String name;
    private final MapObject[][] objects;
    private final Player player;
    private final List<V2> points = new ArrayList<>();
    private V2 selected = null;

    private final int width, height;

    public Map(MapContent content) {
        val pattern = content.getPattern();
        name = content.getName();

        int width = 1;
        for (String p : pattern) width = Math.max(width, p.length());
        if (pattern.length == 1 || width == 1)
            throw new IllegalArgumentException("patern is empty");

        height = pattern.length;
        objects = new MapObject[pattern.length][this.width = width];

        V2 player = null;

        for (int y = 0; y < pattern.length; y++) {
            val row = pattern[y].toCharArray();
            for (int x = 0; x < row.length; x++) {
                val obj = MapObject.forChar(row[x]);
                Objects.requireNonNull(obj, "map object == null");
                switch (obj) {
                    case POINT: points.add(new V2(x, y)); break;
                    case PLAYER:
                        if (player != null)
                            throw new IllegalArgumentException("there can only be one player");
                        player = new V2(x, y); break;
                }
                objects[y][x] = obj;
            }
        }

        Objects.requireNonNull(player, "player not marked on map");
        if (points.size() < 1)
            throw new IllegalArgumentException("points size < 1");

        this.player = new Player(player, Direction.DOWN);

    }

    public MapObject getAt(int x, int y) {
        if (x < 0 || x > width || y < 0 || y > height)
            return null;
        return objects[y][x];
    }

    public MapObject getAt(V2 v2) {
        return getAt(v2.getX(), v2.getY());
    }

    public void set(MapObject obj, int x, int y) {
        if (x < 1 || x > width || y < 1 || y > height)
            return;
        objects[y][x] = obj;
        if (obj == MapObject.PLAYER)
            player.setLocation(new V2(x, y));
    }

    public void set(MapObject obj, V2 v2) {
        set(obj, v2.getX(), v2.getY());
    }

    public void checkWin() {
        boolean win = points.stream().allMatch(v2 -> getAt(v2.getX(), v2.getY()) == MapObject.BOX);
        if (win)
            Sokoban.getSokoban().setPattern(Sokoban.getSokoban().getLevels().getNextMap());
    }

    public void tryMove(Direction direction) {
        player.setDirection(direction);
        val previous = player.getLocation();
        val next = player.getLocation().clone().add(direction.getV2());
        val nexObj = getAt(next);
        if (nexObj == null || nexObj == MapObject.BLOCK) return;
        if (nexObj == MapObject.FLOOR || nexObj == MapObject.POINT) {
            set(MapObject.FLOOR, previous);
            set(MapObject.PLAYER, next);
        } else if (nexObj == MapObject.BOX) {
            val nextBox = next.clone().add(direction.getV2());
            val nextBoxObj = getAt(nextBox);
            if (nextBoxObj != null && nextBoxObj != MapObject.BLOCK && nextBoxObj != MapObject.BOX) {
                set(MapObject.BOX, nextBox);
                set(MapObject.FLOOR, previous);
                set(MapObject.PLAYER, next);
            }
        }

    }

    public String formattedTime() {
        return (int) ((System.currentTimeMillis() - start) / 1000) + " сек.";
    }
}
