package br.com.ufc.quixada.housecleaning.view;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.ufc.quixada.housecleaning.R;
import br.com.ufc.quixada.housecleaning.transactions.Place;
import br.com.ufc.quixada.housecleaning.transactions.User;

public class WorkerDetailsView extends GenericView {

    private ImageView userPhotoImageView;

    private TextView userNameTextView;

    private TextView userRatingTextView;

    private ListView userPlacesListView;

    @Override
    public void initialize(View rootView) {
        super.initialize(rootView);

        userPhotoImageView = rootView.findViewById(R.id.user_photo);

        userNameTextView = rootView.findViewById(R.id.user_name);

        userRatingTextView = rootView.findViewById(R.id.user_rating);

        userPlacesListView = rootView.findViewById(R.id.user_places);
    }

    @Override
    public int getLayoutFile() {
        return R.layout.activity_worker_details;
    }

    public void loadWorkerProfile(User worker) {
        if (worker.getPhoto() != null && !(worker.getPhoto().trim().isEmpty())) {
            Picasso.with(getRootView().getContext()).load(worker.getPhoto()).fit().centerCrop().into(userPhotoImageView);
        }

        userNameTextView.setText(worker.getName());

        userRatingTextView.setText(worker.getRating() + " ★");

        ArrayAdapter<Place> placeArrayAdapter = new ArrayAdapter<>(getRootView().getContext(), android.R.layout.simple_list_item_1, worker.getServicePlaces());
        userPlacesListView.setAdapter(placeArrayAdapter);
    }

}
