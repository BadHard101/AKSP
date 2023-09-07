public class Pract {
    public static void main(String[] args) {
        PingPongGame game = new PingPongGame();
        Thread pingThread = new Thread(new PingPlayer(game));
        Thread pongThread = new Thread(new PongPlayer(game));
        pingThread.start();
        pongThread.start();
    }
}

class PingPongGame {
    private boolean pingTurn = true;

    public synchronized void playPing() {
        try {
            while (!pingTurn) {
                wait();
            }
            System.out.println("PING");
            pingTurn = false;
            notify();
        } catch (InterruptedException e) {
        }
    }

    public synchronized void playPong() {
        try {
            while (pingTurn) {
                wait();
            }
            System.out.println("PONG");
            pingTurn = true;
            notify();
        } catch (InterruptedException e) {
        }
    }
}

class PingPlayer implements Runnable {
    private PingPongGame game;

    public PingPlayer(PingPongGame game) {
        this.game = game;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            game.playPing();
        }
    }
}

class PongPlayer implements Runnable {
    private PingPongGame game;

    public PongPlayer(PingPongGame game) {
        this.game = game;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            game.playPong();
        }
    }
}
