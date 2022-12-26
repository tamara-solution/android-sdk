package co.tamara.example.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import co.tamara.example.data.DataSource
import co.tamara.example.model.EConsumer

class ConsumerViewModel(private val dataSource: DataSource) : ViewModel() {
    val consumer: LiveData<EConsumer> = liveData {
        val addresses = loadDataFromAsset()
        emit(addresses)
    }

    private fun loadDataFromAsset(): EConsumer {
        return dataSource.loadConsumer()
    }
}
