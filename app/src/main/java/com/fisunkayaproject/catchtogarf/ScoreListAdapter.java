package com.fisunkayaproject.catchtogarf;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fisunkayaproject.catchtogarf.databinding.RecyclerRowBinding;

import java.util.ArrayList;

    public class ScoreListAdapter extends RecyclerView.Adapter<ScoreListAdapter.ModelHolder> {
        private ArrayList<DataModel> modelDataArrayList;
        public ScoreListAdapter(ArrayList<DataModel> modelDataArrayList) {
            this.modelDataArrayList = modelDataArrayList;
        }
        @NonNull
        @Override
        public ModelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            RecyclerRowBinding recyclerRowBinding=RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
            return new ModelHolder(recyclerRowBinding);
        }
        @Override
        public void onBindViewHolder(@NonNull ModelHolder holder, @SuppressLint("RecyclerView") int position) {
            holder.recyclerRowBinding.score.setText(modelDataArrayList.get(position).score);
            holder.recyclerRowBinding.name.setText(modelDataArrayList.get(position).user);
            holder.recyclerRowBinding.date.setText(modelDataArrayList.get(position).date);
            holder.recyclerRowBinding.sira.setText(modelDataArrayList.get(position).sira);

        }
        @Override
        public int getItemCount() {
            return modelDataArrayList.size();
        }
        class ModelHolder extends RecyclerView.ViewHolder {
            RecyclerRowBinding recyclerRowBinding;
            public ModelHolder(RecyclerRowBinding recyclerRowBinding) {
                super(recyclerRowBinding.getRoot());
                this.recyclerRowBinding=recyclerRowBinding;
            }
        }


}
