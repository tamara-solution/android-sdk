package co.tamara.example.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
internal data class CancelOrder (
    @SerializedName("total_amount") var totalAmount: EAmount? = null
): Parcelable