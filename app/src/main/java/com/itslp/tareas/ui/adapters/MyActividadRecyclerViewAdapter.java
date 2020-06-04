package com.itslp.tareas.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.itslp.tareas.R;
import com.itslp.tareas.db.entity.ActividadesEntity;

public class MyActividadRecyclerViewAdapter extends ListAdapter<ActividadesEntity, MyActividadRecyclerViewAdapter.ViewHolder> {
    private MyActividadRecyclerViewAdapter.OnItemClickListener mListener;

    protected MyActividadRecyclerViewAdapter(@NonNull DiffUtil.ItemCallback<ActividadesEntity> diffCallback) {
        super(diffCallback);
    }

    public MyActividadRecyclerViewAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<ActividadesEntity> DIFF_CALLBACK = new DiffUtil.ItemCallback<ActividadesEntity>() {
        @Override
        public boolean areItemsTheSame(ActividadesEntity oldItem, ActividadesEntity newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(ActividadesEntity oldItem, ActividadesEntity newItem) {
            return oldItem.getActividad().equals(newItem.getActividad()) &&
                    oldItem.isTerminada() == newItem.isTerminada();
        }
    };

    @NonNull
    @Override
    public MyActividadRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_actividad, parent, false);
        return new MyActividadRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyActividadRecyclerViewAdapter.ViewHolder holder, int position) {
        ActividadesEntity currentTareas = getItem(position);
        holder.chckActividad.setChecked(currentTareas.isTerminada());
        holder.chckActividad.setText(currentTareas.getActividad());
    }

    public ActividadesEntity getTareaAt(int position) {
        return getItem(position);
    }

    public interface OnItemClickListener {
        void onItemClick(ActividadesEntity actividadesEntity);
    }

    public void setOnItemClickListener(MyActividadRecyclerViewAdapter.OnItemClickListener listener) {
        this.mListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CheckBox chckActividad;

        public ViewHolder(View view) {
            super(view);

            this.chckActividad = view.findViewById(R.id.checkBox);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (mListener != null && position != RecyclerView.NO_POSITION) {
                        mListener.onItemClick(getItem(position));
                    }
                }
            });
        }

        @Override
        public String toString() {
            return super.toString() + " '" + "'";
        }
    }
}
