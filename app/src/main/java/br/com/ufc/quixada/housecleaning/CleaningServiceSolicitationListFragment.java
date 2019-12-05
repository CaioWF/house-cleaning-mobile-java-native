package br.com.ufc.quixada.housecleaning;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import androidx.fragment.app.Fragment;
import br.com.ufc.quixada.housecleaning.dao.CleaningServiceDAO;
import br.com.ufc.quixada.housecleaning.dao.firebase.CleaningServiceFirebaseDAO;
import br.com.ufc.quixada.housecleaning.presenter.CleaningServiceEventListener;
import br.com.ufc.quixada.housecleaning.transactions.Address;
import br.com.ufc.quixada.housecleaning.transactions.CleaningService;
import br.com.ufc.quixada.housecleaning.transactions.Place;
import br.com.ufc.quixada.housecleaning.util.LocationUtil;
import br.com.ufc.quixada.housecleaning.util.SessionUtil;
import br.com.ufc.quixada.housecleaning.view.CleaningServiceSolicitationListView;
import br.com.ufc.quixada.housecleaning.view.eventlistener.CleaningServiceSolicitationListViewEventListener;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CleaningServiceSolicitationListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CleaningServiceSolicitationListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CleaningServiceSolicitationListFragment extends Fragment implements CleaningServiceSolicitationListViewEventListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private CleaningServiceDAO cleaningServiceDAO;
    private CleaningServiceSolicitationListView cleaningServiceSolicitationListView;

    public CleaningServiceSolicitationListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CleaningServiceSolicitationListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CleaningServiceSolicitationListFragment newInstance(String param1, String param2) {
        CleaningServiceSolicitationListFragment fragment = new CleaningServiceSolicitationListFragment();
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
        View view = inflater.inflate(R.layout.fragment_cleaning_service_solicitation_list, container, false);

        cleaningServiceDAO = CleaningServiceFirebaseDAO.getInstance(new CleaningServiceEventListener() {
            @Override
            public void onAdded(CleaningService cleaningService) {
                cleaningServiceSolicitationListView.addCleaningServiceToList(cleaningService);
            }

            @Override
            public void onChanged(CleaningService cleaningService) {

            }

            @Override
            public void onRemoved(CleaningService cleaningService) {

            }
        });

        cleaningServiceSolicitationListView = new CleaningServiceSolicitationListView(this);
        cleaningServiceSolicitationListView.initialize(view);

        updateCleaningServiceList();

        return view;
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
    public void onClickAcceptSolicitation(CleaningService cleaningService) {
        cleaningService.setStatus(CleaningService.Status.ACCEPTED);

        cleaningServiceDAO.update(cleaningService);

        updateCleaningServiceList();
    }

    @Override
    public void onClickRefuseSolicitation(CleaningService cleaningService) {
        cleaningService.setStatus(CleaningService.Status.REFUSED);

        cleaningServiceDAO.update(cleaningService);

        updateCleaningServiceList();
    }

    @Override
    public void onClickFinalizeSolicitation(CleaningService cleaningService) {
        cleaningService.setStatus(CleaningService.Status.DONE);

        cleaningServiceDAO.update(cleaningService);

        updateCleaningServiceList();
    }

    @Override
    public void onClickSeeOnMapSolicitation(CleaningService cleaningService) {
        LocationUtil locationUtil = new LocationUtil();
        Address address = cleaningService.getAddress();
        LatLng latLng = locationUtil.getLatLngFromCityAndNeighborhood(getActivity(), address);
        if (latLng == null) {
            Toast.makeText(getActivity(), "Não foi possível ver a localização no mapa.", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(getActivity(), MapsActivity.class);
            intent.putExtra("lat", latLng.latitude);
            intent.putExtra("lng", latLng.longitude);
            startActivity(intent);
        }
    }

    private void updateCleaningServiceList() {
        List<CleaningService> cleaningServices = cleaningServiceDAO.findAllByResponsible(SessionUtil.getCurrentUserId(getContext()));

        cleaningServiceSolicitationListView.updateCleaningServiceList(cleaningServices);
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
}
