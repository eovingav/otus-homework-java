package ru.otus.hw12CacheEngine.cachehw;

import ru.otus.hw12CacheEngine.api.cachehw.HwCache;
import ru.otus.hw12CacheEngine.api.cachehw.HwListener;

import java.util.ArrayList;
import java.util.Map;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {

  private Map<K, V> cache = new WeakHashMap<>();
  private ArrayList<HwListener<K, V>> listeners = new ArrayList<>();

  @Override
  public void put(K key, V value) {
    cache.put(key, value);
    notifyListeners(key, value, "put");
  }

  @Override
  public void remove(K key) {
    cache.remove(key);
    notifyListeners(key, null, "remove");
  }

  @Override
  public V get(K key) {
    return cache.get(key);
  }

  @Override
  public void addListener(HwListener<K, V> listener) {
    listeners.add(listener);

  }

  @Override
  public void removeListener(HwListener<K, V> listener) {
    listeners.remove(listener);
  }

  private void notifyListeners(K key, V value, String action) {
    for (HwListener<K, V> listener: listeners) {
      listener.notify(key, value, action);
    }
  }
}
