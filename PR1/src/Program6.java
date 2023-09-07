import java.util.concurrent.locks.ReentrantLock;

public class Program6 {
    public static void main(String[] args) {
        CommonResource6 commonResource6 = new CommonResource6();
        ReentrantLock locker = new ReentrantLock(); // создаем заглушку
        for (int i = 1; i < 6; i++) {
            Thread t = new Thread(new CountThread6(commonResource6, locker));
            t.setName("Thread " + i);
            t.start();
        }
    }
}

class CommonResource6 {
    int x = 0;
}

class CountThread6 implements Runnable {
    CommonResource6 res;
    ReentrantLock locker;

    CountThread6(CommonResource6 res, ReentrantLock lock) {
        this.res = res;
        locker = lock;
    }

    public void run() {
        locker.lock(); // устанавливаем блокировку
        try {
            // res.x = 1;
            for (int i = 1; i < 5; i++) {
                System.out.printf("%s %d \n", Thread.currentThread().getName(), res.x);
                res.x++;
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        } finally {
            locker.unlock(); // снимаем блокировку
        }
    }
}