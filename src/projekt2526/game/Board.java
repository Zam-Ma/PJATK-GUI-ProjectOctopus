package projekt2526.game;

import javax.swing.Timer;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Board implements KeyListener, TickListener {

    private int[][] stateArray;
    private boolean isGameStarted = false;

    public enum PlayerState {
        ON_BOAT,
        DIVING_EMPTY,
        DIVING_WITH_BAG,
        PACKING_TREASURE,
        CAUGHT,
        DEPOSIT,
    }

    private PlayerState currentPlayerState;
    private int playerWaterPosition = 0;
    private Timer animTimer;
    private int animFrameIndex = 0;
    private boolean hadBagBeforePacking = false;
    private boolean isFrozenCollision = false;
    private int lives = 3;
    private int score = 0;
    private int[] tentacleLengths = new int[4];
    private boolean[] tentacleRetracting = new boolean[4];
    private int tentacle1Branch = 1; // w ktora strone leci macka pierwsza 1 - do gory, 2 w dol


    public Board() {
        initArray();
        animTimer = new Timer(100, e -> processPlayerAnimations());
        resetGame();
    }

    private void initArray() {
        stateArray = new int[6][];
        stateArray[0] = new int[2]; // Gracz
        stateArray[1] = new int[3]; // Macka 1 (Góra)
        stateArray[2] = new int[4]; // Macka 1 (Dół)
        stateArray[3] = new int[5]; // Macka 2
        stateArray[4] = new int[4]; // Macka 3
        stateArray[5] = new int[3]; // Macka 4
    }

    public void resetGame() {
        if (animTimer.isRunning()) animTimer.stop();

        if (GameThread.getInstance() != null) {
            GameThread.getInstance().pauseThread();
            GameThread.getInstance().resetInterval();
        }

        currentPlayerState = PlayerState.ON_BOAT;
        playerWaterPosition = 0;
        animFrameIndex = 0;

        lives = 3;
        score = 0;
        hadBagBeforePacking = false;
        isFrozenCollision = false;

        clearArray();

        for (int i = 0; i < 4; i++) {
            tentacleLengths[i] = 0;
            tentacleRetracting[i] = false;
        }
        tentacle1Branch = 1;

        updateLivesArrayState();
        updateTentaclesArrayState();

        if (GameThread.getInstance() != null) {
            GameThread.getInstance().setTickInterval(calculateTickDelay());
        }
    }

    private void clearArray() {
        for (int i = 0; i < stateArray.length; i++){
            for (int j = 0; j < stateArray[i].length; j++){
                stateArray[i][j] = 0;
            }
        }
    }

    private void setTimerSpeed(int ms) {
        animTimer.setInitialDelay(ms); // Czas do PIERWSZEGO wywołania po restarcie
        animTimer.setDelay(ms);        // Czas do KAŻDEGO KOLEJNEGO wywołania
    }

    public void setGameStarted(boolean started) {
        this.isGameStarted = started;
    }

    private void addScore(int points) {
        score += points;
        if (score > 999) {
            score = score % 1000;
            System.out.println("LOG: Rollover wyniku! Wstrzymuję wątek.");
            GameThread.getInstance().pauseThread();
            GameThread.getInstance().resetInterval();
            setGameStarted(false);
        }
        // Przyspieszamy grę po dodaniu punktu
        GameThread.getInstance().setTickInterval(calculateTickDelay());
    }

    // Game thread
    @Override
    public void onTick(TickEvent e) {
        if (isGameStarted){
            updateGameTick();
        }
    }

    public void updateGameTick() {
        if (currentPlayerState == PlayerState.CAUGHT || isFrozenCollision) {
            return;
        }
        moveTentacles();
        updateTentaclesArrayState();
    }

    // LOGIKA MACEK

    private void moveTentacles() {
        int i = (int) (Math.random() * 4);

        if (tentacleRetracting[i]){
            tentacleLengths[i]--;
            if (tentacleLengths[i] <= 0){
                tentacleLengths[i] = 0;
                tentacleRetracting[i] = false;
                if (i == 0) {
                    tentacle1Branch = (Math.random() < 0.5) ? 1 : 2;
                }
            }
        } else {
            tentacleLengths[i]++;
            int maxLength = getTentacleMaxLength(i);
            if (tentacleLengths[i] >= maxLength){
                tentacleLengths[i] = maxLength;
                tentacleRetracting[i] = true;
                checkCollisions();
            }
        }
    }

    private int getTentacleMaxLength(int index) {
        if (index == 0) return (tentacle1Branch == 1) ? 3 : 4; // T1: 3 w górę, 4 w dół
        if (index == 1) return 5; // T2
        if (index == 2) return 4; // T3
        if (index == 3) return 3; // T4
        return 0;
    }

    private void updateTentaclesArrayState(){
        for (int i = 1; i <= 5; i++) {
            for (int j = 0; j < stateArray[i].length; j++) {
                stateArray[i][j] = 0;
            }
        }
        for (int j = 0; j < tentacleLengths[0]; j++) {
            stateArray[tentacle1Branch][j] = 1;
        }
        for (int j = 0; j < tentacleLengths[1]; j++) {
            stateArray[3][j] = 1;
        }
        for (int j = 0; j < tentacleLengths[2]; j++) {
            stateArray[4][j] = 1;
        }
        for (int j = 0; j < tentacleLengths[3]; j++) {
            stateArray[5][j] = 1;
        }
    }

    private void checkCollisions() {
        if (currentPlayerState == PlayerState.ON_BOAT) return;

        boolean caught = false;
        if (tentacle1Branch == 1 && tentacleLengths[0] == 3 && playerWaterPosition == 0) caught = true;
        if (tentacle1Branch == 2 && tentacleLengths[0] == 4 && playerWaterPosition == 1) caught = true;
        if (tentacleLengths[1] == 5 && playerWaterPosition == 2) caught = true;
        if (tentacleLengths[2] == 4 && playerWaterPosition == 3) caught = true;
        if (tentacleLengths[3] == 3 && playerWaterPosition == 4) caught = true;

        if (caught) {
            System.out.println("LOG: NUREK ZŁAPANY!");
            isFrozenCollision = true;

            setTimerSpeed(1000);
            animTimer.restart();
        }
    }

    // Animacja ----------------------------------------------

    private void processPlayerAnimations(){
        if (isFrozenCollision) {
            isFrozenCollision = false;

//            System.out.println("LOG: NUREK ZŁAPANY!");
            currentPlayerState = PlayerState.CAUGHT;

            // Konfiguracja macek do sceny "śmierci"
            tentacleLengths[1] = 2; // Druga macka skraca się do 2 (+ element w łapie)
            if (tentacleLengths[2] < 2) {
                tentacleLengths[2] = 2; // Trzecia jeśli krótka, wydłuża się tylko do 2
            }
            updateTentaclesArrayState();

            // Gracz w momencie kolizji traci jedno z żyć, usuwany jest "z zapasu"
            lives--;
            updateLivesArrayState();
//            System.out.println("LOG: Stracono życie! Zostało: " + lives);

            animFrameIndex = 0;

            setTimerSpeed(500);
            return;

        } else if (currentPlayerState == PlayerState.PACKING_TREASURE) {
            setTimerSpeed(120);
            animFrameIndex++;
            if (animFrameIndex == 2) {
                addScore(1);
//                System.out.println("LOG: Nurek zapakował skarb: +1 punkt. Punkty: " + score);
            }
            else if (animFrameIndex >= 3) {
                currentPlayerState = PlayerState.DIVING_WITH_BAG;
                animTimer.stop();
            }
        }
        else if (currentPlayerState == PlayerState.DEPOSIT) {
            setTimerSpeed(120);
            animFrameIndex++;
            if  (animFrameIndex >=4){
                currentPlayerState = PlayerState.ON_BOAT;
                addScore(3);
//                System.out.println("LOG: Zdeponowano skarb! +3 punkty. Punkty:" + score);
                animTimer.stop();
            }
        }
        else if (currentPlayerState == PlayerState.CAUGHT){
            animFrameIndex++;
            updateLivesArrayState();
            if (animFrameIndex >= 6) {
                animTimer.stop();
                if (lives > 0) {
                    currentPlayerState = PlayerState.ON_BOAT;
                    playerWaterPosition = 0;
                    updateLivesArrayState();
                } else {
                    System.out.println("LOG: Koniec gry! Wynik koncowy: " + score);
                    resetGame();
                }
            }
        }
    }

    public int getTreasureAnimFrame() {
        if (!hadBagBeforePacking) {
            if (animFrameIndex == 0) return 0;
            if (animFrameIndex == 1) return 1;
            if (animFrameIndex == 2) return 2;
            return 3;
        } else {
            if (animFrameIndex == 0) return 4;
            if (animFrameIndex == 1) return 3;
            if (animFrameIndex == 2) return 2;
            return 3;
        }
    }

    public int getCaughtAnimFrame(){
        return animFrameIndex % 2;
    }
    public int getDepositAnimFrame() {
        return animFrameIndex % 2;
    }

    // Życia ----------------------------------------------------

    private void updateLivesArrayState() {
        if (currentPlayerState == PlayerState.CAUGHT) {
            if (lives == 2) {
                if (animFrameIndex == 0 || animFrameIndex == 1) {
                    stateArray[0][0] = 0; // Środkowy znika natychmiast
                    stateArray[0][1] = 1; // Prawy czeka na miejscu
                } else {
                    // Od klatki nr 3 (indeks 2) prawy nurek BŁYSKAWICZNIE przeskakuje na środek
                    stateArray[0][0] = 1; // Pojawia się na środku
                    stateArray[0][1] = 0; // Znika z prawej
                }
            } else if (lives == 1) { // Zostało 1 życie (były 2)
                stateArray[0][0] = 0; // Środkowy znika natychmiast
                stateArray[0][1] = 0;
            } else { // Koniec gry
                stateArray[0][0] = 0;
                stateArray[0][1] = 0;
            }
        } else {
            // Normalny stan (poza animacją śmierci)
            if (lives == 3){
                stateArray[0][0] = 1;
                stateArray[0][1] = 1;
            } else if (lives == 2){
                stateArray[0][0] = 1;
                stateArray[0][1] = 0;
            } else {
                stateArray[0][0] = 0;
                stateArray[0][1] = 0;
            }
        }
    }

    // INPUT -----------------------------------

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_S) {
            GameThread.getInstance().startOrResume();
            setGameStarted(true);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!isGameStarted) {
            return;
        }

        int code = e.getKeyCode();

        if (currentPlayerState == PlayerState.CAUGHT
            || currentPlayerState == PlayerState.PACKING_TREASURE
            || currentPlayerState == PlayerState.DEPOSIT || isFrozenCollision)
            return;

        if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_R) {
//            System.out.println("LOG: moved right");
            moveRight();
        }
        if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A) {
//            System.out.println("LOG: moved left");
            moveLeft();
        }
    }

    private void moveRight(){
        if (currentPlayerState == PlayerState.ON_BOAT){
            currentPlayerState = PlayerState.DIVING_EMPTY;
            playerWaterPosition = 0;
            checkCollisions();
        }
        else if (currentPlayerState == PlayerState.DIVING_EMPTY || currentPlayerState == PlayerState.DIVING_WITH_BAG){
            if (playerWaterPosition < 4) {
                playerWaterPosition++;
                checkCollisions();
            } else if (playerWaterPosition == 4) {
                // Rozpoczynamy procedurę pakowania, zapisując jaki był nasz poprzedni stan!
                hadBagBeforePacking = (currentPlayerState == PlayerState.DIVING_WITH_BAG);
                currentPlayerState = PlayerState.PACKING_TREASURE;
                animFrameIndex = 0;

                setTimerSpeed(120);
                animTimer.restart();
            }
        }
    }

    private void moveLeft(){
        if (currentPlayerState == PlayerState.DIVING_EMPTY || currentPlayerState == PlayerState.DIVING_WITH_BAG){
            if (playerWaterPosition > 0) {
                playerWaterPosition--;
                checkCollisions();
            } else if (playerWaterPosition == 0) {
                if (currentPlayerState == PlayerState.DIVING_WITH_BAG) {
                    currentPlayerState = PlayerState.DEPOSIT;
                    animFrameIndex =0;
                    animTimer.setDelay(150);
                    animTimer.restart();
                    System.out.println("LOG: Deponuję skarb.");
                } else {
                    currentPlayerState = PlayerState.ON_BOAT;
                }
            }
        }
    }

    // Zwykłe gettery -------------------------------------------------------

    public boolean isSegmentActive(int row, int col) {
        if (row < 0 || row >= stateArray.length) return false;
        if (col < 0 || col >= stateArray[row].length) return false;
        return stateArray[row][col] == 1;
    }
    public boolean isPlayerOnBoat() {
        if (currentPlayerState == PlayerState.ON_BOAT) return true;
        // Pojawia się tam w połowie animacji śmierci (kiedy przeskakuje ze środka)
        if (currentPlayerState == PlayerState.CAUGHT && lives > 0 && animFrameIndex >= 2) return true;
        return false;
    }
    public PlayerState getCurrentPlayerState() {
        return currentPlayerState;
    }
    public int getPlayerWaterPosition(){
        return playerWaterPosition;
    }
    public int calculateTickDelay() {
        int delay = 800 - (score * 3); // przyspieszamy o 3ms w zaleznosci od punktacji
        return Math.max(50, delay); // Zabezpieczenie, żeby gra nie przyspieszyła poniżej 50ms
    }
    public int getLives() {
        return lives;
    }
    public int getScore() {
        return score;
    }
}