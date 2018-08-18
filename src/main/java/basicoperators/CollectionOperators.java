package basicoperators;

import rx.Observable;

import java.util.List;

public class CollectionOperators {

    private Observable<List<String>> setUpToList() {
        return Observable.just("AA","BB")
              //.toSortedList()
                .toList();
    }
/*
    private Observable<Map> setUpToMap() {
        return Observable.just("AD", "BF", "CF")
                .toMap(s -> s.charAt(0), String::length);
    }

    // Errors handling
    private Observable<Integer> checkErrors() {
        return Observable.just(4, 3, 0, 2, 6, 8, 10)
                .map(i -> 10 / i)
                .onErrorReturn(this::showError);
    }

    private Integer showError(Throwable throwable) {
        return 0;
    }
*/
    private Observable<Integer> actions() {
        return Observable.just(5, 2, 4, 0, 3, 2, 8)
                .doOnError(e -> System.out.println("Source failed!"))
                .map(i -> 10 / i)
                .doOnError(e -> System.out.println("Division failed!"));
    }


}
