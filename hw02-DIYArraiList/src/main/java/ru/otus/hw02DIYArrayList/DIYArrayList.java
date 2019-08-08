package ru.otus.hw02DIYArrayList;

import java.util.*;

public class DIYArrayList<E> implements List<E> {

    private Object[] innerArray;
    private int size;

    public DIYArrayList() {
        size = 0;
        innerArray = new Object[0];
    }

    public DIYArrayList(int size) {
        this.size = size;
        innerArray = new Object[size];
    }

    public int size() {

       return size;
    }

    public boolean isEmpty() {
        throw new UnsupportedOperationException();
    }

    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
    }

    public Iterator<E> iterator() {

        return listIterator();
    }

    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }

    public boolean add(E e) {
        int newSize = size + 1;
        Object[] newInnerArray = Arrays.copyOf(innerArray, newSize);
        newInnerArray[size] = e;
        innerArray = newInnerArray;
        size = newSize;
        return true;
    }

    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    public boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    public void clear() {
        throw new UnsupportedOperationException();
    }

    public E get(int index) {

        if ((index < 0) || (index >= size))
                throw new NoSuchElementException();
        return (E) innerArray[index];
    }

    public E set(int index, E element) {

        Objects.checkIndex(index, size);
        E oldValue = (E) innerArray[index];
        innerArray[index] = element;
        return oldValue;
    }

    public void add(int index, E element) {
        throw new UnsupportedOperationException();
    }

    public E remove(int index) {
        return null;
    }

    public int indexOf(Object o) {
        throw new UnsupportedOperationException();

    }

    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();

    }

    public ListIterator<E> listIterator() {
        return listIterator(0);

    }

    public ListIterator<E> listIterator(int index) {

        return new DIYArrayListIterator(index);
    }

    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void sort(Comparator<? super E> c) {
        Arrays.sort((E[]) innerArray, 0, size, c);
    }
    private class DIYArrayListIterator implements ListIterator<E>{

        private int cursor;
        int lastRet = -1;

        DIYArrayListIterator(int index){
            cursor = index;
        }

        @Override
        public boolean hasNext() {
            return cursor != size;
        }

        @Override
        public E next() {
            int i = cursor;
            if ( i >= size)
                throw new NoSuchElementException();
            Object[] elementData = DIYArrayList.this.innerArray;
            cursor = i + 1;
            return (E) elementData[lastRet = i];
        }

        @Override
        public boolean hasPrevious() {
            return cursor != 0;
        }

        @Override
        public E previous() {
            int i = cursor - 1;
            if (i < 0)
                throw new NoSuchElementException();
            Object[] elementData = DIYArrayList.this.innerArray;
            cursor = i;
            return (E) elementData[lastRet = i];
        }

        @Override
        public int nextIndex() {
            return cursor;
        }

        @Override
        public int previousIndex() {
            return cursor - 1;
        }

        @Override
        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();
            try {
                DIYArrayList.this.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public void set(E e) {
            if (lastRet < 0)
                throw new IllegalStateException();

            DIYArrayList.this.set(lastRet, e);
        }

        @Override
        public void add(E e) {
            int i = cursor;
            DIYArrayList.this.add(e);
            cursor = i + 1;
            lastRet = -1;
        }

    }
}