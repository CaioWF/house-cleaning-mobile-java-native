package br.com.ufc.quixada.housecleaning;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.fragment.app.Fragment;
import br.com.ufc.quixada.housecleaning.dao.CleaningServiceDAO;
import br.com.ufc.quixada.housecleaning.dao.UserDAO;
import br.com.ufc.quixada.housecleaning.dao.firebase.CleaningServiceFirebaseDAO;
import br.com.ufc.quixada.housecleaning.dao.firebase.UserFirebaseDAO;
import br.com.ufc.quixada.housecleaning.presenter.CleaningServiceEventListener;
import br.com.ufc.quixada.housecleaning.presenter.UserEventListener;
import br.com.ufc.quixada.housecleaning.transactions.CleaningService;
import br.com.ufc.quixada.housecleaning.transactions.User;
import br.com.ufc.quixada.housecleaning.util.SessionUtil;
import br.com.ufc.quixada.housecleaning.view.HistoryView;
import br.com.ufc.quixada.housecleaning.view.eventlistener.HistoryViewEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HistoryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment implements HistoryViewEventListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private UserDAO userDAO;
    private CleaningServiceDAO cleaningServiceDAO;
    private HistoryView historyView;

    public HistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
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
        final View view = inflater.inflate(R.layout.fragment_history, container, false);

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

        cleaningServiceDAO = CleaningServiceFirebaseDAO.getInstance(new CleaningServiceEventListener() {
            @Override
            public void onAdded(CleaningService cleaningService) {
                if (cleaningService.getRequester().getId().equals(SessionUtil.getCurrentUserId(view.getContext()))) {
                    historyView.addCleaningServiceToList(cleaningService);
                }
            }

            @Override
            public void onChanged(CleaningService cleaningService) {

            }

            @Override
            public void onRemoved(CleaningService cleaningService) {

            }
        });

        historyView = new HistoryView(this);
        historyView.initialize(view);

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
    public void onClickRateCleaningService(final CleaningService cleaningService) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        String[] ratingOptions = {"1 ★", "2 ★", "3 ★", "4 ★", "5 ★"};

        builder.setTitle(R.string.rate_service_label).setSingleChoiceItems(ratingOptions, 0, null).setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Float[] ratingValues = {1f, 2f, 3f, 4f, 5f};
                int checkedItemPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();

                User worker = cleaningService.getResponsible();

                rateWorker(worker.getId(), ratingValues[checkedItemPosition]);

                setCleaningServiceAsRated(cleaningService);

                updateCleaningServiceList();

                dialog.dismiss();
            }
        }).setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    private void rateWorker(String userId, Float rating) {
        User worker = userDAO.findById(userId);

        worker.addRating(rating);

        userDAO.update(worker);
    }

    private void setCleaningServiceAsRated(CleaningService cleaningService) {
        cleaningService.setStatus(CleaningService.Status.RATED);

        cleaningServiceDAO.update(cleaningService);
    }

    private void updateCleaningServiceList() {
        List<CleaningService> cleaningServices = cleaningServiceDAO.findAllByRequester(SessionUtil.getCurrentUserId(getContext()));

        historyView.updateCleaningServiceList(cleaningServices);
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
