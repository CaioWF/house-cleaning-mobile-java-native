package br.com.ufc.quixada.housecleaning.util;

import android.net.Uri;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import androidx.annotation.NonNull;

public class StorageUtil {

    private static StorageUtil storageUtil = null;

    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    private StorageUtil() {
        this.firebaseStorage = FirebaseStorage.getInstance();
        this.storageReference = firebaseStorage.getReference("uploads");
    }

    public void uploadFile(Uri fileUri, String fileName, final UploadEventListener uploadEventListener) {
        final StorageReference fileStorageReference = storageReference.child(fileName);

        fileStorageReference.putFile(fileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileStorageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        uploadEventListener.onSuccessfulFileUpload(uri);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                uploadEventListener.onFailureFileUpload(e);
            }
        });
    }

    public static StorageUtil getInstance() {
        if (storageUtil == null) {
            storageUtil = new StorageUtil();
        }

        return storageUtil;
    }

    public interface UploadEventListener {

        void onSuccessfulFileUpload(Uri uploadedFileUri);

        void onFailureFileUpload(Exception e);

    }

}
