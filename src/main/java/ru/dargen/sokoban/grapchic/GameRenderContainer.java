package ru.dargen.sokoban.grapchic;

import lombok.SneakyThrows;
import lombok.val;
import ru.dargen.sokoban.Sokoban;
import ru.dargen.sokoban.game.enums.Direction;
import ru.dargen.sokoban.game.enums.MapObject;
import ru.dargen.sokoban.render.Renderer;
import ru.dargen.sokoban.render.Resource;
import ru.dargen.sokoban.util.V2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameRenderContainer extends Container {

    private final ru.dargen.sokoban.render.Renderer renderer;
    private final JFrame owner;

    private final Resource selected = Resource.getResource("objects/selected.png");
    private final Resource background = Resource.getResource("objects/background.png");

    public GameRenderContainer(Renderer renderer, JFrame owner) {
        this.renderer = renderer;
        this.owner = owner;
        owner.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {}
            public void keyPressed(KeyEvent e) {
                pressKey(e.getKeyCode());
            }
            public void keyReleased(KeyEvent e) {}
        });
        owner.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {}
            public void mousePressed(MouseEvent e) {
                pressMouse();
            }
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        });
    }

    @SneakyThrows
    public void paint(Graphics g) {
        renderer.update(g);

        val sb = Sokoban.getSokoban();
        val map = sb.getMap();
        val objects = map.getObjects();

        owner.setSize(Math.max(500, map.getWidth() * 32 + 100), 100 + map.getHeight() * 32);

        int startX = owner.getWidth() / 2 - map.getWidth() * 32 / 2;
        int startY = 54;

        for (int y = 0; y < owner.getHeight() / 32 + (owner.getHeight() % 32 != 0 ? 1 : 0); y++)
            for (int x = 0; x < owner.getWidth() / 32 + (owner.getWidth() % 32 != 0 ? 1 : 0); x++)
                draw(x * 32, y * 32, background);

        g.setFont(g.getFont().deriveFont(0, 20));
        g.setColor(Color.WHITE);
        g.drawString(map.getName(), owner.getWidth() / 2 - g.getFontMetrics().stringWidth(map.getName()) / 2 , 20);
        g.setColor(Color.BLACK);
        g.drawString(map.formattedTime(), owner.getWidth() / 2 - g.getFontMetrics().stringWidth(map.formattedTime()) / 2 , 42);

        map.setSelected(null);

        V2 player = map.getPlayer().getLocation();

        for (int y = 0; y < objects.length; y++) {
            for (int x = 0; x < objects[y].length; x++) {
                val obj = map.getAt(x, y);
                if (obj == null) continue;
                draw(startX + 32 * x, startY + 32 * y, MapObject.FLOOR.getResources()[0]);
                val resource =
                        obj == MapObject.PLAYER
                                ? map.getPlayer().getDirection().getResource()
                                : obj.getResources()[0];
                draw(startX + 32 * x, startY + 32 * y, resource);
                if (map.getSelected() == null && obj != MapObject.BLOCK && getMouse() != null) {
                    if (startX + 32 * x <= getMouse().getX()
                            && getMouse().getX() <= startX + 32 * x + 32
                            && startY + 32 * y <= getMouse().getY()
                            && getMouse().getY() <= startY + 32 * y + 32) {
                        val selected = new V2(x, y);
                        if (Direction.directionByV2(map.getPlayer().getLocation(), selected) != null) {
                            map.setSelected(selected);
                            draw(startX + 32 * x, startY + 32 * y, this.selected);
                        }
                    }
                }
            }
        }

        for (V2 point : map.getPoints()) {
            if (point.equals(player)) continue;
            draw(startX + 32 * point.getX(), startY + 32 * point.getY(), MapObject.POINT.getResources()[0]);
        }
    }

    protected void draw(int x, int y, Resource resource) {
        renderer.bind(resource);
        renderer.drawCustomSizedTexture(x, y, 32, 32, 0, 0, 64, 64);
    }

    public V2 getMouse() {
        try {
            return new V2(getMousePosition().x, getMousePosition().y);
        } catch (Throwable e) {
            return V2.ZERO;
        }
    }

    protected void pressKey(int key) {
        val direction = Direction.directionByKey(key);
        if (direction == null) {
            if (key == 116)
                Sokoban.getSokoban().recreateMap();
        } else Sokoban.getSokoban().getMap().tryMove(direction);
    }

    private void pressMouse() {
        val direction = Direction.directionByV2(
                Sokoban.getSokoban().getMap().getPlayer().getLocation(),
                Sokoban.getSokoban().getMap().getSelected()
        );
        if (direction != null)
            Sokoban.getSokoban().getMap().tryMove(direction);
    }

}
