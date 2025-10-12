package com.btoy.multi_threading;

/*
 * @created 12/10/2025 ~~ 09:04
 * author: batu
 */
public class WaitNotifyProcesses<T> {

    // TODO -> One slot lock sharing !

    private final Object lock = new Object();
    private T message;
    private boolean isWritable = true; // Initially true !

    void put(T msg) {
        synchronized (lock) {
            try {
                while (!isWritable) lock.wait();
                this.message = msg;
                isWritable = false;
                lock.notifyAll();
            } catch (InterruptedException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    T take() {
        try {
            synchronized (lock) {
                while (isWritable) lock.wait();
                isWritable = true;
                T msg = this.message;
                lock.notifyAll(); // Use always notifyAll method instead of notify unless be sure
                                    // that the there is only one thread waiting for the acquiring !
                return msg;
            }
        } catch (InterruptedException interruptedException) {
            throw new RuntimeException(interruptedException.getMessage());
        }
    }
}
