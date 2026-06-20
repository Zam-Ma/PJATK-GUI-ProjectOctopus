package projekt2526.pres;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public record Sprite(Part... parts) {

    public record Part(BufferedImage image, int destX, int destY) {}

    public void draw(Graphics2D g2d) {
        for (Part part : parts) {
            if (part.image() != null) {
                g2d.drawImage(
                        part.image(),
                        part.destX(),
                        part.destY(),
                        null
                );
            }
        }
    }
}