package projekt2526.pres;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SpriteRenderer {

    private Sprite boatDiver[] = new Sprite[2];
    private Sprite extraDiver;

    private Sprite[] diverEmpty = new Sprite[5];
    private Sprite[] diverWithBag = new Sprite[4];

    private Sprite[] trasureGrabAnim = new Sprite[3];

    private Sprite caughtStaticHead;
    private Sprite[] caughtBodyAnim = new Sprite[2];

    private Sprite tentacle1Base;
    private Sprite[] tentacle1Up = new Sprite[2];
    private Sprite[] tentacle1Down = new Sprite[3];

    private Sprite[] tentacle2 = new Sprite[5];
    private Sprite tentacle2Catch;

    private Sprite[] tentacle3 = new Sprite[4];

    private Sprite[] tentacle4 = new Sprite[3];

    public SpriteRenderer(BufferedImage spriteSheet){
        cutSprites(spriteSheet);
    }

    private Sprite.Part part(BufferedImage sheet, int srcX1, int srcY1, int srcX2, int srcY2, int destX, int destY) {
        int width = (srcX2 - srcX1) + 1;
        int height = (srcY2 - srcY1) + 1;
        return new Sprite.Part(sheet.getSubimage(srcX1, srcX2, width, height), destX, destY);
    }

    private void cutSprites(BufferedImage spriteSheet) {
        boatDiver[0] = new Sprite(
                part(spriteSheet, 0,133,20,20,50,20),
                part(spriteSheet, 0,0,20,20,50,20);
        );
        boatDiver[1] =
    }

}
