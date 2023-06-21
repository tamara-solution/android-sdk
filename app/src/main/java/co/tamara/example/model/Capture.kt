package co.tamara.example.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
internal data class Capture (
    @SerializedName("order_id") var orderId: String? = null,
    @SerializedName("total_amount") var totalAmount: EAmount? = null,
    @SerializedName("shipping_info") var shippingInfo: ShippingInfo? = null
): Parcelable