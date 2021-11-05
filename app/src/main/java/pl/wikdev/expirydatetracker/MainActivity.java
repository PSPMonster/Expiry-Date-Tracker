package pl.wikdev.expirydatetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MyDatabaseHelper myDB;
    ArrayList<String> product_title, product_expiry_date;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        recyclerView = findViewById(R.id.show_products);
        myDB = new MyDatabaseHelper(MainActivity.this);
        product_title = new ArrayList<>();
        product_expiry_date = new ArrayList<>();

        storeDataInArrays();

        customAdapter = new CustomAdapter(MainActivity.this, product_title, product_expiry_date);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    public void exitApp(View view) {
        finish();
    }

    public void addProduct(View view) {
        Intent intent = new Intent(this, AddProductActivity.class);
        startActivity(intent);
        overridePendingTransition(R.xml.fade_in, R.xml.fade_out);
    }

    void storeDataInArrays() {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                product_title.add(cursor.getString(1));
                product_expiry_date.add(cursor.getString(2));
            }
        }
    }
}