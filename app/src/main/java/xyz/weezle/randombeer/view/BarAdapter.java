package xyz.weezle.randombeer.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import xyz.weezle.randombeer.R;
import xyz.weezle.randombeer.model.room.Bar;

public class BarAdapter extends RecyclerView.Adapter<BarAdapter.ViewHolder> {

    private List<Bar> bars;

    public BarAdapter(List<Bar> bars) {
        this.bars = bars;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ligne_bar, parent, false);

        return new BarAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bar bar = bars.get(position);

        holder.tvNomBar.setText(bar.nom);
        holder.tvNbFrigos.setText(" : " + bar.nbFrigos);
        holder.tvNbEtageres.setText(" : " + bar.nbEtageres);
        holder.tvNbBieres.setText(" : " + bar.nbBieres);
    }

    @Override
    public int getItemCount() {
        return bars.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvNomBar;
        public TextView tvNbFrigos;
        public TextView tvNbEtageres;
        public TextView tvNbBieres;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNomBar = (TextView) itemView.findViewById(R.id.tvNomBar);
            tvNbFrigos = (TextView) itemView.findViewById(R.id.tvNbFrigos);
            tvNbEtageres = (TextView) itemView.findViewById(R.id.tvNbEtageres);
            tvNbBieres = (TextView) itemView.findViewById(R.id.tvNbBieres);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Context context = v.getContext();
            int itemPosition = getLayoutPosition();
            int id = bars.get(itemPosition).id;
            Intent intent = new Intent(context, BiereActivity.class);
            intent.putExtra("id", id);
            intent.putExtra("itemPosition", itemPosition);
            ((Activity) context).startActivityForResult(intent, 3);
        }
    }
}
