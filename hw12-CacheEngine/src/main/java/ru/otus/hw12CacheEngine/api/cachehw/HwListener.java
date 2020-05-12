package ru.otus.hw12CacheEngine.api.cachehw;

public interface HwListener<K, V> {
  void notify(K key, V value, String action);
}
