package br.com.ufc.quixada.housecleaning;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import br.com.ufc.quixada.housecleaning.dao.PlaceDAO;
import br.com.ufc.quixada.housecleaning.dao.UserDAO;
import br.com.ufc.quixada.housecleaning.dao.firebase.PlaceFirebaseDAO;
import br.com.ufc.quixada.housecleaning.dao.firebase.UserFirebaseDAO;
import br.com.ufc.quixada.housecleaning.presenter.PlaceEventListener;
import br.com.ufc.quixada.housecleaning.presenter.UserEventListener;
import br.com.ufc.quixada.housecleaning.transactions.Place;
import br.com.ufc.quixada.housecleaning.transactions.User;
import br.com.ufc.quixada.housecleaning.util.CameraUtil;
import br.com.ufc.quixada.housecleaning.util.ExternalStorageUtil;
import br.com.ufc.quixada.housecleaning.util.SessionUtil;
import br.com.ufc.quixada.housecleaning.util.StorageUtil;
import br.com.ufc.quixada.housecleaning.view.UserProfileView;
import br.com.ufc.quixada.housecleaning.view.eventlistener.UserProfileViewEventListener;

public class UserProfileActivity extends AppCompatActivity implements UserProfileViewEventListener {

    private static final int IMAGE_PICK_CODE = 1;
    private static final int REQUEST_CAMERA_CODE = 2;
    private static final int TAKE_A_PICTURE_CODE = 3;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE_CODE = 4;

    private PlaceDAO placeDAO;
    private UserDAO userDAO;
    private UserProfileView userProfileView;
    private CameraUtil cameraUtil;
    private ExternalStorageUtil externalStorageUtil;

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

        placeDAO = PlaceFirebaseDAO.getInstance(new PlaceEventListener() {
            @Override
            public void onAdded(Place place) {

            }

            @Override
            public void onChanged(Place place) {

            }

            @Override
            public void onRemoved(Place place) {

            }
        });

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

        cameraUtil = new CameraUtil();
        externalStorageUtil = new ExternalStorageUtil();
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
        User user = userDAO.findById(currentUserId);
        return user;
    }

    @Override
    public void onClickLoadPhotoButton() {
        Dialog dialog = onCreateDialog();
        dialog.show();
    }

    @Override
    public void onClickSelectPlacesButton(List<Place> userServicePlaces) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final List<Place> places = placeDAO.findAll();

        List<String> placeStringList = toStringList(places);
        List<String> selectedPlaceStringList = toStringList(userServicePlaces);

        String[] placeStrings = Arrays.copyOf(placeStringList.toArray(), placeStringList.size(), String[].class);

        final boolean[] selectPlacePositions = getSelectedPositions(placeStringList, selectedPlaceStringList);

        builder.setTitle("").setMultiChoiceItems(placeStrings, selectPlacePositions, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                selectPlacePositions[which] = isChecked;
            }
        }).setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                List<Place> selectedPlaces = new ArrayList<>();

                for (int i = 0; i < selectPlacePositions.length; i++) {
                    if (selectPlacePositions[i]) {
                        selectedPlaces.add(places.get(i));
                    }
                }

                userProfileView.setSelectedPlaces(selectedPlaces);

                dialog.dismiss();
            }
        }).setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    @Override
    public void onClickSave(User user, Uri userPhotoFileUri) {
        final User currentUser = getCurrentUser();

        currentUser.setName(user.getName());
        currentUser.setEmail(user.getEmail());
        currentUser.setPassword(user.getPassword());
        currentUser.setWorker(user.isWorker());
        currentUser.setServicePlaces(user.getServicePlaces());

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
        } else if (requestCode == TAKE_A_PICTURE_CODE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap image = (Bitmap) extras.get("data");
            Uri uri = externalStorageUtil.getImageUri(this, image);
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

    private <T extends Object> List<String> toStringList(List<T> objects) {
        List<String> stringList = new ArrayList<>();

        for (Object o : objects) {
            stringList.add(o.toString());
        }

        return stringList;
    }

    private boolean[] getSelectedPositions(List<String> items, List<String> selectedItems) {
        boolean[] selectedPositions = new boolean[items.size()];

        for (int i = 0; i < items.size(); i++) {
            if (selectedItems.contains(items.get(i))) {
                selectedPositions[i] = true;
            } else {
                selectedPositions[i] = false;
            }
        }

        return selectedPositions;
    }

    public Dialog onCreateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.title_dialog_camera)
                .setItems(R.array.camera_options, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                pickImageFromGallery();
                                break;
                            case 1:
                                openCamera();
                                break;
                            default:
                                break;
                        }
                    }
                });
        return builder.create();
    }

    public void openCamera() {
        if (!cameraUtil.checkCameraPermission(UserProfileActivity.this)
                || !externalStorageUtil.checkExternalStoragePermission(UserProfileActivity.this)) {
            cameraUtil.requestCameraPermission(this);
        } else {
            cameraUtil.takeAPicture(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CAMERA_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if(!externalStorageUtil.checkExternalStoragePermission(this)) {
                    externalStorageUtil.requestExternalStoragePermission(this);
                } else {
                    cameraUtil.takeAPicture(this);
                }
            }
        } else if (requestCode == REQUEST_WRITE_EXTERNAL_STORAGE_CODE) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                cameraUtil.takeAPicture(this);
            }
        }
    }
}
