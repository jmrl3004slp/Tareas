package com.itslp.tareas.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.itslp.tareas.R;
import com.itslp.tareas.db.entity.TareasEntity;
import com.itslp.tareas.ui.dialogs.activity.DialogAddActividad;
import com.itslp.tareas.ui.dialogs.works.DialogUpdateWork;

public class MyTareaRecyclerViewAdapter extends ListAdapter<TareasEntity, MyTareaRecyclerViewAdapter.ViewHolder> {
    private FragmentManager mFragmentManager;
    private OnItemClickListener mListener;
    private Context mCtx;
    private Activity mActivity;

    protected MyTareaRecyclerViewAdapter(@NonNull DiffUtil.ItemCallback<TareasEntity> diffCallback) {
        super(diffCallback);
    }

    public MyTareaRecyclerViewAdapter(Context context, Activity activity, FragmentManager supportFragmentManager) {
        super(DIFF_CALLBACK);

        this.mCtx = context;
        this.mActivity = activity;
        this.mFragmentManager = supportFragmentManager;
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
            return oldItem.getNombre().equals(newItem.getNombre());
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
        final TareasEntity currentTareas = getItem(position);
        holder.textViewNombreTarea.setText(currentTareas.getNombre());
        holder.buttonViewOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //creating a popup menu
                PopupMenu popup = new PopupMenu(mCtx, holder.buttonViewOption);
                //inflating menu from xml resource
                popup.inflate(R.menu.menu_options_item);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu__option_edit_tarea:
                                DialogUpdateWork actividadDialog = new DialogUpdateWork(mActivity);
                                actividadDialog.EXTRA_ID = currentTareas.getId();
                                actividadDialog.EXTRA_NAME = currentTareas.getNombre();
                                actividadDialog.show();
                                break;
                            case R.id.menu_option_add_actividad:
                                DialogAddActividad dialog = new DialogAddActividad(mActivity);
                                dialog.EXTRA_ID_WORK = currentTareas.getId();
                                dialog.show();
                                break;
                        }

                        return false;
                    }
                });

                popup.show();
            }
        });
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
        private TextView textViewNombreTarea;
        public ImageView buttonViewOption;

        public ViewHolder(View view) {
            super(view);

            this.textViewNombreTarea = view.findViewById(R.id.txtNombreTarea);
            this.buttonViewOption = view.findViewById(R.id.buttonViewOption);

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
