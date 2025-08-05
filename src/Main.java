import java.math.BigDecimal;
import java.util.List;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        long start = System.nanoTime();

        GregoryThreads gregoryThreads = new GregoryThreads(1_000_000_000_000L, 8);
        List<Thread> liat = gregoryThreads.getThreads();
        for (Thread thread: liat) {
            thread.start();
        }

        for (Thread thread: liat) {
            thread.join();
        }
        BigDecimal result = gregoryThreads.getBigDecimalTotal();

        long end = System.nanoTime();
        long time = (end - start) / 1_000_000;

        System.out.println("VALUE " + result);
        System.out.println("TIME " + time);

    }
}