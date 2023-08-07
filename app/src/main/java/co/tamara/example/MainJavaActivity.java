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
        TamaraPayment.Companion.startPayment(this, "https://checkout-sandbox.tamara.co/checkout/4aad3198-019e-483f-a685-4db03c712cfe?locale=en_US&orderId=c6645ea1-2c26-4075-98a1-b313a68303aa&show_item_images=with_item_images_shown&pay_the_difference_disclaimer=yellow&id_match_another_user=changing_phone&pay_in_full_value=full_values",
                "tamara://checkout/success", "tamara://checkout/failure", "tamara://checkout/cancel");
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
