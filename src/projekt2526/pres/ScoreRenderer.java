package projekt2526.pres;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ScoreRenderer {
    private static final int DIGIT_SPACING = 3;
    private static final int SINGLE_DIGIT_WIDTH = 9;
    private BufferedImage[] segments = new BufferedImage[7];
    // Tablica przechowująca 7 wyciętych fragmentów jednej cyfry "8" ze spritesheeta
    // 0: Góra (A)
    // 1: Prawy-Góra (B)
    // 2: Prawy-Dół (C)
    // 3: Dół (D)
    // 4: Lewy-Dół (E)
    // 5: Lewy-Góra (F)
    // 6: Środek (G)
    private static final boolean[][] DIGIT_MAP = {
            {true,  true,  true,  true,  true,  true,  false}, // 0
            {false, true,  true,  false, false, false, false}, // 1
            {true,  true,  false, true,  true,  false, true }, // 2
            {true,  true,  true,  true,  false, false, true }, // 3
            {false, true,  true,  false, false, true,  true }, // 4
            {true,  false, true,  true,  false, true,  true }, // 5
            {true,  false, true,  true,  true,  true,  true }, // 6
            {true,  true,  true,  false, false, false, false}, // 7
            {true,  true,  true,  true,  true,  true,  true }, // 8
            {true,  true,  true,  true,  false, true,  true }  // 9
    };

    public ScoreRenderer(BufferedImage spriteSheet) {
        cutSegments(spriteSheet);
    }

    private void cutSegments(BufferedImage sheet) {
        BufferedImage topSegment = sheet.getSubimage(40, 171, 7, 2);     // Kreska górna
        BufferedImage rightSegment = sheet.getSubimage(46, 172, 2, 8);  // Kreska dolna
        BufferedImage leftSegment = sheet.getSubimage(39, 172, 2, 8);    // Kreska lewa
        BufferedImage bottomSegment = sheet.getSubimage(40, 179, 7, 2);  // Kreska prawa

        segments[0] = topSegment; // 0: A (Góra)
        segments[1] = rightSegment; // 1: B (Prawy-Góra)
        segments[2] = rightSegment; // 2: C (Prawy-Dół)
        segments[3] = bottomSegment; // 3: D (Dół)
        segments[4] = leftSegment;  // 4: E (Lewy-Dół)
        segments[5] = leftSegment;  // 5: F (Lewy-Góra)
        segments[6] = bottomSegment; // 6: G (Środek)
    }

    public void drawScore(Graphics2D g2d, int score, int startX, int startY) {
        String scoreStr = String.format("%03d", score);

        for (int i = 0; i < scoreStr.length(); i++) {
            int digitValue = scoreStr.charAt(i) - '0';

            // Każda kolejna cyfra jest przesuwana w prawo o szerokość cyfry i zdefiniowany odstęp
            int currentX = startX + (i * (SINGLE_DIGIT_WIDTH + DIGIT_SPACING));

            drawSingleDigit(g2d, digitValue, currentX, startY);
        }
    }

    private void drawSingleDigit(Graphics2D g2d, int digitValue, int x, int y) {
        boolean[] activeSegments = DIGIT_MAP[digitValue];

        for (int i = 0; i < 7; i++) {
            if (activeSegments[i] && segments[i] != null) {
                int offsetX = 0;
                int offsetY = 0;

                // MANUALNE UŁOŻENIE SEGMENTÓW
                switch (i) {
                    case 0: // A (Góra)
                        offsetX = 1;
                        offsetY = 0;
                        break;
                    case 1: // B (Prawy-Góra)
                        offsetX = 7;
                        offsetY = 1;
                        break;
                    case 2: // C (Prawy-Dół)
                        offsetX = 7;
                        offsetY = 10;
                        break;
                    case 3: // D (Dół)
                        offsetX = 1;
                        offsetY = 17;
                        break;
                    case 4: // E (Lewy-Dół)
                        offsetX = 0;
                        offsetY = 10;
                        break;
                    case 5: // F (Lewy-Góra)
                        offsetX = 0;
                        offsetY = 1;
                        break;
                    case 6: // G (Środek)
                        offsetX = 1;
                        offsetY = 8;
                        break;
                }

                g2d.drawImage(segments[i], x + offsetX, y + offsetY, null);
            }
        }
    }
}
