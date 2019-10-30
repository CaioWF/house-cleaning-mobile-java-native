package br.com.ufc.quixada.housecleaning.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.ufc.quixada.housecleaning.R;
import br.com.ufc.quixada.housecleaning.adapter.CleaningServiceListAdapter;
import br.com.ufc.quixada.housecleaning.transactions.CleaningService;

public class CleaningServicesListView extends GenericView{

    private RecyclerView cleaningServiceListRecyclerView;
    private RecyclerView.Adapter cleaningServiceListAdapter;
    private RecyclerView.LayoutManager cleaningServiceListLayoutManager;
    private TextView emptyView;

    private List<CleaningService> cleaningServices;

    public CleaningServicesListView() {
        cleaningServices = new ArrayList<>();
    }

    @Override
    public void initialize(View rootView) {
        super.initialize(rootView);

        cleaningServiceListRecyclerView = rootView.findViewById(R.id.cleaning_service_recycler_view);
        emptyView = (TextView) rootView.findViewById(R.id.empty_view2);
        cleaningServiceListRecyclerView.setHasFixedSize(true);

        cleaningServiceListLayoutManager = new LinearLayoutManager(rootView.getContext());
        cleaningServiceListRecyclerView.setLayoutManager(cleaningServiceListLayoutManager);

        cleaningServiceListAdapter = new CleaningServiceListAdapter(cleaningServices);
        cleaningServiceListRecyclerView.setAdapter(cleaningServiceListAdapter);

        if (cleaningServices.isEmpty()) {
            cleaningServiceListRecyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
        else {
            cleaningServiceListRecyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getLayoutFile() {
        return R.layout.fragment_history;
    }

    public void createCleaningServices(CleaningService cleaningService) {
        cleaningServices.add(cleaningService);
        cleaningServiceListRecyclerView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);

        cleaningServiceListAdapter.notifyDataSetChanged();
    }
}
