package br.com.ufc.quixada.housecleaning.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.com.ufc.quixada.housecleaning.R;
import br.com.ufc.quixada.housecleaning.transactions.CleaningService;

public class CleaningServiceListAdapter extends RecyclerView.Adapter<CleaningServiceListAdapter.CleaningServiceListViewHolder> {
    private List<CleaningService> cleaningServices;

    public static class CleaningServiceListViewHolder extends RecyclerView.ViewHolder {
        public CardView cleaningServiceCardView;
        public TextView cleaningServiceDate;
        public TextView cleaningServiceResponsible;
        public TextView cleaningServiceStatus;
        public TextView cleaningServicePrice;
        public Button seeDetails;

        public CleaningServiceListViewHolder(View view) {
            super(view);
            cleaningServiceCardView = view.findViewById(R.id.cleaning_service_card_view);
            cleaningServiceDate = view.findViewById(R.id.history_item_date);
            cleaningServiceResponsible = view.findViewById(R.id.history_item_responsible);
            cleaningServiceStatus = view.findViewById(R.id.history_item_status);
            cleaningServicePrice = view.findViewById(R.id.history_item_price);
            seeDetails = view.findViewById(R.id.btn_see_details);
        }
    }

    public CleaningServiceListAdapter(List<CleaningService> cleaningServices) {
        this.cleaningServices = cleaningServices;
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
        CleaningService cleaningService = cleaningServices.get(position);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = formatter.format(cleaningService.getDatetime().getDate());

        holder.cleaningServiceDate.setText(dateString);
        holder.cleaningServiceResponsible.setText(cleaningService.getResponsible().getName());

        String status;
        int value = cleaningService.getStatus().getValue();
        if(value == 0) {
            status = "Pendente";
        } else if(value == 1) {
            status = "Fazendo";
        } else {
            status = "Completa";
        }

        holder.cleaningServiceStatus.setText(status);
        holder.cleaningServicePrice.setText("R$ " + String.valueOf(cleaningService.getPrice()).replace(".", ",") + "0");

        holder.seeDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // To implement
            }
        });
    }

    @Override
    public int getItemCount() {
        return cleaningServices.size();
    }
}
