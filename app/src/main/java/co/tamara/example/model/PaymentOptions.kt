package co.tamara.example.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
internal data class PaymentOptions (
    @SerializedName("country") var country: String? = null,
    @SerializedName("order_value") var orderValue: EAmount? = null,
    @SerializedName("phone_number") var phoneNumber: String? = null,
    @SerializedName("is_vip") var isVip: Boolean? = false
): Parcelable