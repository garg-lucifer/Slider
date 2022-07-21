package com.example.slider

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SlideViewModel(private val repository: SlideRepository) : ViewModel() {

    val slideData = repository.slideData

    fun insert(slideData: SlideData){
        viewModelScope.launch {
            repository.insert(slideData)
        }
    }

    fun update(slideData: SlideData){
        viewModelScope.launch {
            repository.update(slideData)
        }
    }

    fun delete(slideData: SlideData){
        viewModelScope.launch {
            repository.delete(slideData)
        }
    }

    fun size() : LiveData<Int>{
        val result = MutableLiveData<Int>()
        viewModelScope.launch {
            val returned = repository.getSize()
            result.postValue(returned)
        }
        return result
    }

    fun getNext(curStart : Int): LiveData<SlideData>{
        val result = MutableLiveData<SlideData>()
        viewModelScope.launch {
            val returned = repository.getNext(curStart)
            result.postValue(returned)
        }
        return result
    }

    fun getPrev(curStart : Int): LiveData<SlideData>{
        val result = MutableLiveData<SlideData>()
        viewModelScope.launch {
            val returned = repository.getPrev(curStart)
            result.postValue(returned)
        }
        return result
    }

    fun getNextSize(curStart: Int): LiveData<Int>{
        val result = MutableLiveData<Int>()
        viewModelScope.launch {
            val returned = repository.getNextSize(curStart)
            result.postValue(returned)
        }
        return result
    }

}