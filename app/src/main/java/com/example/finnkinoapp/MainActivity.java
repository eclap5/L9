package com.example.finnkinoapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;


public class MainActivity extends AppCompatActivity
{
    Spinner spinner;
    ListView listview;
    EditText dateText;
    EditText startTime;
    EditText endTime;

    int id;

    AllTheaters theaters = new AllTheaters();
    AllMovies films = new AllMovies();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        spinner = (Spinner) findViewById(R.id.spinner);
        listview = (ListView) findViewById(R.id.ListView);
        dateText = (EditText) findViewById(R.id.editTextDate);
        startTime = (EditText) findViewById(R.id.editTextTime);
        endTime = (EditText) findViewById(R.id.editTextTime2);

        theaters.readXML();
        films.getDate();

        ArrayAdapter<Theater> spinnerAdapter =new ArrayAdapter<Theater>(this, android.R.layout.simple_spinner_item, theaters.getArray());
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                id = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });
    }


    public void onClick(View v)
    {
        ArrayAdapter<Movie> listViewAdapter = new ArrayAdapter<Movie>(this, android.R.layout.simple_list_item_1, films.getArray());
        listview.setAdapter(listViewAdapter);

        films.clearArrays();
        listViewAdapter.clear();
        films.setID(theaters.getArray().get(id).getID());
        films.setDate(dateText.getText().toString());
        films.readXML();
        films.compareTime(startTime.getText().toString(), endTime.getText().toString());
        listViewAdapter.notifyDataSetChanged();
    }
}