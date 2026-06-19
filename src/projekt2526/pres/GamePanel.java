package projekt2526.pres;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GamePanel extends JPanel{

    // SCREEN SETTINGS
    private final int BASE_WIDTH = 240;
    private final int BASE_HEIGHT = 160;
    private final int SCALE = 3;

    private BufferedImage spriteSheet;

    private BufferedImage backgroundStaticImage;

    public GamePanel(){
        this.setPreferredSize(new Dimension(BASE_WIDTH * SCALE, BASE_HEIGHT * SCALE));
        this.setBackground(new Color(248,232,192));
        this.setDoubleBuffered(true);
        loadGraphics();
    }

    private void loadGraphics() {
        try {
            InputStream is = getClass().getResourceAsStream("/spritesheet.png");
            if (is == null) {
                System.err.println("Błąd: Nie znaleziono pliku spritesheet.png!");
                return;
            }

            spriteSheet = ImageIO.read(is);
            is.close();

            backgroundStaticImage = spriteSheet.getSubimage(0,0,240,160);

            System.out.println("LOG: Grafika wczytana pomyślnie!");
        } catch (IOException e) {
            System.err.println("BŁĄD Wczytywania pliku: " + e);
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        if (backgroundStaticImage == null)
            return;

        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR
        );

        g2d.scale(SCALE, SCALE);

        g2d.drawImage(
                backgroundStaticImage,
                0, 27,
                BASE_WIDTH,
                BASE_HEIGHT,
                null
        );

        g2d.dispose();
    }
}
