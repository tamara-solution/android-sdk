package co.tamara.example.data

import android.content.Context
import co.tamara.example.model.EAddress
import co.tamara.example.model.EConsumer
import co.tamara.example.model.EItem
import co.tamara.example.utils.DataUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DataSource(private val context: Context) {

    fun loadListItem(): List<EItem>{
        val jsonFileString = DataUtils.getJsonDataFromAsset(context, "items.json")
        val listType = object : TypeToken<List<EItem>>() {}.type
        return Gson().fromJson(jsonFileString, listType)
    }

    fun loadListAddress(): List<EAddress>{
        val jsonFileString = DataUtils.getJsonDataFromAsset(context, "addresses.json")
        val listType = object : TypeToken<List<EAddress>>() {}.type
        return Gson().fromJson(jsonFileString, listType)
    }

    fun loadConsumer(): EConsumer {
        val jsonFileString = DataUtils.getJsonDataFromAsset(context, "consumer.json")
        val listType = object : TypeToken<EConsumer>() {}.type
        return Gson().fromJson(jsonFileString, listType)
    }

}