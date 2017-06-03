public class Timer implements Runnable {

    private String name;
    private int runTime;
    private boolean running;

    public Timer(String name) {
        this.name = name;
        runTime = 0;
        running = true;
    }

    public String getName() {
        return name;
    }

    public int getRunTime() {
        return runTime;
    }

    public void setRuntime(int runTime) {
        this.runTime = runTime;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(1000);
                runTime += 1;
            } catch (InterruptedException e) {
                running = false;
            }

        }
    }

    @Override
    public String toString() {
        return "Name: " + getName() + "    runtime:         " + getRunTime();
    }
}
