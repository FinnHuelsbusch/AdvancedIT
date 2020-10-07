package semaphoreTest;

import java.util.concurrent.Semaphore;

public class Mutex {

    static Semaphore mutex = new Semaphore(1, true);
    static Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    mutex.acquire();
                    System.out.println(Thread.currentThread().getName() + " im k.a.");
                    Thread.sleep(3000);
                    mutex.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                    break;
                }

            }
        }
    };

    public static void main(String[] args) {
        new Thread(runnable).start();
        new Thread(runnable).start();
        new Thread(runnable).start();
    }

}
