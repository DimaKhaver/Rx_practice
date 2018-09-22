package combiningobservables;

import rx.Observable;

import java.util.concurrent.TimeUnit;

public class CombiningObservables {

    private CombiningObservables() {}

  //merge() doesn't guarantee order in the final Observable
    private void mergingObservables() {
        rx.Observable<String> src1 = rx.Observable.just("A", "B");
        rx.Observable<String> src2 = rx.Observable.just("C", "D");
        rx.Observable.merge(src1, src2).subscribe(System.out::println); //A,B,C,D
        src1.mergeWith(src2);
      //mergeArray for more than 2 Observables
    }

  //It is an operator that performs a dynamic Observable.merge()
  //by taking each emission and mapping it to an Observable.
  //The flatMap() is also a great way to take a hot Observable UI event
  //stream (such as JavaFX or Android button clicks) and flatMap() each of
  //those events to an entire process within flatMap() . The failure and error
  //recovery can be handled entirely within that flatMap() , so each instance
  //of the process does not disrupt future button clicks
    private void flatMapExample() {
        rx.Observable<String> src = rx.Observable.just("ABC", "DEF");
        src.flatMap(s -> rx.Observable.from(s.split("")))
                .subscribe(System.out::println);
    }

    private void ex2() {
        rx.Observable<String> src = rx.Observable.just("521934/2342/FOXTROT",
                                                       "21962/12112/78886/TANGO",
                                                       "283242/4542/WHISKEY/2348562");
        src.flatMap(s -> rx.Observable.from(s.split("/")))
                .filter(s -> s.matches("[0-9]+")) // regex to filter integers
                .map(Integer::valueOf)
                .subscribe(System.out::println);
    }

  //Guarantee emission ordering. Otherwise prefer merging instead.
    private void concatenation() {
        Observable<String> src1 = Observable.just("A", "B", "C");
        Observable<String> src2 = Observable.just("D", "E", "F");
        Observable.concat(src1, src2); // A,B,C,D,E,F
    }

  //Ordering + each Observable mapped from each
  //emission to finish before starting the next one
    private void concatMap() {
        Observable<String> src1 = Observable.just("A", "B", "C");
        src1.concatMap(s -> Observable.from(s.split("")))
                .subscribe(System.out::println);
    }

  //Taking an emission from each Observable source & combine it into a single emission
  //zip allows up to 8 Observable instances to the Observable.zip() factory
    private void zipping() {
        Observable<String> src1 = Observable.just("A", "B", "C");
        Observable<Integer> src2 = Observable.range(1,6);
        Observable.zip(src1, src2, (s,i) -> s + "-" + i)
                .subscribe(System.out::println);
    }

  //When one src fires, it couples with the latest emissions from the others
  //especially helpful in combining UI inputs
    private void combineLatest() {
        Observable<Long> src1 =
                Observable.interval(300, TimeUnit.MILLISECONDS);
        Observable<Long> src2 =
                Observable.interval(1, TimeUnit.SECONDS);
        Observable.combineLatest(src1, src2, (l1,l2) -> "SRC 1: " + l1 + " SRC 2: " + l2)
                .subscribe(System.out::println);
    }


}
