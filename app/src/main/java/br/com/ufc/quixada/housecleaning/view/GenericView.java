package br.com.ufc.quixada.housecleaning.view;

import android.view.View;

public abstract class GenericView {

    private View rootView;

    public void initialize(View rootView) {
        this.rootView = rootView;
    }

    public View getRootView() {
        return rootView;
    }

    public abstract int getLayoutFile();

}
