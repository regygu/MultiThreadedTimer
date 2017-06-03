import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TimerHandler implements Runnable {

    private Map<Timer, Thread> timerContainer = new HashMap<>();

    private void createTimer(String name) {
        boolean timerExists = false;

        for (Map.Entry<Timer, Thread> entry : timerContainer.entrySet()) {
            if (entry.getKey().getName().equals(name)) timerExists = true;
        }

        if (!timerExists) {
            Timer newTimer = new Timer(name);
            timerContainer.put(newTimer, null);
            System.out.println("Timer " + name + " created.");
        } else {
            System.out.println("Timer with that name already exists.");
        }
    }

    private Timer getTimer(String name) {
        for (Map.Entry<Timer, Thread> entry : timerContainer.entrySet()) {
            if (entry.getKey().getName().equals(name)) {
                return entry.getKey();
            }
        }
        System.out.println("Unknown timer");
        return null;
    }

    public void checkTimer(Timer timer) {
        System.out.println(timer.toString() + " ThreadID: " + timerContainer.get(timer).getId());
    }

    public void checkTimer() {
        if (timerContainer.size() == 0) {
            System.out.println("Add timer(s) first.");
        }

        for (Map.Entry<Timer, Thread> entry: timerContainer.entrySet()) {
            System.out.println(entry.getKey().toString() + " ThreadID: " + entry.getValue().getId());
        }
    }

    public void stopTimer(Timer timer) {
        timerContainer.get(timer).interrupt();
        System.out.println(timer.getName() + " stopped.");
    }

    public void startTimer(Timer timer) {
        Thread newTimerThread = new Thread(timer);
        timerContainer.put(timer, newTimerThread);
        timer.setRunning(true);
        newTimerThread.start();
        System.out.println(timer.getName() + " started.");
    }

    public void stopAll() {
        for (Map.Entry<Timer, Thread> entry : timerContainer.entrySet()) {
            entry.getValue().interrupt();
        }
    }

    public void exit() {
        stopAll();
        System.exit(1);
    }

    @Override
    public void run() {
        System.out.println("Multi threaded timer 1.0\n");
        System.out.println("create / start / stop / check / exit\n");

        Scanner scanner = new Scanner(System.in);

        while (true) {

            if (Thread.currentThread().isInterrupted()) return;

            if (scanner.hasNext()) {

                functionController(scanner.nextLine());
            }
        }
    }

    public void functionController(String userInput) {
        String[] splitInput = userInput.split(" ");

        if (splitInput.length == 1) {
            switch (splitInput[0]) {
                case "check":
                    checkTimer();
                    break;
                case "exit":
                    exit();
                default:
                    System.out.println("Invalid input");
            }
        } else if (splitInput.length == 2) {
            String function = splitInput[0];
            String name = splitInput[1];
            Timer timer = null;

            if (!function.equals("create")) {
                timer = getTimer(name);
                if (timer == null) return;
            }

            switch (function) {
                case "create":
                    createTimer(name);
                    break;
                case "start":
                    startTimer(getTimer(name));
                    break;
                case "stop":
                    stopTimer(getTimer(name));
                    break;
                case "check":
                    checkTimer(getTimer(name));
                    break;
                default:
                    System.out.println("Invalid input");
            }
        } else {
            System.out.println("Invalid input");
        }
    }
}
