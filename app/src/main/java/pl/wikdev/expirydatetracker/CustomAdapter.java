package pl.wikdev.expirydatetracker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>{

    private Context context;
    private ArrayList product_title, product_expiry_date, product_id;
    int position;

    CustomAdapter(Context context, ArrayList product_id, ArrayList product_title, ArrayList product_expiry_date) {
        this.context = context;
        this.product_id = product_id;
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
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        this.position = position;

        holder.product_title_txt.setText(String.valueOf(product_title.get(position)));
        holder.product_expiry_date_txt.setText(String.valueOf("Expiry date: " +product_expiry_date.get(position)));

        holder.btnRemoveProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.getInstance().setVisible();
                getAndRemoveProduct(position);
            }
        });
    }


    public void getAndRemoveProduct(int position) {
        String _id = String.valueOf(product_id.get(position));
        MyDatabaseHelper myDB = new MyDatabaseHelper(context);
        myDB.deleteProduct(_id);

        product_title.remove(position);
        product_expiry_date.remove(position);
        product_id.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());

    }


    @Override
    public int getItemCount() {
        return product_title.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView product_title_txt, product_expiry_date_txt, product_id;
        ImageButton btnRemoveProduct;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            product_title_txt = itemView.findViewById(R.id.txtProductName);
            product_expiry_date_txt = itemView.findViewById(R.id.txtExpiryDate);
            btnRemoveProduct = itemView.findViewById(R.id.removeProduct);
        }
    }
}
