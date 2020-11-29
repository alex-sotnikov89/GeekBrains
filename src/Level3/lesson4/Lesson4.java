package Level3.lesson4;

public class Lesson4 {
    private Object o = new Object();
    private char currentLetter = 'A';

    public void printA() {
        synchronized (o) {
            try {
                for (int i = 0; i < 5; i++) {
                    while (currentLetter != 'A') {
                        o.wait();
                    }
                    System.out.print("A");
                    currentLetter = 'B';
                    o.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void printB() {
        synchronized (o) {
            try {
                for (int i = 0; i < 5; i++) {
                    while (currentLetter != 'B') {
                        o.wait();
                    }
                    System.out.print("B");
                    currentLetter = 'C';
                    o.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void printC() {
        synchronized (o) {
            try {
                for (int i = 0; i < 5; i++) {
                    while (currentLetter != 'C') {
                        o.wait();
                    }
                    System.out.print("C");
                    currentLetter = 'A';
                    o.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Lesson4 m = new Lesson4();
        Thread t1 = new Thread(() -> {
            m.printA();
        });
        Thread t2 = new Thread(() -> {
            m.printB();
        });
        Thread t3 = new Thread(() -> {
            m.printC();
        });
        t1.start();
        t2.start();
        t3.start();
    }
}

