package ipn.docentes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class LabAdapter extends RecyclerView.Adapter<LabAdapter.LabViewHolder> {

    public interface OnLabItemClickListener {
        void onLabItemClick(int position);
    }

    private List<LabModel> labList;
    private OnLabItemClickListener onLabItemClickListener;

    public LabAdapter(List<LabModel> labList, OnLabItemClickListener listener) {
        this.labList = labList;
        this.onLabItemClickListener = listener;
    }

    @NonNull
    @Override
    public LabViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lab, parent, false);
        return new LabViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LabViewHolder holder, int position) {
        LabModel lab = labList.get(position);
        holder.labName.setText(lab.getNombre());
        holder.labCapacity.setText("Capacidad: " + lab.getCapacidad());
        holder.labStatus.setText("Estado: " + lab.getEstado());
    }

    @Override
    public int getItemCount() {
        return labList.size();
    }

    public class LabViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView labName;
        public TextView labCapacity;
        public TextView labStatus;

        public LabViewHolder(@NonNull View itemView) {
            super(itemView);
            labName = itemView.findViewById(R.id.lab_name);
            labCapacity = itemView.findViewById(R.id.lab_capacity);
            labStatus = itemView.findViewById(R.id.lab_status);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (onLabItemClickListener != null) {
                onLabItemClickListener.onLabItemClick(getAdapterPosition());
            }
        }
    }
}


