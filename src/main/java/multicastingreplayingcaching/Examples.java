package multicastingreplayingcaching;


import rx.Observable;
import rx.observables.ConnectableObservable;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/*
* Multicasting (You may do this to increase performance, reducing
memory and CPU usage, or simply because your business logic requires pushing the same
emissions to all Observers)
* Automatic connection
* Replaying and caching
* Subjects
*/
public class Examples {

    private void exampleConnectableObservable() {
        ConnectableObservable<Integer> threeRandoms = Observable.range(1,3)
                .map(i -> randomInt()).publish();

        threeRandoms.subscribe(i -> System.out.println("Observer 1:" + i));
        threeRandoms.reduce(0, (total, next) -> total + next)
                .subscribe(i -> System.out.println("Observer 2:" + i));

        threeRandoms.connect();
    }

    public static int randomInt() {
        return ThreadLocalRandom.current().nextInt(100000);
    }
/*
The autoConnect() operator on ConnectableObservable can be quite handy. For a
given ConnectableObservable<T> , calling autoConnect() will return an
Observable<T> that will automatically call connect() after a specified number of
Observers are subscribed.
*/
    private void exampleAutoConnect() {
        Observable<Integer> threeRandoms = Observable.range(1,3)
                .map(i -> randomInt())
                .publish()
                .autoConnect(2);

        threeRandoms.subscribe(i -> System.out.println("Obs 1:" + i));
        //Observer 2 - sum the random integers, then print
        threeRandoms.reduce(0, (total,next) -> total + next)
                .subscribe(i -> System.out.println("Observer 2: " + i));
    }
/*
The refCount() operator on ConnectableObservable is similar to
autoConnect(1) , which fires after getting one subscription. But there is one important
difference; when it has no Observers anymore, it will dispose of itself and start over when a
new one comes in. It does not persist the subscription to the source when it has no more
Observers, and when another Observer follows, it will essentially "start over".
*/
    private void exampleRefCountShare() {
        Observable<Long> seconds =
                Observable.interval(1, TimeUnit.SECONDS)
                        .publish()
                        .refCount();

        seconds.take(5).subscribe(l -> System.out.println("Observer 1: " + l));

        sleepTh(3000);

        seconds.take(2).subscribe(l -> System.out.println("Observer 2: " + l));

        sleepTh(3000);

        seconds.subscribe(l -> System.out.println("Observer 3: " + l));

        sleepTh(3000);
    }

    private static void sleepTh(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void replaying() {

    }








}
