package co.tamara.example.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EDiscount(
    var amount: EAmount,
    var name: String = "" // Christmas 2020
): Parcelable