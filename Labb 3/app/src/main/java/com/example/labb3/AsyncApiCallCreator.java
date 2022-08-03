package com.example.labb3;

import android.content.Context;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
/* Den här klassen är en Creator klass */

//Den här klassen är för Async anrop för Api
public class AsyncApiCallCreator extends AsyncTask<Void,Void,ArrayList<Artist>> {
    //Deklaration av variabler och Artist är klassen Artist
    String searchQuery;
    URL url;
    Artist artist;
    boolean isArtistFound=false;
    public AsyncTaskInterface asyncInterface = null;
    //Konstruktor av klassen och sparar värdet searchQuery
    public AsyncApiCallCreator(String query){
        searchQuery=query;
    }

    @Override
    protected ArrayList<Artist> doInBackground(Void ...params){
        //Den här koden hämtade vi från KAUs repo, från "example_eservice_xml"
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        return sendApiRequest();
    }
    //Den här funktionen gör ett API anrop och returnerar arraylist som består av artist namn
    private ArrayList<Artist> sendApiRequest(){
        String encodedSearchQuery;
        try{
            //Källa: https://stackoverflow.com/questions/6045377/how-to-insert-20-in-place-of-space-in-android
            //Encode query för att inte krascha appen användare matar in mellanslag och specialtecken.  
            encodedSearchQuery = URLEncoder.encode(searchQuery,"UTF-8");

            url = new URL("https://ws.audioscrobbler.com/2.0/?method=artist.getsimilar&artist=" + encodedSearchQuery + "&api_key=9779cfb394c3c8a107607ac74f759fe6&limit=10");            //Kod från eran repository
            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();
            parser.setInput(url.openStream(), null);
            int parserEvent = parser.getEventType();
            String tagName = "", artistName = "";
            //Vi skapar en tom instans av artist
            artist = new Artist("");
            /*
            * 1. Om en ytterligare sökning utförs, så kollar if satsen om listan inte är lika med NULL
            *    så töms listan genom att vi kallar på funktionen resetList()
            * 2. Sedan loopar vi igenom XML till slutet av dokumentet.
            * */
            if(artist.getArtistList()!=null){
                artist.resetList();
            }while (parserEvent != XmlPullParser.END_DOCUMENT) {
                if (parserEvent == XmlPullParser.START_TAG) {
                    tagName = parser.getName();
                    //Om en sökning utförs på en artist som inte finns så avbryter vi loopen med en break.
                    if (tagName.equals("lfm") && parser.getAttributeValue(0).equals("failed")) {
                        break;
                    }
                } else if (parserEvent == XmlPullParser.TEXT) {
                    if (tagName.equals("name")) {
                        artistName = parser.getText();
                        //Den här if satsen kontrollerar de namn som inte innehåller tomma rader och lägger in dem i addArtist
                        if(!artistName.trim().equals("")){
                            artist = new Artist(artistName);
                            artist.addArtist(artist);
                            isArtistFound = true;
                        }
                    }
                }
                //Den här hämtar nästa tagg i XML dokumentet
                parserEvent = parser.next();
            }
        } catch (Exception e) {
            Log.e("bug", Arrays.toString(e.getStackTrace()));
        }
        //Den här IF satsen används för felmeddelande om en artist inte finns i search_result.xml
        if (!isArtistFound) {
            artist.addArtist(artist);
        }
        //Returnerar listan artist
        return artist.getArtistList();
    }
    protected void onPostExecute(ArrayList<Artist> artistResult){
        //anropar funktionen i vår interface
        asyncInterface.getResultFromAsync(artistResult);
    }
}
