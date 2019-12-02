package br.com.ufc.quixada.housecleaning.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import br.com.ufc.quixada.housecleaning.R;
import br.com.ufc.quixada.housecleaning.transactions.CleaningService;
import br.com.ufc.quixada.housecleaning.view.eventlistener.HistoryViewEventListener;

public class CleaningServiceListAdapter extends RecyclerView.Adapter<CleaningServiceListAdapter.CleaningServiceListViewHolder> {

    private List<CleaningService> cleaningServices;
    private HistoryViewEventListener historyViewEventListener;

    public static class CleaningServiceListViewHolder extends RecyclerView.ViewHolder {
        public CardView cleaningServiceCardView;
        public TextView cleaningServiceDate;
        public TextView cleaningServiceResponsible;
        public TextView cleaningServiceStatus;
        public TextView cleaningServicePrice;
        public Button cleaningServiceRatingButton;

        public CleaningServiceListViewHolder(View view) {
            super(view);
            cleaningServiceCardView = view.findViewById(R.id.cleaning_service_card_view);
            cleaningServiceDate = view.findViewById(R.id.history_item_date);
            cleaningServiceResponsible = view.findViewById(R.id.history_item_responsible);
            cleaningServiceStatus = view.findViewById(R.id.history_item_status);
            cleaningServicePrice = view.findViewById(R.id.history_item_price);
            cleaningServiceRatingButton = view.findViewById(R.id.cleaning_service_rating_button);
        }
    }

    public CleaningServiceListAdapter(List<CleaningService> cleaningServices, HistoryViewEventListener historyViewEventListener) {
        this.cleaningServices = cleaningServices;
        this.historyViewEventListener = historyViewEventListener;
    }

    @NonNull
    @Override
    public CleaningServiceListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cleaning_service_item, parent, false);
        CleaningServiceListViewHolder cleaningServiceListViewHolder = new CleaningServiceListViewHolder(view);

        return cleaningServiceListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CleaningServiceListViewHolder holder, int position) {
        final CleaningService cleaningService = cleaningServices.get(position);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = formatter.format(cleaningService.getDate());

        holder.cleaningServiceDate.setText(dateString);

        holder.cleaningServiceResponsible.setText(cleaningService.getResponsible().getName());

        holder.cleaningServiceStatus.setText(cleaningService.getStatus().toString());

        String price = "R$ " + String.valueOf(cleaningService.getPrice()).replace(".", ",") + "0";
        holder.cleaningServicePrice.setText(price);

        if (cleaningService.getStatus() == CleaningService.Status.DONE) {
            holder.cleaningServiceRatingButton.setVisibility(View.VISIBLE);
        } else {
            holder.cleaningServiceRatingButton.setVisibility(View.GONE);
        }

        holder.cleaningServiceRatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                historyViewEventListener.onClickRateCleaningService(cleaningService);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cleaningServices.size();
    }
}
