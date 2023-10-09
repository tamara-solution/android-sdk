package co.tamara.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.tamara.example.application.App
import co.tamara.example.ui.AddressViewModel
import co.tamara.example.ui.ConsumerViewModel
import co.tamara.example.ui.OrderViewModel
import co.tamara.example.ui.ShopViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ShopViewModel::class.java) -> {
                ShopViewModel(App.instance.dataSource) as T
            }
            modelClass.isAssignableFrom(AddressViewModel::class.java) -> {
                AddressViewModel(App.instance.dataSource) as T
            }
            modelClass.isAssignableFrom(ConsumerViewModel::class.java) -> {
                ConsumerViewModel(App.instance.dataSource) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}