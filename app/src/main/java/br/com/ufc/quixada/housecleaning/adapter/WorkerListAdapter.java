package br.com.ufc.quixada.housecleaning.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import br.com.ufc.quixada.housecleaning.R;
import br.com.ufc.quixada.housecleaning.RequestCleaningServiceActivity;
import br.com.ufc.quixada.housecleaning.transactions.User;
import br.com.ufc.quixada.housecleaning.view.eventlistener.WorkerListViewEventListener;

public class WorkerListAdapter extends RecyclerView.Adapter<WorkerListAdapter.WorkerListViewHolder> implements Filterable {

    private List<User> workers;

    private WorkerListViewEventListener workerListViewEventListener;

    private List<User> workersFull;

    public static class WorkerListViewHolder extends RecyclerView.ViewHolder {
        public CardView workerCardView;
        public TextView workerName;
        public TextView workerRate;
        public ImageView workerPhoto;
        public Button hireButton;
        public Button viewDetailsButton;

        public WorkerListViewHolder(View view) {
            super(view);
            workerCardView = view.findViewById(R.id.worker_card_view);
            workerName = view.findViewById(R.id.worker_name_text_view);
            workerRate = view.findViewById(R.id.worker_rate_text_view);
            workerPhoto = view.findViewById(R.id.worker_photo_image_view);
            hireButton = view.findViewById(R.id.hire_button);
            viewDetailsButton = view.findViewById(R.id.view_details_button);
        }
    }

    public WorkerListAdapter(List<User> workers, WorkerListViewEventListener workerListViewEventListener) {
        this.workers = workers;
        this.workerListViewEventListener = workerListViewEventListener;
        this.workersFull = new ArrayList<>(workers);
    }

    public void updateWorkersFull() {
        this.workersFull = new ArrayList<>(workers);
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

        if (worker.getPhoto() != null && !(worker.getPhoto().trim().isEmpty())) {
            Picasso.with(holder.itemView.getContext()).load(worker.getPhoto()).fit().centerCrop().into(holder.workerPhoto);
        }

        holder.workerName.setText(worker.getName());
        holder.workerRate.setText(worker.getRating() + " ★");

        holder.hireButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workerListViewEventListener.onClickHireButton(worker);
            }
        });

        holder.viewDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workerListViewEventListener.onClickViewDetailsButton(worker);
            }
        });
    }

    @Override
    public int getItemCount() {
        return workers.size();
    }

    @Override
    public Filter getFilter() {
        return workersFilter;
    }

    private Filter workersFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<User> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(workersFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (User user : workersFull) {
                    if (user.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(user);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            workers.clear();
            workers.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
