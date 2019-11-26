package br.com.ufc.quixada.housecleaning.view;

import android.net.Uri;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.squareup.picasso.Picasso;

import br.com.ufc.quixada.housecleaning.R;
import br.com.ufc.quixada.housecleaning.transactions.User;
import br.com.ufc.quixada.housecleaning.view.eventlistener.UserProfileViewEventListener;

public class UserProfileView extends GenericView {

    private Uri userPhotoUri;

    private ImageView photoImageView;

    private Button loadPhotoButton;

    private EditText nameField;

    private EditText emailField;

    private EditText passwordField;

    private Spinner workerSpinner;

    private Button saveButton;

    private String[] isWorkerFieldOptions;
    private ArrayAdapter<String> stringArrayAdapter;

    private UserProfileViewEventListener userProfileViewEventListener;

    public UserProfileView(UserProfileViewEventListener userProfileViewEventListener) {
        this.userProfileViewEventListener = userProfileViewEventListener;
    }

    @Override
    public void initialize(View rootView) {
        super.initialize(rootView);

        userPhotoUri = null;

        photoImageView = rootView.findViewById(R.id.user_profile_photo);

        loadPhotoButton = rootView.findViewById(R.id.load_image_button);
        loadPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userProfileViewEventListener.onClickLoadPhotoButton();
            }
        });

        nameField = rootView.findViewById(R.id.name_field);

        emailField = rootView.findViewById(R.id.email_field);

        passwordField = rootView.findViewById(R.id.password_field);

        isWorkerFieldOptions = new String[]{"Não", "Sim"};
        stringArrayAdapter = new ArrayAdapter<>(rootView.getContext(), android.R.layout.simple_dropdown_item_1line, isWorkerFieldOptions);

        workerSpinner = rootView.findViewById(R.id.is_worker_field);
        workerSpinner.setAdapter(stringArrayAdapter);

        saveButton = rootView.findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user.setName(nameField.getText().toString());
                user.setEmail(emailField.getText().toString());
                user.setPassword(passwordField.getText().toString());

                if (workerSpinner.getSelectedItem().equals("Sim")) {
                    user.setWorker(true);
                } else {
                    user.setWorker(false);
                }

                userProfileViewEventListener.onClickSave(user, userPhotoUri);
            }
        });
    }

    @Override
    public int getLayoutFile() {
        return R.layout.activity_user_profile;
    }

    public void loadUserProfile(User user) {
        if (user.getPhoto() != null && !(user.getPhoto().trim().isEmpty())) {
            Picasso.with(getRootView().getContext()).load(user.getPhoto()).fit().centerCrop().into(photoImageView);
        }

        nameField.setText(user.getName());
        emailField.setText(user.getEmail());
        passwordField.setText(user.getPassword());

        if (user.isWorker()) {
            workerSpinner.setSelection(1);
        } else {
            workerSpinner.setSelection(0);
        }
    }

    public void setNewUserPhoto(Uri uri) {
        userPhotoUri = uri;

        Picasso.with(getRootView().getContext()).load(uri).into(photoImageView);
    }

}
