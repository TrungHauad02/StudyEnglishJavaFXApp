package com.example.englishforkids.feature;

import com.example.englishforkids.model.Listening;

import java.util.List;

// Pattern Observer
public interface DataUpdateListener {
    void onUpdateData(List<Listening> lstListening, int currentIndex);
}
