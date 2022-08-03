# Similar-Artist-Android

This is an application created in Android Studios using Java and in collaboration with two other students.
the purpose of this application is to search for an artist of your liking with the output of retrieve a list of similair artist.

The application in built on four classes and one interface. The classs are - Artist, AsyncApiCallCreator, MainActivity and SearchResult.

- Artist.Java:
The purpose of this class is to control the list of artists stored in a ArrayList. With teh functions of adding a artist and also clearing the list when a new search is being made by the user.

- AsyncApiCallCreator:

The purpose of this class is to make calls to the API, the API that we used is from Last.fm using XML. 

- MainActivity:

This class works as a controller and it helps us to switch between different activities (meaning the different views in the application). Its starts from the starting view where the user is able to make a search. When a user is finalizing a search, the view changes to the search result view. 

- SearchResult:
This class is displaying the result of similair artist in a search result view. This is done by retrieving the result from the API request which in turn creates a layout for the list to be displayed.
