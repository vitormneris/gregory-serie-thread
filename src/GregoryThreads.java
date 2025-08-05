import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

public class GregoryThreads {
    private final BigDecimal[] bigDecimals = new BigDecimal[8];

    private final long accuracy;
    private final int threadNumber;

    public GregoryThreads(long accuracy, int threadNumber) {
        if (accuracy > 0) {
            this.accuracy = accuracy;
        } else this.accuracy = 1;
        if (threadNumber > 0 && threadNumber <= 8 ) {
            this.threadNumber = threadNumber;
        } else this.threadNumber = 1;
        for (int i = 0; i < this.threadNumber; i++) {
            bigDecimals[i] = BigDecimal.ZERO;
        }
    }

    public BigDecimal getBigDecimalTotal() {
        BigDecimal decimal = BigDecimal.ZERO;
        for (BigDecimal b : bigDecimals) {
            if (Objects.nonNull(b)) {
                decimal = decimal.add(b);
            }
        }
        return decimal;
    }

    public List<Thread> getThreads() {
        AtomicInteger index = new AtomicInteger(-1);
        return Stream
                .generate(() -> new Thread(new RunThread(index.addAndGet(1))))
                .limit(threadNumber)
                .toList();
    }

    private class RunThread implements Runnable {
        private final int index;

        private RunThread(int index) {
            if (index >= 0 && index < threadNumber) {
                this.index = index;
            } else throw new IllegalArgumentException("index invalid");
        }

        @Override
        public void run() {
            for (long n = index; n <= (accuracy / threadNumber); n += threadNumber) {
                BigDecimal newBigValue = BigDecimal.valueOf(((n % 2 == 0 ? 1 : -1) * 4d) / (2 * n + 1));
                bigDecimals[index] = bigDecimals[index].add(newBigValue);
            }
        }
    }
}
