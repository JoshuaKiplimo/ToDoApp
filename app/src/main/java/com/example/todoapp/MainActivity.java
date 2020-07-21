package com.example.todoapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final  String KEY_ITEM_TEXT = "item_text";
    public static final  String KEY_ITEM_POSITION = "item_position";
    public static final  int EDIT_REQUEST_CODE = 1;

    List<String> items; //define a list of strings as model
    Button btnAdd;
    EditText etItem;
    RecyclerView rvItems;
    ItemsAdapter itemsAdapter;




    @Override
    //informs that main activity has been created. This is the content of activity
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnAdd = findViewById(R.id.btnAdd);
        etItem = findViewById(R.id.etItem);
        rvItems = findViewById(R.id.rvItems);

        //etItem.setText("Wanna see if it works");

        //instantiate list
        loadItems();

         //Capture instance of click, this will return the position of item
        ItemsAdapter.OnClickListener onClickListener = new ItemsAdapter.OnClickListener() {
            @Override
            public void OnItemClick(int position) {
                //This is where we open the edit activity'
                Log.d("MainActivity", "Item was clicked at "+ position);
                //On single click, we want to create a new activity
                    //create an intent to open new activity - takes in 2 parameters - current activity and where we want to go
                     Intent i = new Intent(MainActivity.this, EditActivity.class);
                //Pass data to the other activity
                i.putExtra("item_text",items.get(position) );
                i.putExtra("item_position", position);
                //Display the activity being edited
                startActivityForResult(i, EDIT_REQUEST_CODE);
            }
        };




        //Create an instance of the items adapter on long click
        ItemsAdapter.OnLongClickListener OnLongClickListener = new ItemsAdapter.OnLongClickListener(){
            @Override
            //Gives exact position of item needed to be deleted
            public void OnItemLongClicked(int position) {
                //delete item from adapter
                //Notify the adapter the point at which the item was deleted
                items.remove(position);
                itemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), "Item was removed", Toast.LENGTH_SHORT).show();
                saveItems();

            }
        };

        itemsAdapter = new ItemsAdapter(items, OnLongClickListener, onClickListener);

        rvItems.setAdapter(itemsAdapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoItem = etItem.getText().toString();

                //ADD ITEM TO MODEL
                items.add(todoItem);
                //NOTIFY ADAPTER THAT WE HAVE INSERTED A MODEL
                itemsAdapter.notifyItemInserted(items.size()-1);
                //Clear edit text once it is submited
                etItem.setText("");
                Toast.makeText(getApplicationContext(), "added successfully", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        });


    }

    //Handle result of edit activity ..


    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == EDIT_REQUEST_CODE) {
            //get updated text value
            String itemText = data.getStringExtra(KEY_ITEM_TEXT);

            //extract original position of the edit item from position keY
            int position = data.getExtras().getInt(KEY_ITEM_POSITION);

            //Update model with new updated/edited text
            items.set(position, itemText);
            //Notify the adapter
            itemsAdapter.notifyItemChanged(position);
            //Persist the changes
            saveItems();
            Toast.makeText(getApplicationContext(), "changes have been saved", Toast.LENGTH_SHORT).show();




        } else {
            Log.w("MainActivity", "error encountered");
        }
    }

    //METHODS TO HELP WITH FILE PERSISTENCE
    private File getDataFile(){
        return new File(getFilesDir(), "data.txt");

    }
    //To read data file - Will load item by reading every line of data.txt file
    private void loadItems(){ //Called when there is a change
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity", "Error reading item", e);
            items = new ArrayList<>();

        }

    }
    //To write data to the file - this for saving data
    private void saveItems(){ //when item is added and when item is removed
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("MainActivity", "Error writing item", e);

        }
    }

    private class OnClickListener {
    }
}
