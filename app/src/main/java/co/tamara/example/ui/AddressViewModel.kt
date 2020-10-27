package co.tamara.example.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import co.tamara.example.data.DataSource
import co.tamara.example.model.EAddress

class AddressViewModel(private val dataSource: DataSource) : ViewModel(){
    val addresses: LiveData<List<EAddress>> = liveData {
        var addresses = loadDataFromAsset()
        emit(addresses)
    }
    private suspend fun loadDataFromAsset(): List<EAddress> {
        return dataSource.loadListAddress()
    }
}
