package observablessubscribers;


import com.sun.xml.internal.bind.v2.model.core.MaybeElement;
import rx.Completable;
import rx.Observable;
import rx.Observer;
import rx.Single;
import rx.observables.ConnectableObservable;

import java.util.concurrent.TimeUnit;

public class Launcher {

    public Launcher() {}

    public void create() {
        Observable<String> src = Observable.create(emitter -> {
            try {
                emitter.onNext("A");
                emitter.onNext("B");
                emitter.onNext("C");
                emitter.onNext("D");
                emitter.onNext("E");
                emitter.onCompleted();
            } catch (Throwable throwable) {
                emitter.onError(throwable);
            }
        });

        src.map(String::length)
                .filter(i -> i>=5)
                .subscribe(s -> System.out.println("RECIEVED: " + s));
    }

    private void setUpObserver() {
        Observable<String> source = Observable
                .just("Alpha", "Beta", "Gamma", "Delta", "Epsilon");

        Observer<Integer> myObserver = new Observer<Integer>() {

            @Override
            public void onCompleted() {
                System.out.println("Done!");
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("RECEIVED: " + integer);
            }
        };

        source.map(String::length).filter(i -> i >= 5)
                .subscribe(myObserver);
    }

    private void intervalPush() {
        Observable<Long> secondInterval = Observable.interval(1, TimeUnit.SECONDS);
        secondInterval.subscribe(s -> System.out.println(s));

        sleep(5000);
    }
    // hold main thread
    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void setUpConnectableObservable() {
        ConnectableObservable<String> src = Observable.just("A", "B", "C")
                .publish();
        src.subscribe(s -> System.out.println("Observer 1: " + s));
        src.subscribe(i -> System.out.print("Observer 2: " + i));
        src.connect();
    }

    private void SMC() {
        Single.just("JUST")
                .map(String::length)
                .subscribe(System.out::println, Throwable::printStackTrace);

      //Maybe ?
      //Completable
    }

    private void setUpDisposable() {
        Observable<Long> sec = Observable.interval(1, TimeUnit.SECONDS);
        
    }
}
