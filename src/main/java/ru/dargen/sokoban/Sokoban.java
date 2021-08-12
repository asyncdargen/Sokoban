package ru.dargen.sokoban;


import lombok.Getter;
import ru.dargen.sokoban.grapchic.GameFrame;
import ru.dargen.sokoban.render.Renderer;
import ru.dargen.sokoban.game.map.Map;
import ru.dargen.sokoban.game.map.MapContent;

@Getter
public class Sokoban implements Runnable {

    @Getter
    private static Sokoban sokoban;
    private final Thread tickThread = new Thread(this, "game-tick-thread");

    private final LevelManager levels;
    private final Renderer renderer;
    private final GameFrame frame;
    private Map map;

    private MapContent currentMapContent;

    public Sokoban() {
        sokoban = this;
        renderer = new Renderer();
        levels = new LevelManager();
        setPattern(levels.getNextMap());
        frame = new GameFrame(renderer);
        tickThread.start();
    }

    public void run() {
        while (true) {
            try {
                frame.repaint();
                map.checkWin();
                Thread.sleep(49);
            } catch (Throwable t) {
                t.printStackTrace();
                System.exit(-1);
            }
        }
    }

    public void recreateMap() {
        map = new Map(currentMapContent);
    }

    public void setPattern(MapContent mapContent) {
        this.currentMapContent = mapContent;
        recreateMap();
    }
}
