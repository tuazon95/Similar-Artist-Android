package com.example.labb3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.SearchView;


//Controller för att den byter mellan aktiviteter
public class MainActivity extends AppCompatActivity{
    private SearchView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Vi lägger en lyssnare på vår SearchView
        search = (SearchView) findViewById(R.id.SearchInput);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            /*
            * När en användare trycker på enter så byter vi activity med hjälp av explicitintent
            * Det som finns i sökrutan skickas som värde till activity
            * */
            @Override
            public boolean onQueryTextSubmit(String s) {
                String searchQuery = search.getQuery().toString();
                Intent explicitIntent = new Intent(MainActivity.this, SearchResult.class);
                explicitIntent.putExtra("searchQuery",searchQuery);
                startActivity(explicitIntent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }
}