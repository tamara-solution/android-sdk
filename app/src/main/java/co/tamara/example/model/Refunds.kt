package co.tamara.example.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Refunds (
    @SerializedName("capture_id") var captureId: String? = null,
    @SerializedName("total_amount") var totalAmount: EAmount? = null,
    @SerializedName("refund_id") var refundId: String? = null,
    @SerializedName("tax_amount") var taxAmount: EAmount? = null,
    @SerializedName("shipping_amount") var shippingAmount: EAmount? = null,
    @SerializedName("discount_amount") var discountAmount: EAmount? = null,
    var items: ArrayList<EItem> = arrayListOf()
): Parcelable