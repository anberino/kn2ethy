package com.example.kneethy2.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is your pretty note. Remember it, for everything in life is temporary. We are rivers, waiting to come ashore."
    }
    val text: LiveData<String> = _text
}