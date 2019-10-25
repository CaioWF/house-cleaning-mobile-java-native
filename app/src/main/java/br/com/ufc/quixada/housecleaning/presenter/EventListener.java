package br.com.ufc.quixada.housecleaning.presenter;

public interface EventListener<T> {

    void onCreate(T t);

    void onUpdate(T t);

    void onDelete(T t);

}
