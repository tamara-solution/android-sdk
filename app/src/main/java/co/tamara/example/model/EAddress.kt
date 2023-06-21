package co.tamara.example.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(
    tableName = "address"
)
data class EAddress(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var city: String = "", // Riyadh
    @SerializedName("country_code") var countryCode: String = "", // SA
    @SerializedName("first_name") var firstName: String = "", // Mona
    @SerializedName("last_name") var lastName: String = "", // Lisa
    var line1: String = "", // 3764 Al Urubah Rd
    var line2: String = "", // string
    @SerializedName("phone_number") var phoneNumber: String = "", // 502223333
    var region: String = "" // As Sulimaniyah
): Parcelable