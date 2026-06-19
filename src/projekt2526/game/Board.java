package projekt2526.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Board implements KeyListener {
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_R) {
            // fire event
        }
        if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A) {
            // fire event
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
