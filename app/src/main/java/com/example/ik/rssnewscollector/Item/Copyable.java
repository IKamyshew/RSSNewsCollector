package com.example.ik.rssnewscollector.Item;

public interface Copyable<T> {
    T copy ();
    T createForCopy ();
    void copyTo (T dest);
}
