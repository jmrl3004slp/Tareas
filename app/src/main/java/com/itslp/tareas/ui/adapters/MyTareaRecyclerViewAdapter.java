package com.itslp.tareas.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.itslp.tareas.R;
import com.itslp.tareas.db.entity.TareasEntity;

public class MyTareaRecyclerViewAdapter extends ListAdapter<TareasEntity, MyTareaRecyclerViewAdapter.ViewHolder>  {
    private OnItemClickListener mListener;

    protected MyTareaRecyclerViewAdapter(@NonNull DiffUtil.ItemCallback<TareasEntity> diffCallback) {
        super(diffCallback);
    }

    public MyTareaRecyclerViewAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<TareasEntity> DIFF_CALLBACK = new DiffUtil.ItemCallback<TareasEntity>() {
        @Override
        public boolean areItemsTheSame(TareasEntity oldItem, TareasEntity newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(TareasEntity oldItem, TareasEntity newItem) {
            return oldItem.getNombre().equals(newItem.getNombre()) &&
                    oldItem.getFecha().equals(newItem.getFecha());
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_tarea, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        TareasEntity currentTareas = getItem(position);
        holder.textViewIdTarea.setText(String.valueOf(currentTareas.getId()));
        holder.textViewNombreTarea.setText(currentTareas.getNombre());
    }

    public TareasEntity getTareaAt(int position) {
        return getItem(position);
    }

    public interface OnItemClickListener {
        void onItemClick(TareasEntity tareasEntity);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewIdTarea;
        private TextView textViewNombreTarea;

        public ViewHolder(View view) {
            super(view);

            this.textViewIdTarea = view.findViewById(R.id.txtIdTarea);
            this.textViewNombreTarea = view.findViewById(R.id.txtNombreTarea);

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