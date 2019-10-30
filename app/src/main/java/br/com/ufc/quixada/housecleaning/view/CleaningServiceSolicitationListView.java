package br.com.ufc.quixada.housecleaning.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import br.com.ufc.quixada.housecleaning.R;
import br.com.ufc.quixada.housecleaning.adapter.CleaningServiceSolicitationListAdapter;
import br.com.ufc.quixada.housecleaning.transactions.CleaningService;
import br.com.ufc.quixada.housecleaning.view.eventlistener.CleaningServiceSolicitationListViewEventListener;

public class CleaningServiceSolicitationListView extends GenericView {

    private RecyclerView cleaningServiceSolicitationListRecyclerView;
    private RecyclerView.Adapter cleaningServiceSolicitationListAdapter;
    private RecyclerView.LayoutManager cleaningServiceSolicitationListLayoutManager;

    private List<CleaningService> cleaningServices;
    private CleaningServiceSolicitationListViewEventListener cleaningServiceSolicitationListViewEventListener;

    public CleaningServiceSolicitationListView(CleaningServiceSolicitationListViewEventListener cleaningServiceSolicitationListViewEventListener) {
        cleaningServices = new ArrayList<>();
        this.cleaningServiceSolicitationListViewEventListener = cleaningServiceSolicitationListViewEventListener;
    }

    @Override
    public void initialize(View rootView) {
        super.initialize(rootView);

        cleaningServiceSolicitationListRecyclerView = rootView.findViewById(R.id.cleaning_service_solicitation_list_recycler_view);
        cleaningServiceSolicitationListRecyclerView.setHasFixedSize(true);

        cleaningServiceSolicitationListLayoutManager = new LinearLayoutManager(rootView.getContext());
        cleaningServiceSolicitationListRecyclerView.setLayoutManager(cleaningServiceSolicitationListLayoutManager);

        cleaningServiceSolicitationListAdapter = new CleaningServiceSolicitationListAdapter(cleaningServices, cleaningServiceSolicitationListViewEventListener);
        cleaningServiceSolicitationListRecyclerView.setAdapter(cleaningServiceSolicitationListAdapter);
    }

    @Override
    public int getLayoutFile() {
        return R.layout.fragment_cleaning_service_solicitation_list;
    }

    public void updateCleaningServiceList(List<CleaningService> cleaningServices) {
        this.cleaningServices.clear();

        for (CleaningService cleaningService : cleaningServices) {
            this.cleaningServices.add(cleaningService);
        }

        cleaningServiceSolicitationListAdapter.notifyDataSetChanged();
    }

}
