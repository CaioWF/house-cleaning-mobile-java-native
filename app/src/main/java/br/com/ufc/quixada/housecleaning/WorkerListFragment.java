package br.com.ufc.quixada.housecleaning;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.ufc.quixada.housecleaning.dao.WorkerDAO;
import br.com.ufc.quixada.housecleaning.dao.memory.WorkerMemoryDAO;
import br.com.ufc.quixada.housecleaning.presenter.EventListener;
import br.com.ufc.quixada.housecleaning.transactions.Worker;
import br.com.ufc.quixada.housecleaning.view.WorkerListView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WorkerListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WorkerListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkerListFragment extends Fragment implements EventListener<Worker> {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private WorkerDAO workerDAO = WorkerMemoryDAO.getInstance(this);
    private WorkerListView workerListView;

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

        workerListView = new WorkerListView();
        workerListView.initialize(view);

        workerDAO.create(new Worker("", "Just Testing", (float) 4.5));
        workerDAO.create(new Worker("", "Just Testing", (float) 4.5));
        workerDAO.create(new Worker("", "Just Testing", (float) 4.5));
        workerDAO.create(new Worker("", "Just Testing", (float) 4.5));
        workerDAO.create(new Worker("", "Just Testing", (float) 4.5));

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
    public void onCreate(Worker worker) {
        workerListView.createWorker(worker);
    }

    @Override
    public void onUpdate(Worker worker) {

    }

    @Override
    public void onDelete(Worker worker) {

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