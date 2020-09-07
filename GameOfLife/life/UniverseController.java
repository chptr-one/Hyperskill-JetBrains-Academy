package life;


public class UniverseController extends Thread {
    private final GameOfLife view;
    private final UniverseModel model;
    private int speed;
    private boolean isRunning = true;


    public UniverseController(GameOfLife view) {
        this.model = new UniverseModel(20);
        this.view = view;
        speed = 20;
    }

    public void restart() {
        model.populateRandomly();
    }

    public void togglePause() {
        isRunning = !isRunning;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getGeneration() {
        return model.getGeneration();
    }

    public int getAlive() {
        return model.getAlive();
    }

    public boolean[][] getCells() {
        return model.getCells();
    }

    public void run() {
        while (!interrupted()) {
            if (isRunning) {
                model.evolve();
                view.repaint();
                try {
                    sleep(10000 / speed);
                } catch (InterruptedException i) {
                    i.printStackTrace();
                    return;
                }
            }
        }
    }
}
