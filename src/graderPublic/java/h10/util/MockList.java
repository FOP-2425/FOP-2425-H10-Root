package h10.util;

import h10.BidirectionalIterator;
import h10.MyList;

import java.util.ArrayList;
import java.util.ListIterator;

public class MockList<T> implements MyList<T> {

    private final java.util.List<T> delegate = new ArrayList<>();

    public static <T> MockList<T> of(T... elements) {
        return of(java.util.List.of(elements));
    }

    public static <T> MockList<T> of(Iterable<T> elements) {
        MockList<T> list = new MockList<>();
        for (T element : elements) {
            list.add(element);
        }
        return list;
    }

    @Override
    public int findFirst(T key) {
        return delegate.indexOf(key);
    }

    @Override
    public int size() {
        return delegate.size();
    }

    @Override
    public T get(int index) {
        return delegate.get(index);
    }

    @Override
    public void add(int index, T key) {
        delegate.add(index, key);
    }

    @Override
    public T removeAtIndex(int index) {
        return delegate.remove(index);
    }

    @Override
    public void clear() {
        delegate.clear();
    }

    @Override
    public BidirectionalIterator<T> cyclicIterator() {
        return new BidirectionalIterator<>() {

            ListIterator<T> it = delegate.listIterator();
            Boolean nextCalled = null;
            Boolean removeCalled = false;

            @Override
            public boolean hasNext() {
                return size() > 0;
            }

            private void checkResetNext() {
                if (!it.hasNext()) {
                    it = delegate.listIterator();
                }
            }

            private void checkResetPrevious() {
                if (!it.hasPrevious()) {
                    it = delegate.listIterator(size());
                }
            }

            @Override
            public T next() {
                checkResetNext();
                // Case when previous was called
                // We need to skip the current element or else we will return the same element
                if (nextCalled != null && !nextCalled) {
                    it.next();
                    checkResetNext();
                }

                T next = it.next();
                nextCalled = true;
                return next;
            }

            @Override
            public boolean hasPrevious() {
                return size() > 0;
            }

            @Override
            public T previous() {
                checkResetPrevious();
                // Case when next was called
                // We need to skip the current element or else we will return the same element
                if (nextCalled != null && nextCalled) {
                    it.previous();
                    checkResetPrevious();
                }
                T previous = it.previous();
                nextCalled = false;
                return previous;
            }

            @Override
            public void remove() {
                it.remove();
                removeCalled = true;
            }
        };
    }


    @Override
    public String toString() {
        return delegate.toString();
    }
}
