package com.example.todoapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import org.apache.commons.io.FileUtils;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_ITEM_TEXT = "item_text";
    public static final String KEY_ITEM_POSITION = "item_position";
    public static final int EDIT_TEXT_CODE = 20;

    List<String> items;

    Button btnAdd;
    EditText etItem;
    RecyclerView rvItems;
    ItemsAdapter itemsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        etItem = findViewById(R.id.etItem);
        rvItems = findViewById(R.id.rvItems);

        loadItems();


        ItemsAdapter.OnLongClockListener onLongClockListener = new ItemsAdapter.OnLongClockListener(){
            @Override
            public void onItemLongClicked(int position) {
                // delete item
                items.remove(position);
                //notify adapter
                itemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), "Item was Removed", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        };

        ItemsAdapter.OnClickListener onClickListener = new ItemsAdapter.OnClickListener() {
            @Override
            public void onItemClicked(int position) {
                Log.d("MainActivity", "Single click at position" + position);
                //create new activity
                Intent i = new Intent(MainActivity.this, EditActivity.class);
                //pass data
                i.putExtra(KEY_ITEM_TEXT, items.get(position));
                i.putExtra(KEY_ITEM_POSITION, position);
                //display activity
                startActivityForResult(i, EDIT_TEXT_CODE);
            }
        };
        itemsAdapter = new ItemsAdapter(items, onLongClockListener, onClickListener);
        rvItems.setAdapter(itemsAdapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String todoItem = etItem.getText().toString();

               items.add(todoItem);

               itemsAdapter.notifyItemInserted(items.size() - 1);
               etItem.setText("");
               Toast.makeText(getApplicationContext(), "Item was Added", Toast.LENGTH_SHORT).show();
               saveItems();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == EDIT_TEXT_CODE){
            String itemText = data.getStringExtra(KEY_ITEM_TEXT);

            int position = data.getExtras().getInt(KEY_ITEM_POSITION);

            items.set(position, itemText);

            itemsAdapter.notifyItemChanged(position);

            saveItems();
            Toast.makeText(getApplicationContext(), "Item Updated Successfully", Toast.LENGTH_SHORT).show();
        }
        else{
                Log.w("MainActivity", "Unknown to onActivityResult");
            }

    }

    private File getDataFile(){
        return new File(getFilesDir(), "data.txt");
    }

    // load items by reading data file
    private void loadItems(){
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), String.valueOf(Charset.defaultCharset())));
        } catch (IOException e) {
            Log.e("MainActivity", "Error reading items", e);
            items = new ArrayList<>();
        }
    }
    //saves items by writing data file
    private void saveItems(){
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("MainActivity", "Error writing items", e);
        }
    }
}