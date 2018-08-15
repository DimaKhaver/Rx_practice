package basicoperators;

import rx.Observable;

public class TransformingOperators {

    // .map() one-to-one conversion for each emission
    // .flatMap() & .concatMap() one-to-many conversion
    private Observable<Object> mapOperator() {
        return Observable.just("13", "34", "", "", "")
              //.map(String::isEmpty)
                .map(s -> (Object) s) // cast to the type
                .cast(Object.class)
              //.contains("13")
                .startWith("YES"); // insert a T emission that precedes all the other emissions
    }

    private Observable<String> setUpComparator() {
        return Observable.just("D", "DD", "DDD")
                .sorted((x,y) -> Integer.compare(x.length(), y.length()));
    }


}
