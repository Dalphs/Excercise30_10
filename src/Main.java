import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static Set<Integer> sets = Collections.synchronizedSet(new HashSet<>());
    public static ExecutorService pool = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        add();
        iterate();
        pool.shutdown();

    }

    public static void add(){
        Runnable add = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    sets.add(i);
                }
            }
        };
        pool.execute(add);
    }

    public static void iterate(){
        Runnable iterate = new Runnable() {
            @Override
            public void run() {
                synchronized (sets) {
                    Iterator iterator = sets.iterator();
                    while (iterator.hasNext()) {
                        System.out.println(iterator.next());
                    }
                }
            }
        };
        pool.execute(iterate);
    }


}
