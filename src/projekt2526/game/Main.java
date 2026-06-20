package projekt2526.game;

import projekt2526.pres.GamePanel;
import javax.swing.*;

public class Main {

    public static void main (String[] args){
        // bezpieczne odpalenie swing w EDT
        SwingUtilities.invokeLater(() -> {
            JFrame window = new JFrame();
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setResizable(false);
            window.setTitle("Game & Watch - OCTOPUS");

            // Engine
            Board board = new Board();

            // GUI
            GamePanel gamePanel = new GamePanel(board);
            window.add(gamePanel);

            // input
            window.addKeyListener(board);

            window.pack(); // show window
            window.setLocationRelativeTo(null); // środek ekranu
            window.setVisible(true);

            // Rysowanie grafiki w EDT
            Timer drawTimer = new Timer(16, e -> gamePanel.repaint());
            drawTimer.start();

            // Wątek logiki
            GameThread gameThread = GameThread.getInstance();

            // Konfigurujemy początkową szybkość i podpinamy Board pod odbiór TickEvent
            gameThread.setTickInterval(board.calculateTickDelay());
            gameThread.addTickListener(board);

            System.out.println("LOG: Gra gotowa! Naciśnij i puść klawisz 'S' na klawiaturze, aby rozpocząć grę!");
        });
    }
}