package br.com.ufc.quixada.housecleaning.adapter;

import android.content.Intent;
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
import br.com.ufc.quixada.housecleaning.RequestCleaningServiceActivity;
import br.com.ufc.quixada.housecleaning.network.DownloadImage;
import br.com.ufc.quixada.housecleaning.transactions.User;
import br.com.ufc.quixada.housecleaning.transactions.Worker;

public class WorkerListAdapter extends RecyclerView.Adapter<WorkerListAdapter.WorkerListViewHolder> {

    private List<User> workers;

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

    public WorkerListAdapter(List<User> workers) {
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
        final User worker = workers.get(position);

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

        holder.hireButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RequestCleaningServiceActivity.class);
                intent.putExtra("workerId", worker.getId());

                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return workers.size();
    }

}
