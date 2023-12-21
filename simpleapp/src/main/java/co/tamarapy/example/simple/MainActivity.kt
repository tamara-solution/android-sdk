package co.tamarapy.example.simple

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import co.tamara.sdk.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.testBtn).setOnClickListener {
//            startPayment()
//            TamaraPayment.getOrderDetail(this, "00000")
            TamaraPayment.initialize(
                AUTH_TOKEN,
                API_URL,
                NOTIFICATION_WEB_HOOK_URL,
                PUBLISH_KEY,
                NOTIFICATION_TOKEN,
                true
            )
            TamaraPayment.createOrder("1", "1")
            TamaraPayment.startPayment(this)

        }
    }

    //cancel:"https://qa-tools.tamara.co/cancel"
    //failure:"https://qa-tools.tamara.co/fail"
    //notification:"https://store-demo.com/payments/tamarapay"
    //success:"https://qa-tools.tamara.co/success"

    private fun startPayment() {
//        TamaraPayment.startPayment(this,
//            "https://tamara:tamarapay@2020@checkout-staging.tamara.co/checkout/69b6fe85-ec6a-41f1-8a4b-384dd84760cd?locale=en_US&orderId=1625ef78-2792-4a73-a634-0a2ebc30b4cc",
//            "https://qa-tools.tamara.co/success", "https://qa-tools.tamara.co/fail", "https://qa-tools.tamara.co/cancel")
        TamaraPayment.initialize(
            AUTH_TOKEN,
            API_URL,
            NOTIFICATION_WEB_HOOK_URL,
            PUBLISH_KEY,
            NOTIFICATION_TOKEN,
            true
        )
//        TamaraPayment.getCapturePayment(this,"{\"billing_address\":{\"city\":\"Riyadh\",\"country_code\":\"SA\",\"first_name\":\"Mona\",\"last_name\":\"Lisa\",\"line1\":\"3764 Al Urubah Rd\",\"line2\":\"string\",\"phone_number\":\"966502223333\",\"region\":\"As Sulimaniyah\"},\"canceled_amount\":{\"amount\":0.0,\"currency\":\"SAR\"},\"captured_amount\":{\"amount\":0.0,\"currency\":\"SAR\"},\"consumer\":{\"email\":\"user@example.com\",\"first_name\":\"Mona\",\"last_name\":\"Lisa\",\"phone_number\":\"966502223333\"},\"country_code\":\"SA\",\"created_at\":\"2023-03-30T01:57:38+00:00\",\"description\":\"string\",\"instalments\":3,\"is_mobile\":true,\"items\":[{\"discount_amount\":{\"amount\":100.0,\"currency\":\"SAR\"},\"image_url\":\"https://www.example.com/product.jpg\",\"item_url\":\"https://www.example.com/product.html\",\"name\":\"Lego City 8601\",\"quantity\":1,\"reference_id\":\"123456\",\"sku\":\"SA-12436\",\"tax_amount\":{\"amount\":100.0,\"currency\":\"SAR\"},\"total_amount\":{\"amount\":100.0,\"currency\":\"SAR\"},\"unit_price\":{\"amount\":100.0,\"currency\":\"SAR\"}}],\"locale\":\"en-US\",\"merchant_url\":{\"cancel\":\"tamara://checkout/cancel\",\"failure\":\"tamara://checkout/failure\",\"notification\":\"tamara://checkout/notification\",\"success\":\"tamara://checkout/success\"},\"order_id\":\"e0fd1f72-7844-4d60-bcc0-9c41dcd4325d\",\"order_number\":\"A123456\",\"order_reference_id\":\"123456\",\"paid_amount\":{\"amount\":0.0,\"currency\":\"SAR\"},\"payment_type\":\"PAY_BY_INSTALMENTS\",\"platform\":\"Android\",\"processing\":false,\"refunded_amount\":{\"amount\":0.0,\"currency\":\"SAR\"},\"settlement_status\":\"\",\"shipping_address\":{\"city\":\"Riyadh\",\"country_code\":\"SA\",\"first_name\":\"Mona\",\"last_name\":\"Lisa\",\"line1\":\"3764 Al Urubah Rd\",\"line2\":\"string\",\"phone_number\":\"966502223333\",\"region\":\"As Sulimaniyah\"},\"shipping_amount\":{\"amount\":100.0,\"currency\":\"SAR\"},\"status\":\"expired\",\"store_code\":\"Store code A\",\"tax_amount\":{\"amount\":100.0,\"currency\":\"SAR\"},\"total_amount\":{\"amount\":100.55,\"currency\":\"SAR\"},\"wallet_prepaid_amount\":{\"amount\":0.0,\"currency\":\"SAR\"}}")
        TamaraPayment.renderWidgetProduct(
            this,
            "en",
            "SA",
            "d36c6279-90c2-4239-b4e2-2c91bfda0fe4",
            350.0
        )
//        TamaraPayment.setCustomerInfo("Tuan", "Tran", "502223334", "tuan.tran@tamara.co", true)
//        TamaraPayment.addItem("Test item", "ref-id-123456", "sku-123456",100.0,10.0, 0.0, 1)
//        TamaraPayment.setShippingAmount(50.0)
//        TamaraPayment.setBillingAddress("Tuan", "Tran","502223334"," 191 Tran Phu", "","SA","As Sulimaniyah","Riyadh")
//        TamaraPayment.setShippingAddress("Tuan", "Tran","502223334"," 191 Tran Phu", "","SA","As Sulimaniyah","Riyadh")
//        TamaraPayment.startPayment(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            TamaraPayment.REQUEST_TAMARA_PAYMENT -> {
                if (TamaraPaymentHelper.shouldHandleActivityResult(requestCode, resultCode, data)) {
                    var result = TamaraPaymentHelper.getData(data!!)
                    when (result?.status) {
                        PaymentResult.STATUS_CANCEL -> {
                            Toast.makeText(this, "Payment canceled", Toast.LENGTH_LONG).show()
                        }

                        PaymentResult.STATUS_FAILURE -> {
                            Toast.makeText(
                                this,
                                result.getMessage() ?: "Payment error",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        PaymentResult.STATUS_SUCCESS -> {
                            Toast.makeText(this, "Payment success", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }

            TamaraPayment.REQUEST_TAMARA_ORDER_DETAIL -> {
                if (TamaraInformationHelper.shouldHandleActivityResult(
                        requestCode,
                        resultCode,
                        data
                    )
                ) {
                    var result = TamaraInformationHelper.getData(data!!)
                    when (result?.status) {
                        InformationResult.STATUS_FAILURE -> {
                            Toast.makeText(
                                this,
                                result.getMessage() ?: "Order Detail error",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        InformationResult.STATUS_SUCCESS -> {
                            val orderDetail = TamaraInformationHelper.getDataOrderDetail(data)
                            Toast.makeText(
                                this,
                                "Order Detail success " + orderDetail,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }

            TamaraPayment.REQUEST_TAMARA_CAPTURE_PAYMENT -> {
                if (TamaraInformationHelper.shouldHandleActivityResult(
                        requestCode,
                        resultCode,
                        data
                    )
                ) {
                    var result = TamaraInformationHelper.getData(data!!)
                    when (result?.status) {
                        InformationResult.STATUS_FAILURE -> {
                            Toast.makeText(
                                this,
                                result.getMessage() ?: "Capture Payment error",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        InformationResult.STATUS_SUCCESS -> {
                            val capturePayment = TamaraInformationHelper.getDataCapturePayment(data)
                            Toast.makeText(
                                this,
                                "Capture Payment " + capturePayment,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }

            TamaraPayment.REQUEST_TAMARA_REFUNDS -> {
                if (TamaraInformationHelper.shouldHandleActivityResult(
                        requestCode,
                        resultCode,
                        data
                    )
                ) {
                    var result = TamaraInformationHelper.getData(data!!)
                    when (result?.status) {
                        InformationResult.STATUS_FAILURE -> {
                            Toast.makeText(
                                this,
                                result.getMessage() ?: "Capture Payment error",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        InformationResult.STATUS_SUCCESS -> {
                            val refunds = TamaraInformationHelper.getDataRefunds(data)
                            Toast.makeText(this, "Capture Payment " + refunds, Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }

            TamaraPayment.REQUEST_TAMARA_REFERENCE -> {
                if (TamaraInformationHelper.shouldHandleActivityResult(
                        requestCode,
                        resultCode,
                        data
                    )
                ) {
                    var result = TamaraInformationHelper.getData(data!!)
                    when (result?.status) {
                        InformationResult.STATUS_FAILURE -> {
                            Toast.makeText(
                                this,
                                result.getMessage() ?: "Order Reference error",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        InformationResult.STATUS_SUCCESS -> {
                            val refunds = TamaraInformationHelper.getOrderReference(data)
                            Toast.makeText(this, "Order Reference " + refunds, Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }

            TamaraPayment.REQUEST_TAMARA_CART_PAGE -> {
                if (TamaraInformationHelper.shouldHandleActivityResult(
                        requestCode,
                        resultCode,
                        data
                    )
                ) {
                    var result = TamaraInformationHelper.getData(data!!)
                    when (result?.status) {
                        InformationResult.STATUS_FAILURE -> {
                            Toast.makeText(
                                this,
                                result.getMessage() ?: "Cart page error",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        InformationResult.STATUS_SUCCESS -> {
                            val cartPage = TamaraInformationHelper.getCartPage(data)
                            Toast.makeText(this, "Cart Page " + cartPage, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }

            TamaraPayment.REQUEST_TAMARA_PRODUCT -> {
                if (TamaraInformationHelper.shouldHandleActivityResult(
                        requestCode,
                        resultCode,
                        data
                    )
                ) {
                    var result = TamaraInformationHelper.getData(data!!)
                    when (result?.status) {
                        InformationResult.STATUS_FAILURE -> {
                            Toast.makeText(
                                this,
                                result.getMessage() ?: "Product error",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        InformationResult.STATUS_SUCCESS -> {
                            val cartPage = TamaraInformationHelper.getProduct(data)
                            Toast.makeText(this, "Product " + cartPage, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val API_URL = "https://api-sandbox.tamara.co"
        const val AUTH_TOKEN =
            "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhY2NvdW50SWQiOiJmY2ZiYzk3ZC0wYmIwLTRkYTItYmY3ZS02MjhlOTRkMzM0M2EiLCJ0eXBlIjoibWVyY2hhbnQiLCJzYWx0IjoiNzQxMmZkZjI1NGZiMWJhNmY5N2FmMmY1N2YxYzA1MDYiLCJpYXQiOjE2Nzc4MzIzNzQsImlzcyI6IlRhbWFyYSJ9.WVn2sf3LrW_YI3c2pNrbcOa--tRDAVm9p2GOBRdn7d671QIuqPvDgI9Gz7MNzBirUDnVLATCrL9uvMxDY_1OzXe3Sn1Gawckw-NE2EfL_Kjnl8GcNqwMcMvcin9XGxGRhbDDusgFCFzxaiEYae3DpA-pO0TpyQbEXl49ZLT4a9sEW75Taxc2ofZ-DJ_ciblImk1aJ6p9YhQowvzAVHz6yG-ZRfosxc96t8BK15bVTvTLnT9hzEnCqifqKO7vSu1e2mKEG8lC46pZHSr-ZpvfjSytrMX2QAZuXqxtlvbg3aRZeGiJ-SKVcbRdlId1wSRTZ5lntrw3pyrLS1dpxcfSOA"
        const val NOTIFICATION_WEB_HOOK_URL = "https://tamara.co/pushnotification"
        const val NOTIFICATION_TOKEN = "aeae44a2-5f57-475e-a384-0e9b8a802326"
        const val PUBLISH_KEY = "d36c6279-90c2-4239-b4e2-2c91bfda0fe4"
    }
}
