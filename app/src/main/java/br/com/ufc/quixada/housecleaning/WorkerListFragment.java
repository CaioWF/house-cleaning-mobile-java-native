package br.com.ufc.quixada.housecleaning;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import br.com.ufc.quixada.housecleaning.dao.UserDAO;
import br.com.ufc.quixada.housecleaning.dao.firebase.UserFirebaseDAO;
import br.com.ufc.quixada.housecleaning.presenter.UserEventListener;
import br.com.ufc.quixada.housecleaning.transactions.Place;
import br.com.ufc.quixada.housecleaning.transactions.User;
import br.com.ufc.quixada.housecleaning.util.LocationUtil;
import br.com.ufc.quixada.housecleaning.util.SessionUtil;
import br.com.ufc.quixada.housecleaning.view.NearWorkerListView;
import br.com.ufc.quixada.housecleaning.view.WorkerListView;
import br.com.ufc.quixada.housecleaning.view.eventlistener.UpdateCurrentPlaceEventListener;
import br.com.ufc.quixada.housecleaning.view.eventlistener.WorkerListViewEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WorkerListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WorkerListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkerListFragment extends Fragment implements UpdateCurrentPlaceEventListener, WorkerListViewEventListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final int REQUEST_GPS_CODE = 5;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private UserDAO userDAO;

    private NearWorkerListView nearWorkerListView;
    private WorkerListView workerListView;

    private LocationUtil locationUtil;
    private Place place;

    public WorkerListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WorkerListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WorkerListFragment newInstance(String param1, String param2) {
        WorkerListFragment fragment = new WorkerListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_worker_list, container, false);

        userDAO = UserFirebaseDAO.getInstance(new UserEventListener() {
            @Override
            public void onAdded(User user) {
                workerListView.addWorkerToList(user);
            }

            @Override
            public void onChanged(User user) {

            }

            @Override
            public void onRemoved(User user) {

            }
        });

        locationUtil = new LocationUtil(this);

        place = new Place();

        openGPS();

        nearWorkerListView = new NearWorkerListView(this);
        nearWorkerListView.initialize(view);

        workerListView = new WorkerListView(this);
        workerListView.initialize(view);

        List<User> nearWorkers = getAllNearWorkersExceptCurrentUser(view.getContext());
        nearWorkerListView.updateWorkerList(nearWorkers);

        List<User> workers = getAllWorkersExceptCurrentUser(view.getContext());
        workerListView.updateWorkerList(workers);

        return view;
    }

    private List<User> getAllNearWorkersExceptCurrentUser(Context context) {
        List<User> workers = userDAO.findAllWorkers();
        List<User> toRemove = new ArrayList<>();
        String currentUserId = SessionUtil.getCurrentUserId(context);

        for (User worker : workers) {
            if (worker.getId().equals(currentUserId) || !containsPlace(place, worker.getServicePlaces())) {
                toRemove.add(worker);
            }
        }

        workers.removeAll(toRemove);

        return workers;
    }

    private boolean containsPlace(Place place1, List<Place> places) {
        if (place1.getCity() == null && place1.getNeighborhood() == null) {
            return false;
        }

        for (Place place : places) {
            if (place.getCity().equals(place1.getCity())
                    && place.getNeighborhood().equals(place1.getNeighborhood())) {
                return true;
            }
        }

        return false;
    }

    private List<User> getAllWorkersExceptCurrentUser(Context context) {
        List<User> workers = userDAO.findAllWorkers();
        List<User> toRemove = new ArrayList<>();

        String currentUserId = SessionUtil.getCurrentUserId(context);

        for (User worker : workers) {
            if (worker.getId().equals(currentUserId)
                    || containsUser(worker, getAllNearWorkersExceptCurrentUser(getContext()))) {
                toRemove.add(worker);
            }
        }

        workers.removeAll(toRemove);

        return workers;
    }

    private boolean containsUser(User worker, List<User> nearWorkers) {
        for (User user : nearWorkers) {
            if (user.getId().equals(worker.getId())) {
                return true;
            }
        }

        return false;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void updatePlace(Place place) {
        this.place = place;

        List<User> near = getAllNearWorkersExceptCurrentUser(getContext());
        List<User> others = getAllWorkersExceptCurrentUser(getContext());

        nearWorkerListView.updateWorkerList(near);
        workerListView.updateWorkerList(others);
    }

    @Override
    public void onClickHireButton(User worker) {
        Intent intent = new Intent(getContext(), RequestCleaningServiceActivity.class);
        intent.putExtra("user_id", worker.getId());

        startActivity(intent);
    }

    @Override
    public void onClickViewDetailsButton(User worker) {
        Intent intent = new Intent(getContext(), WorkerDetailsActivity.class);
        intent.putExtra("user_id", worker.getId());

        startActivity(intent);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public WorkerListView getWorkerListView() {
        return workerListView;
    }

    private void openGPS() {
        if (!locationUtil.checkGPSPermission(getActivity())) {
            locationUtil.requestGPSPermission(getActivity());
        } else {
            useLocation();
        }
    }

    private void useLocation() {
        locationUtil.useLocation(getActivity());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_GPS_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                useLocation();
            }
        }
    }
}
