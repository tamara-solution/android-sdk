package co.tamara.example.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(
    tableName = "item"
)
data class EItem(
    @SerializedName("discount_amount") var discountAmount: EAmount? = null,
    var name: String = "",
    var quantity: Int = 0,
    @SerializedName("reference_id") var referenceId: String = "",
    @PrimaryKey(autoGenerate = false)
    var sku: String,
    @SerializedName("tax_amount") var taxAmount: EAmount? = null,
    @SerializedName("total_amount") var totalAmount: EAmount? = null,
    var type: String = "DIGITAL",
    @SerializedName("unit_price") var unitPrice: EAmount? = null
): Parcelable