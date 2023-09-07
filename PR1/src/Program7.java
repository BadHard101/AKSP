import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class Program7 {
    public static void main(String[] args) {
        Store7 store7 = new Store7();
        Producer7 producer7 = new Producer7(store7);
        Consumer7 consumer7 = new Consumer7(store7);
        new Thread(producer7).start();
        new Thread(consumer7).start();
    }
}

// Класс Магазин, хранящий произведенные товары
class Store7 {
    private int product = 0;
    ReentrantLock locker;
    Condition condition;

    Store7() {
        locker = new ReentrantLock(); // создаем блокировку
        condition = locker.newCondition(); // получаем условие, связанное с блокировкой
    }

    public void get() {
        locker.lock();
        try {
// пока нет доступных товаров на складе, ожидаем
            while (product < 1)
                condition.await();
            product--;
            System.out.println("Покупатель купил 1 товар");
            System.out.println("Товаров на складе: " + product);
// сигнализируем
            condition.signalAll();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        } finally {
            locker.unlock();
        }
    }

    public void put() {
        locker.lock();
        try {
// пока на складе 3 товара, ждем освобождения места
            while (product >= 3)
                condition.await();
            product++;
            System.out.println("Производитель добавил 1 товар");
            System.out.println("Товаров на складе: " + product);
// сигнализируем
            condition.signalAll();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        } finally {
            locker.unlock();
        }
    }
}

// класс Производитель
class Producer7 implements Runnable {
    Store7 store7;

    Producer7(Store7 store7) {
        this.store7 = store7;
    }

    public void run() {
        for (int i = 1; i < 6; i++) {
            store7.put();
        }
    }
}

// Класс Потребитель
class Consumer7 implements Runnable {
    Store7 store7;

    Consumer7(Store7 store7) {
        this.store7 = store7;
    }

    public void run() {
        for (int i = 1; i < 6; i++) {
            store7.get();
        }
    }
}