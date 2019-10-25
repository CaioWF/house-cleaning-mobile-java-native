package br.com.ufc.quixada.housecleaning.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import br.com.ufc.quixada.housecleaning.R;
import br.com.ufc.quixada.housecleaning.adapter.WorkerListAdapter;
import br.com.ufc.quixada.housecleaning.transactions.Worker;

public class WorkerListView extends GenericView {

    private RecyclerView workerListRecyclerView;
    private RecyclerView.Adapter workerListAdapter;
    private RecyclerView.LayoutManager workerListLayoutManager;

    private List<Worker> workers;

    public WorkerListView() {
        workers = new ArrayList<>();
    }

    @Override
    public void initialize(View rootView) {
        super.initialize(rootView);

        workerListRecyclerView = rootView.findViewById(R.id.worker_list_recycler_view);
        workerListRecyclerView.setHasFixedSize(true);

        workerListLayoutManager = new LinearLayoutManager(rootView.getContext());
        workerListRecyclerView.setLayoutManager(workerListLayoutManager);

        workerListAdapter = new WorkerListAdapter(workers);
        workerListRecyclerView.setAdapter(workerListAdapter);
    }

    @Override
    public int getLayoutFile() {
        return R.layout.fragment_worker_list;
    }

    public void createWorker(Worker worker) {
        workers.add(worker);

        workerListAdapter.notifyDataSetChanged();
    }
}
