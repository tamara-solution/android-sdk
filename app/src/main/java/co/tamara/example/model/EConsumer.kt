package co.tamara.example.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EConsumer(
    @SerializedName("first_name") var firstName: String = "",
    @SerializedName("last_name") var lastName: String = "",
    @SerializedName("phone_number") var phoneNumber: String = "",
    var email: String = "",
    @SerializedName("date_of_birth") var dateOfBirth: String = "",
    @SerializedName("national_id") var nationalId: String = "",
    @SerializedName("is_first_order") var isFirstOrder: Boolean = false
): Parcelable