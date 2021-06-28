package co.tamara.example

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import co.tamara.example.appvalue.AppConst
import co.tamara.sdk.PaymentResult
import co.tamara.sdk.TamaraPayment
import co.tamara.sdk.TamaraPaymentHelper
import kotlinx.android.synthetic.main.activity_main.*

internal class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        TamaraPayment.initialize(AppConst.AUTH_TOKEN, AppConst.API_URL, AppConst.NOTIFICATION_WEB_HOOK_URL)
        activityBtn.setOnClickListener {
            TamaraPayment.startPayment(this, "https://checkout-staging.tamara.co/checkout/310fdb59-f447-44df-825b-19f467c6774b?locale=en-US",
                    "tamara://success", "tamara://failure", "tamara://cancel")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(TamaraPaymentHelper.shouldHandleActivityResult(requestCode, resultCode, data)){
            var result = TamaraPaymentHelper.getData(data!!)
            when(result?.status){
                PaymentResult.STATUS_CANCEL ->{
                    Toast.makeText(this, R.string.payment_cancel, Toast.LENGTH_LONG).show()
                }
                PaymentResult.STATUS_FAILURE -> {
                    Toast.makeText(this, result.getMessage() ?: getString(R.string.payment_error), Toast.LENGTH_LONG).show()
                }
                PaymentResult.STATUS_SUCCESS -> {
                    Toast.makeText(this, R.string.payment_success, Toast.LENGTH_LONG).show()
                    findNavController(this,R.id.navHostFragment).navigate(R.id.consumerFragment)
                }
            }
        }
    }

}
