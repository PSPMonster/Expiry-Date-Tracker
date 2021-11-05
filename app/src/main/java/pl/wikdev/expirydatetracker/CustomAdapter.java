package pl.wikdev.expirydatetracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>{

    private Context context;
    private ArrayList product_title, product_expiry_date;
    CustomAdapter(Context context, ArrayList product_title, ArrayList product_expiry_date) {
        this.context = context;
        this.product_title = product_title;
        this.product_expiry_date = product_expiry_date;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.product_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.product_title_txt.setText(String.valueOf(product_title.get(position)));
        holder.product_expiry_date_txt.setText(String.valueOf(product_expiry_date.get(position)));

    }

    @Override
    public int getItemCount() {
        return product_title.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView product_title_txt, product_expiry_date_txt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            product_title_txt = itemView.findViewById(R.id.txtProductName);
            product_expiry_date_txt = itemView.findViewById(R.id.txtExpiryDate);
        }
    }
}
