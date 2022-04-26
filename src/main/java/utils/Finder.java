package utils;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Finder<T> implements IFindDate<T> {
    private List<T> items;
    private Predicate<T> predicate;

    public Finder(List<T> items, Predicate<T> predicate) {
        this.items = items;
        this.predicate = predicate;
    }

    @Override
    public T searchFirstElement() {
        List<T> results = items.stream().filter(predicate).collect(Collectors.toList());
        if (!results.isEmpty()) {
            return results.get(0);
        }
        return null;
    }

    @Override
    public  List<T> searchAllElements() {
        List<T> results = items.stream().filter(predicate).collect(Collectors.toList());
        if (!results.isEmpty()) {
            return results;
        }
        return null;
    }
}
