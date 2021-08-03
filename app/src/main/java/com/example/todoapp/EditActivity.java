package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

    EditText itEdit;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        itEdit = findViewById(R.id.itEdit);
        btnSave = findViewById(R.id.btnSave);

        getSupportActionBar().setTitle("Edit item");

        itEdit.setText(getIntent().getStringExtra(MainActivity.KEY_ITEM_TEXT));
        //user done editing
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // create intent containing results
                Intent intent = new Intent();
                //pass editing results
                intent.putExtra(MainActivity.KEY_ITEM_TEXT,itEdit.getText().toString());
                intent.putExtra(MainActivity.KEY_ITEM_POSITION, getIntent().getExtras().getInt(MainActivity.KEY_ITEM_POSITION));
                //set results of intent
                setResult(RESULT_OK, intent);
                //finish activity
                finish();
            }
        });
    }
}