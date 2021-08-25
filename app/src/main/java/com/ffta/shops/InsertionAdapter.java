package com.ffta.shops;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class InsertionAdapter extends RecyclerView.Adapter<InsertionAdapter.AdapterManager> {
List<ShopData> list;
Context c;



    public InsertionAdapter(List<ShopData> list,Context c)
    {
       this.list=list;
       this.c=c;
    }
    @NonNull
    @Override
    public AdapterManager onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li=LayoutInflater.from(c);
        View view=li.inflate(R.layout.recycler,parent,false);
        return new AdapterManager(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterManager holder, int i) {
        Toast.makeText(c,list.size()+"",Toast.LENGTH_LONG).show();
holder.name.setText(list.get(i).getName());
holder.item.setText(list.get(i).getItem());
holder.amount.setText(list.get(i).getAmount()+"");
holder.system.setText(list.get(i).getP_b());
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class AdapterManager extends RecyclerView.ViewHolder {
        TextView name,item,amount,system;
        public AdapterManager(@NonNull View v) {
            super(v);
            name=v.findViewById(R.id.name);
            item=v.findViewById(R.id.item);
            amount=v.findViewById(R.id.amount);
            system=v.findViewById(R.id.system);

        }
    }
}
