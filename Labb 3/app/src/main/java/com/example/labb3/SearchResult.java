package com.example.labb3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

/*Vi väljer den här klassen som ännu en Controller klass och en Low Coupling till AsyncTaskInterface.Java.
Då den implementerar interface klassen.

*Här implementerar vi interface klassen till aktivitet searchResult, för att hämta API resultatet.
 Vi fick inspiration av att använda oss av en interface genom
* https://stackoverflow.com/questions/12575068/how-to-get-the-result-of-onpostexecute-to-main-activity-because-asynctask-is-a
* Första svaret på posten
* */
public class SearchResult extends AppCompatActivity implements AsyncTaskInterface{
    int totalResult;
    ArrayList<Artist> artistList;
    private SearchView search;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result);
        //Hämtar värde från intent och skickar det till API klassen
        Intent intent = getIntent();
        String intentString = intent.getStringExtra("searchQuery");
        AsyncApiCallCreator asyncApi = new AsyncApiCallCreator(intentString);
        asyncApi.asyncInterface=this;
        asyncApi.execute();
        search = (SearchView) findViewById(R.id.SearchInput);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String s) {
                String searchQuery = search.getQuery().toString();
                Intent explicitIntent = new Intent(SearchResult.this, SearchResult.class);
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
    //Den här hämtar värde från vår API request och anropar funktionen för gränssnittet
    @Override
    public void getResultFromAsync(ArrayList<Artist> artistList){
        totalResult = artistList.size();
        if(artistList.get(0).getName()==""){
            makeErrorVisible();
        }else{
            createResultView(artistList,totalResult);
        }
    }
    //Den här skapar gränssnittet för artistlist med komponenterna ListView och TextView.
    private void createResultView(ArrayList<Artist> artistList,int total){
        TextView resultMessage = findViewById(R.id.ResultMessage);
        resultMessage.setText("Hej, vi har hittat "+total+" liknande artister!");
        resultMessage.setVisibility(View.VISIBLE);
        ListView resultList = findViewById(R.id.ResultList);
        TextView artistName;
        ArrayList<String> itemList = new ArrayList<>();
        for(int i=0;i<total;i++){
            itemList.add("name: "+artistList.get(i).getName());
        }
        ArrayAdapter listAdapter = new ArrayAdapter(SearchResult.this,android.R.layout.simple_list_item_1,itemList);
        resultList.setAdapter(listAdapter);
    }
    //Den här funktionen gör felmeddelandet synlig, då den är inställd på Invisible i search_result.xml
    private void makeErrorVisible(){
        TextView errorText = findViewById(R.id.ErrorText);
        errorText.setVisibility(View.VISIBLE);
    }
}