package com.example.slider

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SlideViewModelFactory(private val repository: SlideRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SlideViewModel::class.java))
            return SlideViewModel(repository) as T
        throw IllegalArgumentException("Unknown View Model Class")
    }

}