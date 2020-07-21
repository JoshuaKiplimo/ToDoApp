package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static android.view.View.*;

public class EditActivity extends AppCompatActivity {

    //save EditText and SaveButton as variabbles

    EditText etItem;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        //grab references to views in our layout
        etItem = findViewById(R.id.etItem);
        btnSave = findViewById(R.id.btnSave);

        //Make the view more descriptive so that users can know where they are
        getSupportActionBar().setTitle("Edit Item");


        //GET DATA THAT WAS PASSED ALONG WITH THE INTENT and PREPOPULATE TO EDIT TEXT SECTION

        etItem.setText(getIntent().getStringExtra(MainActivity.KEY_ITEM_TEXT));
        //Click listener for when the user is done editing

        btnSave.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //return to main activity  usin a n intent
                Intent intent = new Intent();
                intent.putExtra(MainActivity.KEY_ITEM_TEXT, etItem.getText().toString());
                //At what point should the list be updated
                intent.putExtra(MainActivity.KEY_ITEM_POSITION, getIntent().getExtras().getInt(MainActivity.KEY_ITEM_POSITION));


                //SET RESULT OF THE INTENT
                setResult(RESULT_OK, intent);

                //Finish activity
                finish();


            }
        });
    }


}