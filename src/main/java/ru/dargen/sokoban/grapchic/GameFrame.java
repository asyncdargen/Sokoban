package ru.dargen.sokoban.grapchic;

import lombok.Getter;
import lombok.SneakyThrows;
import ru.dargen.sokoban.render.Renderer;
import ru.dargen.sokoban.render.Resource;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

@Getter
public class GameFrame extends JFrame {

    private final GameRenderContainer renderPanel;
    private final ru.dargen.sokoban.render.Renderer renderer;

    @SneakyThrows
    public GameFrame(Renderer renderer) {
        super("Sokoban");
        setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 250, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 200);
        setIconImage(ImageIO.read(Resource.getResource("objects/icon.png").getInputStream()));
        setResizable(false);
        setDefaultCloseOperation(3);
        setSize(500, 500);
        add(renderPanel = new GameRenderContainer(this.renderer = renderer, this));
        setVisible(true);
    }

}
