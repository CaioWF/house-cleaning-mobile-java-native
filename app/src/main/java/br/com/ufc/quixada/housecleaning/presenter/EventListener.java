package br.com.ufc.quixada.housecleaning.presenter;

public interface EventListener<T> {

    void onAdded(T t);

    void onChanged(T t);

    void onRemoved(T t);

}
