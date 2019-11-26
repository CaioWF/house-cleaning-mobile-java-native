package br.com.ufc.quixada.housecleaning.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import br.com.ufc.quixada.housecleaning.R;
import br.com.ufc.quixada.housecleaning.transactions.CleaningService;
import br.com.ufc.quixada.housecleaning.view.eventlistener.CleaningServiceSolicitationListViewEventListener;

public class CleaningServiceSolicitationListAdapter extends RecyclerView.Adapter<CleaningServiceSolicitationListAdapter.CleaningServiceSolicitationListViewHolder> {

    private List<CleaningService> cleaningServices;
    private CleaningServiceSolicitationListViewEventListener cleaningServiceSolicitationListViewEventListener;

    public static class CleaningServiceSolicitationListViewHolder extends RecyclerView.ViewHolder {
        public TextView cleaningServiceDate;
        public TextView cleaningServiceRequester;
        public TextView cleaningServiceStatus;
        public TextView cleaningServicePrice;
        public Button acceptSolicitationButton;
        public Button refuseSolicitationButton;
        public Button finalizeSolicitationButton;

        public CleaningServiceSolicitationListViewHolder(View view) {
            super(view);
            cleaningServiceDate = view.findViewById(R.id.cleaning_service_date);
            cleaningServiceRequester = view.findViewById(R.id.cleaning_service_requester);
            cleaningServiceStatus = view.findViewById(R.id.cleaning_service_status);
            cleaningServicePrice = view.findViewById(R.id.cleaning_service_price);
            acceptSolicitationButton = view.findViewById(R.id.accept_solicitation_button);
            refuseSolicitationButton = view.findViewById(R.id.refuse_solicitation_button);
            finalizeSolicitationButton = view.findViewById(R.id.finalize_solicitation_button);
        }
    }

    public CleaningServiceSolicitationListAdapter(List<CleaningService> cleaningServices, CleaningServiceSolicitationListViewEventListener cleaningServiceSolicitationListViewEventListener) {
        this.cleaningServices = cleaningServices;
        this.cleaningServiceSolicitationListViewEventListener = cleaningServiceSolicitationListViewEventListener;
    }

    @NonNull
    @Override
    public CleaningServiceSolicitationListAdapter.CleaningServiceSolicitationListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cleaning_service_solicitation_list_item, parent, false);
        CleaningServiceSolicitationListViewHolder cleaningServiceSolicitationListViewHolder = new CleaningServiceSolicitationListViewHolder(view);

        return cleaningServiceSolicitationListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CleaningServiceSolicitationListViewHolder holder, int position) {
        final CleaningService cleaningService = cleaningServices.get(position);

        holder.cleaningServiceDate.setText(new SimpleDateFormat("dd/MM/yyyy hh:mm").format(cleaningService.getDate()));
        holder.cleaningServiceRequester.setText(cleaningService.getRequester().getName());
        holder.cleaningServiceStatus.setText(cleaningService.getStatus().toString());
        holder.cleaningServicePrice.setText("R$ " + cleaningService.getPrice());

        if (cleaningService.getStatus() == CleaningService.Status.REFUSED || cleaningService.getStatus() == CleaningService.Status.DONE) {
            holder.acceptSolicitationButton.setVisibility(View.GONE);
            holder.refuseSolicitationButton.setVisibility(View.GONE);
            holder.finalizeSolicitationButton.setVisibility(View.GONE);
        }

        if (cleaningService.getStatus() == CleaningService.Status.ACCEPTED) {
            holder.acceptSolicitationButton.setVisibility(View.GONE);
            holder.refuseSolicitationButton.setVisibility(View.GONE);
            holder.finalizeSolicitationButton.setVisibility(View.VISIBLE);
        }

        if (cleaningService.getStatus() == CleaningService.Status.PENDENT) {
            holder.acceptSolicitationButton.setVisibility(View.VISIBLE);
            holder.refuseSolicitationButton.setVisibility(View.VISIBLE);
            holder.finalizeSolicitationButton.setVisibility(View.GONE);
        }

        holder.acceptSolicitationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleaningServiceSolicitationListViewEventListener.onClickAcceptSolicitation(cleaningService);
            }
        });

        holder.refuseSolicitationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleaningServiceSolicitationListViewEventListener.onClickRefuseSolicitation(cleaningService);
            }
        });

        holder.finalizeSolicitationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleaningServiceSolicitationListViewEventListener.onClickFinalizeSolicitation(cleaningService);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cleaningServices.size();
    }

}
