package pl.wikdev.expirydatetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MyDatabaseHelper myDB;
    ArrayList<String> product_title, product_expiry_date, product_id;
    CustomAdapter customAdapter;
    ImageButton btnRemoveProduct;

    ImageView no_data_icon;
    TextView no_data_text;

    private static MainActivity instance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        instance = this;

        btnRemoveProduct = findViewById(R.id.removeProduct);
        recyclerView = findViewById(R.id.show_products);
        no_data_icon = findViewById(R.id.imgNoData);
        no_data_text = findViewById(R.id.txtNoData);

        myDB = new MyDatabaseHelper(MainActivity.this);
        product_title = new ArrayList<>();
        product_expiry_date = new ArrayList<>();
        product_id = new ArrayList<>();

        storeDataInArrays();

        customAdapter = new CustomAdapter(MainActivity.this, product_id, product_title, product_expiry_date);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }



    public void exitApp(View view) {
        finish();
    }

    public void addProduct(View view) {
        Intent intent = new Intent(this, AddProductActivity.class);
        startActivity(intent);
        this.finish();
        overridePendingTransition(R.xml.fade_in, R.xml.fade_out);
    }

    public void updateProduct(View view) {
        Intent intent = new Intent(this, EditProductActivity.class);
        startActivity(intent);
        this.finish();
        overridePendingTransition(R.xml.fade_in, R.xml.fade_out);
    }

    public void inDevelopment(View view) {
        Toast.makeText(MainActivity.this, "Barcode coming soon", Toast.LENGTH_SHORT).show();
    }


    public void storeDataInArrays() {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            setVisible();
        } else {
            while (cursor.moveToNext()) {
                product_id.add(cursor.getString(0));
                product_title.add(cursor.getString(1));
                product_expiry_date.add(cursor.getString(2));
            }
            setGone();
        }
    }

    void setGone() {
        no_data_icon.setVisibility(View.GONE);
        no_data_text.setVisibility(View.GONE);
    }

    void setVisible() {
        ObjectAnimator.ofFloat(no_data_icon, View.ALPHA, 0.0f, 0.6f).setDuration(500).start();
        ObjectAnimator.ofFloat(no_data_text, View.ALPHA, 0.0f, 0.6f).setDuration(500).start();
        no_data_icon.setVisibility(View.VISIBLE);
        no_data_text.setVisibility(View.VISIBLE);
    }

    public static MainActivity getInstance() {
        return instance;
    }



}