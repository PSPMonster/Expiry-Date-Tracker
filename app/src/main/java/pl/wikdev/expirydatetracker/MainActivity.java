package pl.wikdev.expirydatetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView yourName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView yourName = findViewById(R.id.yourName);
        EditText inputName = (EditText) findViewById(R.id.textInputEditText_name);


        final Button saveName = findViewById(R.id.buttonSaveName);
        saveName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = inputName.getText().toString();
                yourName.setText("Your name: " + str);
            }
        });
    }

}