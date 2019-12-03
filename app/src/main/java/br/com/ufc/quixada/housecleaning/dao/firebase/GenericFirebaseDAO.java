package br.com.ufc.quixada.housecleaning.dao.firebase;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import br.com.ufc.quixada.housecleaning.dao.GenericDAO;
import br.com.ufc.quixada.housecleaning.presenter.EventListener;
import br.com.ufc.quixada.housecleaning.transactions.Bean;

public abstract class GenericFirebaseDAO<T extends Bean> implements GenericDAO<T> {

    private String path;
    private EventListener eventListener;
    private Class<T> persistenceClass;
    private List<T> tList;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    public GenericFirebaseDAO(String path, EventListener eventListener, Class<T> persistenceClass) {
        this.path = path;
        this.eventListener = eventListener;
        this.persistenceClass = persistenceClass;
        this.tList = new ArrayList<>();

        database = FirebaseDatabase.getInstance();

        reference = database.getReference(path);
        final EventListener thisEventListener = this.eventListener;
        final Class<T> thisPersistenceClass = this.persistenceClass;
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tList.clear();

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    T t = child.getValue(thisPersistenceClass);
                    tList.add(t);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                T t = dataSnapshot.getValue(thisPersistenceClass);

                tList.add(t);

                thisEventListener.onAdded(t);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                T t = dataSnapshot.getValue(thisPersistenceClass);

                thisEventListener.onChanged(t);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                T t = dataSnapshot.getValue(thisPersistenceClass);

                thisEventListener.onRemoved(t);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void create(T t) {
        reference.child(t.getId()).setValue(t);
    }

    @Override
    public void update(T t) {
        reference.child(t.getId()).setValue(t);
    }

    @Override
    public void delete(T t) {
        reference.child(t.getId()).removeValue();
    }

    @Override
    public T findById(String id) {
        for (T t : tList) {
            if (t.getId().equals(id))
                return t;
        }

        return null;
    }

    @Override
    public List<T> findAll() {
        return tList;
    }

}