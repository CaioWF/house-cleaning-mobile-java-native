package br.com.ufc.quixada.housecleaning;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import br.com.ufc.quixada.housecleaning.dao.UserDAO;
import br.com.ufc.quixada.housecleaning.dao.memory.UserMemoryDAO;
import br.com.ufc.quixada.housecleaning.presenter.UserEventListener;
import br.com.ufc.quixada.housecleaning.transactions.User;
import br.com.ufc.quixada.housecleaning.util.SessionUtil;
import br.com.ufc.quixada.housecleaning.view.UserProfileView;
import br.com.ufc.quixada.housecleaning.view.eventlistener.UserProfileViewEventListener;

public class UserProfileActivity extends AppCompatActivity implements UserEventListener, UserProfileViewEventListener {

    private UserDAO userDAO = UserMemoryDAO.getInstance(this);
    private UserProfileView userProfileView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Perfil");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#189764")));

        View rootView = getWindow().getDecorView().getRootView();

        userProfileView = new UserProfileView(this);
        userProfileView.initialize(rootView);
        userProfileView.loadUserProfile(getCurrentUser());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private User getCurrentUser() {
        int currentUserId = SessionUtil.getCurrentUserId(this);

        return userDAO.findById(currentUserId);
    }

    @Override
    public void onClickSave(User user) {
        User currentUser = getCurrentUser();

        currentUser.setName(user.getName());
        currentUser.setEmail(user.getEmail());
        currentUser.setPassword(user.getPassword());
        currentUser.setWorker(user.isWorker());

        finish();
    }
}
