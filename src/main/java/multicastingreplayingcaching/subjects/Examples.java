package multicastingreplayingcaching.subjects;


import rx.subjects.*;

//Subject -> both an Observer and an Observable
public class Examples {

//  Hotly broadcasts to its downstream Observers
    private void publishSubjects() {

        Subject<String, String> subject = PublishSubject.create();
        subject.map(String::length).subscribe(System.out::println);

        subject.onNext("Alpha");
        subject.onNext("Beta");
        subject.onNext("Gamma");
        subject.onCompleted();
    }
/*
Use Subjects to eagerly subscribe to an unknown number of multiple
source Observables and consolidate their emissions as a single Observable.
Since Subjects are an Observer, you can pass them to a subscribe() method easily.
onSubscribe() , onNext() , onError() & onComplete() calls are not threadsafe!
*/

//  BehaviorSubject will replay the last emitted item to each new Observer downstream.
    private void behaviorSubject() {
        BehaviorSubject<Object> subject = BehaviorSubject.create();
        subject.subscribe(s -> System.out.println("Observer 1: " + s));
        subject.onNext("Alpha");
        subject.onNext("Beta");
        subject.onNext("Gamma");
        subject.subscribe(s -> System.out.println("Observer 2: " + s));
    }

//  ReplaySubject immediately captures emissions regardless of the presence of
//  downstream Observers & optimizes the caching to occur inside the Subject itself
    private void replaySubject() {
        ReplaySubject<Object> subject = ReplaySubject.create();
        subject.subscribe(s -> System.out.println("Observer 1: " + s));
        subject.onNext("Alpha");
        subject.onNext("Beta");
        subject.onNext("Gamma");
        subject.onCompleted();
        subject.subscribe(s -> System.out.println("Observer 2: " + s));
    }

//  AsyncSubject will only push the last value it receives
    private void asyncSubject() {
        Subject<String, String> subject =
                AsyncSubject.create();
        subject.subscribe(s ->
                System.out.println("Observer 1: " + s),
                Throwable::printStackTrace,
                () -> System.out.println("Observer 1 done!"));
        subject.onNext("Alpha");
        subject.onNext("Beta");
        subject.onNext("Gamma");
        subject.onCompleted();

        subject.subscribe(s ->
                System.out.println("Observer 2: " + s),
                Throwable::printStackTrace,
                () -> System.out.println("Observer 2 done!"));
    }

// UnicastSubject will be used to observe and subscribe to the sources.
// It will buffer all the emissions it receives until an Observer subscribes to it,
// & then it will release all these emissions to the Observer and clear its cache
    private void unicastSubject() {

    }






}
