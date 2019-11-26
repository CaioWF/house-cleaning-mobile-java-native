package br.com.ufc.quixada.housecleaning;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import br.com.ufc.quixada.housecleaning.dao.UserDAO;
import br.com.ufc.quixada.housecleaning.dao.firebase.UserFirebaseDAO;
import br.com.ufc.quixada.housecleaning.presenter.UserEventListener;
import br.com.ufc.quixada.housecleaning.transactions.User;
import br.com.ufc.quixada.housecleaning.util.SessionUtil;
import br.com.ufc.quixada.housecleaning.util.StorageUtil;
import br.com.ufc.quixada.housecleaning.view.UserProfileView;
import br.com.ufc.quixada.housecleaning.view.eventlistener.UserProfileViewEventListener;

public class UserProfileActivity extends AppCompatActivity implements UserProfileViewEventListener {

    private static final int IMAGE_PICK_CODE = 1;

    private UserDAO userDAO;
    private UserProfileView userProfileView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Perfil");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#62DBA8")));

        View rootView = getWindow().getDecorView().getRootView();

        userDAO = UserFirebaseDAO.getInstance(new UserEventListener() {
            @Override
            public void onAdded(User user) {

            }

            @Override
            public void onChanged(User user) {

            }

            @Override
            public void onRemoved(User user) {

            }
        });

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
        String currentUserId = SessionUtil.getCurrentUserId(this);

        return userDAO.findById(currentUserId);
    }

    @Override
    public void onClickLoadPhotoButton() {
        pickImageFromGallery();
    }

    @Override
    public void onClickSave(User user, Uri userPhotoFileUri) {
        final User currentUser = getCurrentUser();

        currentUser.setName(user.getName());
        currentUser.setEmail(user.getEmail());
        currentUser.setPassword(user.getPassword());
        currentUser.setWorker(user.isWorker());

        if (userPhotoFileUri != null) {
            String userPhotoFileName = System.currentTimeMillis() + "." + getFileExtension(userPhotoFileUri);

            StorageUtil storageUtil = StorageUtil.getInstance();
            storageUtil.uploadFile(userPhotoFileUri, userPhotoFileName, new StorageUtil.UploadEventListener() {
                @Override
                public void onSuccessfulFileUpload(Uri uploadedFileUri) {
                    currentUser.setPhoto(uploadedFileUri.toString());

                    userDAO.update(currentUser);
                }

                @Override
                public void onFailureFileUpload(Exception e) {
                    Toast.makeText(UserProfileActivity.this, "Ocorreu um erro ao fazer upload da imagem", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            userDAO.update(currentUser);
        }

        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            Uri uri = data.getData();

            userProfileView.setNewUserPhoto(uri);
        }
    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");

        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);

        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
}
