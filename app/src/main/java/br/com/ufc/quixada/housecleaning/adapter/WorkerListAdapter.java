package br.com.ufc.quixada.housecleaning.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.ufc.quixada.housecleaning.R;
import br.com.ufc.quixada.housecleaning.network.DownloadImage;
import br.com.ufc.quixada.housecleaning.transactions.Worker;

public class WorkerListAdapter extends RecyclerView.Adapter<WorkerListAdapter.WorkerListViewHolder> {

    private List<Worker> workers;

    public static class WorkerListViewHolder extends RecyclerView.ViewHolder {
        public CardView workerCardView;
        public TextView workerName;
        public TextView workerRate;
        public ImageView workerPhoto;
        public Button viewProfileButton;
        public Button hireButton;

        public WorkerListViewHolder(View view) {
            super(view);
            workerCardView = view.findViewById(R.id.worker_card_view);
            workerName = view.findViewById(R.id.worker_name_text_view);
            workerRate = view.findViewById(R.id.worker_rate_text_view);
            workerPhoto = view.findViewById(R.id.worker_photo_image_view);
            viewProfileButton = view.findViewById(R.id.view_profile_button);
            hireButton = view.findViewById(R.id.hire_button);
        }
    }

    public WorkerListAdapter(List<Worker> workers) {
        this.workers = workers;
    }

    @NonNull
    @Override
    public WorkerListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.worker_list_item, parent, false);
        WorkerListViewHolder workerListViewHolder = new WorkerListViewHolder(view);

        return workerListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WorkerListViewHolder holder, int position) {
        Worker worker = workers.get(position);

        holder.workerName.setText(worker.getName());
        holder.workerRate.setText(worker.getRate() + " ★");
        // Load Photo
        new DownloadImage(holder.workerPhoto).execute(worker.getPhoto());

        holder.viewProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // To implement
            }
        });

        holder.viewProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // To implement
            }
        });
    }

    @Override
    public int getItemCount() {
        return workers.size();
    }

}