package projekt2526.game;

import java.util.ArrayList;
import java.util.List;

public class GameThread extends Thread {
    private static GameThread instance;

    private boolean running = false;
    private boolean paused = true;
    private int tickInterval = 800; // Bazowy interwał logiki (np. ruch macek)

    private final List<TickListener> listeners = new ArrayList<>();

    // Singleton - nie mozna stworzyc drugiego wątku tej klasy
    private GameThread() {}

    public static synchronized GameThread getInstance() {
        if (instance == null) {
            instance = new GameThread();
        }
        return instance;
    }

    public void addTickListener(TickListener listener) {
        listeners.add(listener);
    }

    // uruchomienie następuje po wciśnięciu i puszczeniu klawisza 's'
    public void startOrResume() {
        if (!running) {
            running = true;
            paused = false;
            this.start();
            System.out.println("LOG: Wątek gry uruchomiony (START)!");
        } else if (paused) {
            paused = false;
            System.out.println("LOG: Wątek gry wznowiony (RESUME)!");
        }
    }

    public void pauseThread() {
        paused = true;
        System.out.println("LOG: Wątek gry wstrzymany.");
    }

    public void resetInterval() {
        tickInterval = 800;
    }

    public void setTickInterval(int interval) {
        this.tickInterval = interval;
    }

    @Override
    public void run() {
        while (running) {
            try {
                // Usypiamy wątek na wyznaczony czas (np. 800ms na początku)
                Thread.sleep(tickInterval);

                // send tick
                if (!paused) {
                    TickEvent event = new TickEvent(this);

                    // Kopia listy na wypadek bezpiecznej iteracji w innych wątkach
                    List<TickListener> copy;
                    synchronized (listeners) {
                        copy = new ArrayList<>(listeners);
                    }

                    for (TickListener listener : copy) {
                        listener.onTick(event);
                    }
                }
            } catch (InterruptedException e) {
                System.out.println("LOG: Wątek przerwany!");
                running = false;
            }
        }
    }
}