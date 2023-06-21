package co.tamara.example.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
internal data class ShippingInfo (
    @SerializedName("shipped_at") var shippedAt: String? = "2019-08-24T14:15:22Z",
    @SerializedName("shipping_company") var shippingCompany: String? = "DHL",
    @SerializedName("tracking_number") var trackingNumber: String? = "",
    @SerializedName("tracking_url") var trackingUrl: String? = ""
): Parcelable