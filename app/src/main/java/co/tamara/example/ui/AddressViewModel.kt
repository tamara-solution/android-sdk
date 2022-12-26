package co.tamara.example.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import co.tamara.example.data.DataSource
import co.tamara.example.model.EAddress

class AddressViewModel(private val dataSource: DataSource) : ViewModel() {
    val addresses: LiveData<List<EAddress>> = liveData {
        val addresses = loadDataFromAsset()
        emit(addresses)
    }

    private fun loadDataFromAsset(): List<EAddress> {
        return dataSource.loadListAddress()
    }
}
