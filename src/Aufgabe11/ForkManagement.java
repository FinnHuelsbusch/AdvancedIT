package Aufgabe11;

import java.util.concurrent.Semaphore;

public class ForkManagement {
    private int anz;
    private Semaphore fork;
    private Semaphore mutexBesteck = new Semaphore(1, true);
    private boolean[] forkFree;

    public ForkManagement(int anz) {
        this.anz = anz;
        fork = new Semaphore(anz, true);
        forkFree = new boolean[anz];
        for ( int i = 0; i < forkFree.length; i++ ) {
            forkFree[i] = true;
        }

    }

    public void getCutlery(int nr) {
        try {
            mutexBesteck.acquire();
            if (forkFree[nr] && forkFree[(nr - 1 + 4) % 4]) {
                forkFree[nr] = false;
                forkFree[(nr - 1 + 4) % 4] = false;
                mutexBesteck.release();
            } else {
                mutexBesteck.release();
                mutexBesteck.acquire();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void returnClutlery(int nr) {
        try {
            mutexBesteck.acquire();
            forkFree[nr] = forkFree[(nr - 1 + 4) % 4] = false;
            mutexBesteck.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
