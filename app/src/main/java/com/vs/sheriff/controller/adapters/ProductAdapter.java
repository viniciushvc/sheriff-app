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
import java.util.List;

public class ProductAdapter extends ArrayAdapter<ProductEntity>
{
    private LayoutInflater inflater;

    public ProductAdapter(@NonNull Context context, @NonNull List<ProductEntity> objects)
    {
        super(context, R.layout.activity_list_product, objects);
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        Holder holder;
        if(convertView == null)
        {
            convertView = inflater.inflate(R.layout.activity_list_product, null);
            holder = new Holder();
            convertView.setTag(holder);
            holder.tvName = convertView.findViewById(R.id.tvName);
            holder.tvBarcode = convertView.findViewById(R.id.tvBarcode);
        }
        else {
            holder = (Holder) convertView.getTag();
        }

        ProductEntity item = getItem(position);
        holder.tvName.setText(item.getName());
        holder.tvBarcode.setText(item.getBarcode());

        return convertView;
    }

    private class Holder
    {
        private TextView tvName;
        private TextView tvBarcode;
    }
}
