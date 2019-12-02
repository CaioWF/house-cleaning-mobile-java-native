package br.com.ufc.quixada.housecleaning.view;

import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import br.com.ufc.quixada.housecleaning.R;
import br.com.ufc.quixada.housecleaning.adapter.CleaningServiceListAdapter;
import br.com.ufc.quixada.housecleaning.transactions.CleaningService;
import br.com.ufc.quixada.housecleaning.view.eventlistener.HistoryViewEventListener;

public class HistoryView extends GenericView {

    private RecyclerView cleaningServiceListRecyclerView;
    private RecyclerView.Adapter cleaningServiceListAdapter;
    private RecyclerView.LayoutManager cleaningServiceListLayoutManager;
    private TextView emptyView;

    private List<CleaningService> cleaningServices;
    private HistoryViewEventListener historyViewEventListener;

    public HistoryView(HistoryViewEventListener historyViewEventListener) {
        this.cleaningServices = new ArrayList<>();
        this.historyViewEventListener = historyViewEventListener;
    }

    @Override
    public void initialize(View rootView) {
        super.initialize(rootView);

        cleaningServiceListRecyclerView = rootView.findViewById(R.id.cleaning_service_recycler_view);
        emptyView = rootView.findViewById(R.id.empty_view2);
        cleaningServiceListRecyclerView.setHasFixedSize(true);

        cleaningServiceListLayoutManager = new LinearLayoutManager(rootView.getContext());
        cleaningServiceListRecyclerView.setLayoutManager(cleaningServiceListLayoutManager);

        cleaningServiceListAdapter = new CleaningServiceListAdapter(cleaningServices, historyViewEventListener);
        cleaningServiceListRecyclerView.setAdapter(cleaningServiceListAdapter);
    }

    @Override
    public int getLayoutFile() {
        return R.layout.fragment_history;
    }

    public void addCleaningServiceToList(CleaningService cleaningService) {
        if (cleaningServiceListRecyclerView.getVisibility() == View.GONE) {
            cleaningServiceListRecyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }

        cleaningServices.add(cleaningService);

        cleaningServiceListAdapter.notifyDataSetChanged();
    }

    public void updateCleaningServiceList(List<CleaningService> cleaningServices) {
        if (cleaningServices.isEmpty()) {
            cleaningServiceListRecyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            cleaningServiceListRecyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }

        this.cleaningServices.clear();

        for (CleaningService cleaningService : cleaningServices) {
            this.cleaningServices.add(cleaningService);
        }

        cleaningServiceListAdapter.notifyDataSetChanged();
    }
}
