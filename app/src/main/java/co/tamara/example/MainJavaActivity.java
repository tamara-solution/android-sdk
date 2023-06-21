package co.tamara.example;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import co.tamara.sdk.PaymentResult;
import co.tamara.sdk.TamaraPayment;
import co.tamara.sdk.TamaraPaymentHelper;

public class MainJavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TamaraPayment.Companion.startPayment(this, "https://checkout-staging.tamara.co/checkout/310fdb59-f447-44df-825b-19f467c6774b?locale=en-US",
                "tamara://success", "tamara://failure", "tamara://cancel");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(TamaraPaymentHelper.Companion.shouldHandleActivityResult(requestCode, resultCode, data)){
            PaymentResult result = TamaraPaymentHelper.Companion.getData(data);
            if(PaymentResult.STATUS_CANCEL == result.getStatus()){
                Toast.makeText(this, R.string.payment_cancel, Toast.LENGTH_LONG).show();
            } else if(PaymentResult.STATUS_FAILURE == result.getStatus()){
                Toast.makeText(this,  result.getMessage() != null ? result.getMessage() : getString(R.string.payment_error), Toast.LENGTH_LONG).show();
            } else if(PaymentResult.STATUS_SUCCESS == result.getStatus()){
                Toast.makeText(this, R.string.payment_success, Toast.LENGTH_LONG).show();
            }
        }
    }
}
