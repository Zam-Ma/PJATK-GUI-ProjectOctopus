package projekt2526.game;

import java.util.EventListener;

public interface TickListener extends EventListener {
    void onTick(TickEvent e);
}