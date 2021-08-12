package ru.dargen.sokoban.render;

import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Renderer {

    private Graphics currenGraphics;
    private Resource currentBind;
    private Image currentBindImage;

    private final Map<Resource, Image> alreadyBinds = new HashMap<>();


    public void drawTexture(int x, int y) {
        Objects.requireNonNull(currentBind, "bind == null");
        Objects.requireNonNull(currenGraphics, "graphics == null");

        currenGraphics.drawImage(currentBindImage, x, y, null);
    }

    public void drawSizedTexture(int x, int y, int width, int height) {
        Objects.requireNonNull(currentBind, "bind == null");
        Objects.requireNonNull(currenGraphics, "graphics == null");

        currenGraphics.drawImage(currentBindImage, x, y, width, height, null);
    }

    public void drawCustomSizedTexture(int x, int y, int width, int height, int imageX, int imageY, int imageWidth, int imageHeight) {
        Objects.requireNonNull(currentBind, "bind == null");
        Objects.requireNonNull(currenGraphics, "graphics == null");

        currenGraphics.drawImage(currentBindImage, x, y, x + width, y + height, imageX, imageY, imageX + imageWidth, imageY + imageHeight, null);
    }

    public Graphics getGraphic() {
        return currenGraphics;
    }

    @SneakyThrows
    public void bind(Resource resource) {
        Objects.requireNonNull(resource, "resource == null");
        currentBind = resource;
        currentBindImage = alreadyBinds.computeIfAbsent(resource, r -> {
            try {
                return ImageIO.read(resource.getInputStream());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            return null;
        });
    }

    public void update(Graphics graphics) {
        currenGraphics = graphics;
    }

}
