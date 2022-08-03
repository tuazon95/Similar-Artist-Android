package com.example.labb3;

import java.util.ArrayList;
/* Den här klassen blir en expert klass, då den innehåller alla artister som hämtas */

//En klass som innehåller namnet på en artist
public class Artist extends ArtistList{
    private String artistName;
    public Artist(String name){
        artistName=name;
    }
    public String getName(){
        return artistName;
    }
}
//En lista med alla artistnamn
class ArtistList{
    //Syftet med static är att listan inte ska tömmas vid varje anrop
    static ArrayList<Artist> artistList;
    public ArtistList(){
        if(artistList==null){
            artistList = new ArrayList<>();
        }
    }
    public void addArtist(Artist artistClass){
        artistList.add(artistClass);
    }
    public ArrayList<Artist> getArtistList(){return artistList;}
    public void resetList(){
        artistList.clear();
    }
}