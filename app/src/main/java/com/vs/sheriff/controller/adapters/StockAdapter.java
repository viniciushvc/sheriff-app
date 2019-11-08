package com.vs.sheriff.controller.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.vs.sheriff.R;
import com.vs.sheriff.controller.database_room.entity.ProductEntity;
import com.vs.sheriff.controller.database_room.entity.StockEntity;

import java.util.List;

public class StockAdapter extends ArrayAdapter<StockEntity> {
    private LayoutInflater inflater;

    public StockAdapter(@NonNull Context context, @NonNull List<StockEntity> objects) {
        super(context, R.layout.activity_list_stock, objects);
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_stock, null);
            holder = new Holder();
            convertView.setTag(holder);

            holder.tvId = convertView.findViewById(R.id.tvId);
            holder.tvStreet = convertView.findViewById(R.id.tvStreet);
            holder.tvColumn = convertView.findViewById(R.id.tvName);
            holder.tvFloor = convertView.findViewById(R.id.tvFloor);
        } else {
            holder = (Holder) convertView.getTag();
        }

        StockEntity item = getItem(position);
        holder.tvId.setText(item.getId().toString());
        holder.tvStreet.setText(item.getStreet());
        holder.tvColumn.setText(item.getColumn());
        holder.tvFloor.setText(item.getFloor());

        return convertView;
    }

    private class Holder {
        private TextView tvId;
        private TextView tvStreet;
        private TextView tvColumn;
        private TextView tvFloor;
    }
}
