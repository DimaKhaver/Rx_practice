package basicoperators;

import rx.Observable;

import java.util.concurrent.TimeUnit;

public class Launcher {

    public Launcher() {}

    private Observable<String> filteringString() {
        return Observable.just("A", "B")
                .filter(s -> s.length() != 5);
    }

    private Observable<String> takingObj() {
        return Observable.just("A", "B")
                .take(3);
    }

//  emit every 300 milliseconds, but take() emissions for only 2 seconds
    private Observable<Long> otherOperators() {
        return Observable.interval(200, TimeUnit.MILLISECONDS)
                .take(2,TimeUnit.SECONDS)
                .takeWhile(i -> i > 10)
                .skipWhile(i -> i > 19) // while true for both
                .distinct() // only unique operators (doesn't allow repeated)
                .distinctUntilChanged(); // 1,2,3, (1)
    }

    private Observable<Integer> skipping() {
        return Observable.range(1, 100)
                .skip(90);
    }

    private void sleep(long millis) { // sleep main thread
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
