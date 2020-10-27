package co.tamara.example.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.text.NumberFormat
import java.util.*

@Parcelize
data class EAmount(
    var amount: Double = 0.0, // 50.00
    var currency: String = "" // SAR
): Parcelable {
    fun getFormattedAmount(): String{
        val format: NumberFormat = NumberFormat.getCurrencyInstance()
        format.maximumFractionDigits = 0
        format.currency = Currency.getInstance("SAR")
        return format.format(amount)
    }
}