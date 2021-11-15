package com.example.scribbleshare.searchpage;

import org.json.JSONArray;

/**
 * TODO implement
 */
public interface SearchPageView {
    //add methods here that the search page view needs to update things in the UI
    //then override them in the SearchPage class and actually implement them

    public void setSearchResults(JSONArray a);
    public void refreshSearch(String s);
}
