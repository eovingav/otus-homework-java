package ru.otus.hw02DIYArrayList;

import java.util.*;

public class DIYArrayList<E> implements List<E> {

    private Object[] innerArray;
    private int size;

    public DIYArrayList() {
        size = 0;
        innerArray = new Object[0];
    }

    public static void main(String[] args) {
        Integer[] array = {12, 15, 19, 20, 27, 39};
        List<Integer> myArrayList = new DIYArrayList<>();
        boolean resultAdd = Collections.addAll(myArrayList, array);
        System.out.println(resultAdd);

    }

    public int size() {

        throw new UnsupportedOperationException();
    }

    public boolean isEmpty() {
        throw new UnsupportedOperationException();
    }

    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
    }

    public Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }

    public boolean add(E e) {
        int newSize = size++;
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
        throw new UnsupportedOperationException();
    }

    public E set(int index, E element) {

        throw new UnsupportedOperationException();
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
        throw new UnsupportedOperationException();

    }

    public ListIterator<E> listIterator(int index) {

        throw new UnsupportedOperationException();
    }

    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

}