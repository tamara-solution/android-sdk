package co.tamara.example.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
internal data class PaymentRefund (
    @SerializedName("comment") var comment: String? = null,
    @SerializedName("total_amount") var totalAmount: EAmount? = null
): Parcelable