package projekt2526.pres;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SpriteRenderer {

    private Sprite boatDiver[] = new Sprite[2];
    private Sprite extraDiver[] = new Sprite[2];

    private Sprite[] diverEmpty = new Sprite[5];
    private Sprite[] diverWithBag = new Sprite[5];

    private Sprite[] treasureGrabAnim = new Sprite[5];

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
        return new Sprite.Part(sheet.getSubimage(srcX1, srcY1, width, height), destX, destY);
    }

    private void cutSprites(BufferedImage spriteSheet) {
	
        boatDiver[0] = new Sprite(
                part(spriteSheet, 0,133,15,147,22,5), // head
                part(spriteSheet, 16,133,32,139,16,20), // body
                part(spriteSheet, 16,140,18,144,35,21) // arm-down
        );
        boatDiver[1] = new Sprite(
                part(spriteSheet, 0,133,15,147,22,5), // head
                part(spriteSheet, 16,133,32,139,16,20), // body
                part(spriteSheet, 19,140,31,156,35,8) // arm-raised
        );
		
        extraDiver[0] = new Sprite(
                part(spriteSheet, 0,153,15,147,50,5) // head & body left-diver
        );
        extraDiver[1] = new Sprite(
                part(spriteSheet, 0,153,15,147,68,5) // head & body right-diver
        );
		
        diverEmpty[0] = new Sprite(
                part(spriteSheet, 33,133,68,162,17,39) // diver on the first position under water
        );
        diverEmpty[1] = new Sprite(
                part(spriteSheet, 69,133,98,167,21,91) // diver on the second position under water
        );
        diverEmpty[2] = new Sprite(
                part(spriteSheet, 99,133,127,167,72,113) // diver on the third position under water
        );
		diverEmpty[3] = new Sprite(
                part(spriteSheet, 128,133,160,168,113,113) // diver on the fourth position under water
        );
		diverEmpty[4] = new Sprite(
                part(spriteSheet, 161,133,189,166,160,119), // diver on the fifth position under water
				part(spriteSheet, 32,163,38,171,181,138) // arm close right
        );
		
		diverWithBag[0] = new Sprite(
                part(spriteSheet, 33,133,68,162,17,39), // diver on the first position under water
				part(spriteSheet, 0,154,6,163,50,58) // bag
        );
        diverWithBag[1] = new Sprite(
                part(spriteSheet, 69,133,98,167,21,91), // diver on the second position under water
				part(spriteSheet, 7,154,14,163,43,117) // bag
        );
        diverWithBag[2] = new Sprite(
                part(spriteSheet, 99,133,127,167,72,113), // diver on the third position under water
				part(spriteSheet, 15,154,23,165,95,137) // bag
        );
		diverWithBag[3] = new Sprite(
                part(spriteSheet, 128,133,160,168,113,113), // diver on the fourth position under water
				part(spriteSheet, 24,157,31,169,141,136) // bag
        );
		diverWithBag[4] = new Sprite(
                part(spriteSheet, 161,133,189,166,160,119), // diver on the fifth position under water
				part(spriteSheet, 32,163,38,171,181,138), // arm extended close right
				part(spriteSheet, 0,164,12,174,153,135) // bag
        );
		
		// diverWithBag[4] == treasureGrabAnim[3] --> possible performance incrase!
		
		treasureGrabAnim[0] = new Sprite(
                part(spriteSheet, 161,133,189,166,160,119), // body
				part(spriteSheet, 39,163,51,170,184,136) // arm extended far right
        );
		treasureGrabAnim[1] = new Sprite(
                part(spriteSheet, 161,133,189,166,160,119), // body
				part(spriteSheet, 32,163,38,171,181,138) // arm extended close right
        );
		treasureGrabAnim[2] = new Sprite(
                part(spriteSheet, 161,133,189,166,160,119), // body
				part(spriteSheet, 13,166,18,170,167,135), // arm extended left
				part(spriteSheet, 0,164,12,174,153,135) // bag
        );
		treasureGrabAnim[3] = new Sprite(
                part(spriteSheet, 161,133,189,166,160,119), // body
				part(spriteSheet, 32,163,38,171,181,138), // arm extended close right
				part(spriteSheet, 0,164,12,174,153,135) // bag
        );
		treasureGrabAnim[4] = new Sprite(
                part(spriteSheet, 161,133,189,166,160,119), // body
				part(spriteSheet, 39,163,51,170,184,136), // arm extended far right
				part(spriteSheet, 0,164,12,174,153,135) // bag
        );
		
		caughtBodyAnim[0] = new Sprite(
                part(spriteSheet, 190,133,209,153,124,75), // body
				part(spriteSheet, 190,154,198,166,131,95), // arm raised
				part(spriteSheet, 210,141,220,155,156,86) // legs closed
        );
		caughtBodyAnim[1] = new Sprite(
                part(spriteSheet, 190,133,209,153,124,75), // body
				part(spriteSheet, 198,155,202,164,137,99), // arm lowered  -> needs revision
				part(spriteSheet, 210,133,219,140,157,80), // leg spread left
				part(spriteSheet, 203,155,209,164,156,97) // leg spread right
        );
		
		tentacle1Base = new Sprite(
                part(spriteSheet, 95,168,123,180,84,51) // first segment of the first tentacle
        );
		tentacle1Up[0] = new Sprite(
                part(spriteSheet, 75,168,94,175,70,54) // second segment going up of the first tentacle
        );
		
		tentacle1Up[1] = new Sprite(
                part(spriteSheet, 52,163,74,174,49,46) // third segment going up of the first tentacle
        );
		tentacle1Down[0] = new Sprite(
                part(spriteSheet, 124,169,142,174,78,64) // second segment going down of the first tentacle
        );
		tentacle1Down[1] = new Sprite(
                part(spriteSheet, 143,169,157,180,73,71) // third segment going down of the first tentacle
        );
		tentacle1Down[2] = new Sprite(
                part(spriteSheet, 158,167,185,190,54,81) // fourth segment going down of the first tentacle
        );
		
		tentacle2[0] = new Sprite(
                part(spriteSheet, 186,167,210,181,110,66) // first segment of the second tentacle
        );
		tentacle2[1] = new Sprite(
                part(spriteSheet, 210,156,222,173,108,79) // second segment of the second tentacle
        );
		tentacle2[2] = new Sprite(
                part(spriteSheet, 211,174,220,190,107,87) // third segment of the second tentacle
        );
		tentacle2[3] = new Sprite(
                part(spriteSheet, 221,133,228,147,107,98) // fourth segment of the second tentacle
        );
		tentacle2[4] = new Sprite(
                part(spriteSheet, 229,133,239,147,99,108) // fifth segment of the second tentacle
        );
		
		tentacle2Catch = new Sprite(
                part(spriteSheet, 124,175,135,187,118,89) // fifth segment of the second tentacle
        );
		
		tentacle3[0] = new Sprite(
                part(spriteSheet, 223,148,235,158,143,84) // first segment of the third tentacle
        );
		tentacle3[1] = new Sprite(
                part(spriteSheet, 223,159,233,171,143,92) // second segment of the third tentacle
        );
		tentacle3[2] = new Sprite(
                part(spriteSheet, 221,174,228,186,148,100) // third segment of the third tentacle
        );
		tentacle3[3] = new Sprite(
                part(spriteSheet, 229,172,237,189,149,109) // fourth segment of the third tentacle
        );
		
		tentacle4[0] = new Sprite(
                part(spriteSheet, 0,175,8,183,177,94) // first segment of the fourth tentacle
        );
		tentacle4[1] = new Sprite(
                part(spriteSheet, 9,175,19,184,176,101) // second segment of the fourth tentacle
        );
		tentacle4[2] = new Sprite(
                part(spriteSheet, 20,172,36,188,179,111) // third segment of the fourth tentacle
        );
    }

	public void renderSpritesDebug(Graphics2D g2d) {

        for (Sprite s : boatDiver) { if (s != null) s.draw(g2d); }
        for (Sprite s : extraDiver) { if (s != null) s.draw(g2d); }
        for (Sprite s : diverEmpty) { if (s != null) s.draw(g2d); }
        for (Sprite s : diverWithBag) { if (s != null) s.draw(g2d); }
        for (Sprite s : treasureGrabAnim) { if (s != null) s.draw(g2d); }
        for (Sprite s : caughtBodyAnim) { if (s != null) s.draw(g2d); }

        if (tentacle1Base != null) tentacle1Base.draw(g2d);
        for (Sprite s : tentacle1Up) { if (s != null) s.draw(g2d); }
        for (Sprite s : tentacle1Down) { if (s != null) s.draw(g2d); }
        
        for (Sprite s : tentacle2) { if (s != null) s.draw(g2d); }
        if (tentacle2Catch != null) tentacle2Catch.draw(g2d);
        
        for (Sprite s : tentacle3) { if (s != null) s.draw(g2d); }
        for (Sprite s : tentacle4) { if (s != null) s.draw(g2d); }
    }
	
}
