package co.tamara.example.ui

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import co.tamara.example.model.EItem

class OrderViewModel : ViewModel(){

    val items = MediatorLiveData<List<EItem>>()

    fun updateItems(items: List<EItem>){
        this.items.postValue(items)
    }
}