package pl.wikdev.expirydatetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddProductActivity extends AppCompatActivity {

    EditText title, expiry_date;
    ImageButton buttonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);


        title = (EditText) findViewById(R.id.edProductName);
        expiry_date = (EditText) findViewById(R.id.edProductExpiryDate);
        buttonAdd = (ImageButton) findViewById(R.id.btnAddProduct);

        expiry_date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // if user is typing string one character at a time
                if (count == 1) {
                    // auto insert dashes while user typing their ssn
                    if (start == 1 || start == 4) {
                        expiry_date.setText(MessageFormat.format("{0}/", expiry_date.getText()));
                        expiry_date.setSelection(expiry_date.getText().length());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.xml.fade_in, R.xml.fade_out);
    }

    public void saveProductAndBackToMenu(View view) {
        if (title.getText().toString().trim().length() > 0 && expiry_date.getText().toString().trim().length() == 10 && validDate(expiry_date.getText().toString().trim())) {
            MyDatabaseHelper myDB = new MyDatabaseHelper(AddProductActivity.this);
            myDB.addProduct(title.getText().toString().trim(), expiry_date.getText().toString().trim());
            Intent intent = new Intent(this, MainActivity.class);
            this.finish();
            startActivity(intent);
            overridePendingTransition(R.xml.fade_in, R.xml.fade_out);
        } else if (title.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "INVALID TITLE LENGTH", Toast.LENGTH_SHORT).show();
        }
        else if (expiry_date.getText().toString().trim().length() < 10) {
            Toast.makeText(this, "INVALID DATA LENGTH", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "INVALID DATA FORMAT", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("SimpleDateFormat")
    private boolean validDate(String dateStr) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        dateFormat.setLenient(false);

        try {
            dateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}