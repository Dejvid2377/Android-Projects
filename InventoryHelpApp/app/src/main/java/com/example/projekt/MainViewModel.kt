package com.example.projekt

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private var _sheetName = MutableLiveData("")
    var sheetName : LiveData<String> = _sheetName

    fun saveSheetName(newSheetName : String) {
        _sheetName.value = newSheetName
    }
}