package br.com.ufc.quixada.housecleaning.view;

import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import br.com.ufc.quixada.housecleaning.R;
import br.com.ufc.quixada.housecleaning.adapter.WorkerListAdapter;
import br.com.ufc.quixada.housecleaning.transactions.User;

public class WorkerListView extends GenericView {

    private RecyclerView workerListRecyclerView;
    private RecyclerView.Adapter workerListAdapter;
    private RecyclerView.LayoutManager workerListLayoutManager;
    private TextView emptyView;

    private List<User> workers;

    public WorkerListView() {
        workers = new ArrayList<>();
    }

    @Override
    public void initialize(View rootView) {
        super.initialize(rootView);

        workerListRecyclerView = rootView.findViewById(R.id.worker_list_recycler_view);
        emptyView = rootView.findViewById(R.id.empty_view);
        workerListRecyclerView.setHasFixedSize(true);

        workerListLayoutManager = new LinearLayoutManager(rootView.getContext());
        workerListRecyclerView.setLayoutManager(workerListLayoutManager);

        workerListAdapter = new WorkerListAdapter(workers);
        workerListRecyclerView.setAdapter(workerListAdapter);

        if (workers.isEmpty()) {
            workerListRecyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            workerListRecyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getLayoutFile() {
        return R.layout.fragment_worker_list;
    }

    public void updateWorkerList(List<User> users) {
        workers.clear();
        emptyView.setVisibility(View.GONE);
        workerListRecyclerView.setVisibility(View.VISIBLE);
        for (User user : users) {
            workers.add(user);
        }

        ((WorkerListAdapter) workerListAdapter).updateWorkersFull();

        workerListAdapter.notifyDataSetChanged();
    }

    public void addWorkerToList(User worker) {
        workers.add(worker);

        workerListAdapter.notifyDataSetChanged();
    }

    public RecyclerView.Adapter getWorkerListAdapter() {
        return workerListAdapter;
    }
}
