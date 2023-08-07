## Tarama Pay SDK's requirements
To intergrate Tamara Pay SDK to your app, you need AUTH_TOKEN, API_URL and your server NOTIFICATION_WEBHOOK_URL:
```
const val API_URL = "https://api-sandbox.tamara.co/"

const val AUTH_TOKEN ="eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhY2NvdW50SWQiOiI0NWQwMzAzOC1kM2I2LTQ1ODctYWY2Ny1hNDNlY2FlYjFiZDMiLCJ0eXBlIjoibWVyY2hhbnQiLCJzYWx0IjoiOTIwNDFjZDVlOTJlNDQ1MDg1ZTQ2NzgyZWFhYTY3NjkiLCJpYXQiOjE1ODc3NjM2MzksImlzcyI6IlRhbWFyYSJ9.jPwKTNMwGFZ-gueED91piwXk8EA2OcqlYDlDZwg67Oo7ec7E-7Qao89BTJc-gfdyQ8542JqdAuGlg3PlC7-he5fhCUyGAbnEVy9r2pO8ROO9sINXCkdi-CotLTnO_ENWd6AMCnNlNx7hZ1wsVCVSQS6RNZjOm8iEdHxyCRU13pLWxzsSR2WGsqplVprgeMxSdMKjLatdGEk7ipp4mVUTPba4rSYvselIOfpVvX8XDN1y_aYPIIVlCtpLeJ7MRrkyH0LBZ-4b2Ac1iDAjg51K_qvYng4xNiNEyflmy9kmtkNIrPMP1IlAR0ZEx2epAlhKU7TgIiVC1bs0hjMe6M2QMQ"

const val NOTIFICATION_WEB_HOOK_URL = "https://your.site/pushnotification"

const val PUBLISH_KEY = "e3eo6279-90c2-4239-b4e2-2c91bpodisjd"

const val NOTIFICATION_TOKEN = "dede4a2-5f57-475e-a384-9o d9b8a803487"

val isSandbox: sandbox/production account
if sandbox account: isSandbox = true
if production account: isSandbox = false
```

## Initializes SDK
Initialize TamaraPayment before using it:
```
TamaraPayment.initialize(AUTH_TOKEN, API_URL, NOTIFICATION_WEB_HOOK_URL, PUBLISH_KEY, NOTIFICATION_TOKEN, isSandbox)
```

## Create and pay order
Before adding order's information, create Order by call this method with referenceOrderId and description.
RefId is your unique id of your order.
```
TamaraPayment.createOrder(referenceOrderId, description)
```

### These informations are mandatory:

Set customer's information:
```
TamaraPayment.setCustomerInfo(firstName, lastName,
            phoneNumber, email, isFirstOrder)
```

Set payment type (optional: default: PAY_BY_INSTALMENTS):
```
TamaraPayment.setPaymentType(paymentType)
```

Add Item with its price, tax and discount:
```
TamaraPayment.addItem(name, referenceId ,sku, type, unitPrice,
                    taxAmount ,discountAmount, quantity)
```

Set shipping address and billing address:
```
TamaraPayment.setShippingAddress(firstName,lastName, phone,
                    addressLine1, addressLine2, country, region, city)
TamaraPayment.setBillingAddress(firstName,lastName, phone,
                    addressLine1, addressLine2, country, region, city)
```

Set shipping fee:
```
TamaraPayment.setShippingAmount(shippingFee)
```

Set discount (optional):
```
TamaraPayment.setDiscount(discount, name)
```

Processes to Tamara payment page using:
```
TamaraPayment.startPayment(activity)
```

## Order detail
Get order detail
param mandatory: orderId
```
TamaraPayment.getOrderDetail(this, orderId)
```
Example:
Response:
```
OrderDetail(billingAddress=AddressDetail(firstName=Mona, lastName=Lisa, phoneNumber=966502223337, line1=3764 Al Urubah Rd, line2=, countryCode=SA, region=As Sulimaniyah, city=Riyadh), consumer=ConsumerDetail(firstName=Mona, lastName=Lisa, phoneNumber=966502223333, email=user@example.com, isFirstOrder=true, dateOfBirth=2023-04-14, nationalId=), countryCode=SA, description=Description, status=expired, discount=null, items=[ItemDetail(name=Lego City 8601, referenceId=123456_item, sku=SA-12436, unitPrice=AmountDetail(amount=50.0, currency=SAR), quantity=1, discountAmount=AmountDetail(amount=25.0, currency=SAR), taxAmount=AmountDetail(amount=10.0, currency=SAR), totalAmount=AmountDetail(amount=35.0, currency=SAR), imageUrl=, itemUrl=), ItemDetail(name=Batman, referenceId=123457_item, sku=SA-12437, unitPrice=AmountDetail(amount=75.0, currency=SAR), quantity=1, discountAmount=AmountDetail(amount=25.0, currency=SAR), taxAmount=AmountDetail(amount=10.0, currency=SAR), totalAmount=AmountDetail(amount=60.0, currency=SAR), imageUrl=, itemUrl=), ItemDetail(name=Spider man, referenceId=123458_item, sku=SA-12438, unitPrice=AmountDetail(amount=25.0, currency=SAR), quantity=1, discountAmount=AmountDetail(amount=5.0, currency=SAR), taxAmount=AmountDetail(amount=10.0, currency=SAR), totalAmount=AmountDetail(amount=30.0, currency=SAR), imageUrl=, itemUrl=), ItemDetail(name=Thor, referenceId=123459_item, sku=SA-12439, unitPrice=AmountDetail(amount=200.0, currency=SAR), quantity=1, discountAmount=AmountDetail(amount=25.0, currency=SAR), taxAmount=AmountDetail(amount=10.0, currency=SAR), totalAmount=AmountDetail(amount=185.0, currency=SAR), imageUrl=, itemUrl=), ItemDetail(name=Iron man, referenceId=123460_item, sku=SA-12460, unitPrice=AmountDetail(amount=500.0, currency=SAR), quantity=1, discountAmount=AmountDetail(amount=0.0, currency=SAR), taxAmount=AmountDetail(amount=10.0, currency=SAR), totalAmount=AmountDetail(amount=510.0, currency=SAR), imageUrl=, itemUrl=)], locale=en-US, merchantUrl=MerchantUrlDetail(notification=tamara://checkout/notification, cancel=tamara://checkout/cancel, failure=tamara://checkout/failure, success=tamara://checkout/success), orderReferenceId=123, orderId=9af217f1-9e4a-400e-9e06-2b7f4f40687e, orderNumber=123, paymentType=PAY_BY_INSTALMENTS, shippingAddress=AddressDetail(firstName=Mona, lastName=Lisa, phoneNumber=966502223337, line1=3764 Al Urubah Rd, line2=, countryCode=SA, region=As Sulimaniyah, city=Riyadh), shippingAmount=AmountDetail(amount=20.0, currency=SAR), taxAmount=AmountDetail(amount=50.0, currency=SAR), totalAmount=AmountDetail(amount=840.0, currency=SAR), capturedAmount=AmountDetail(amount=0.0, currency=SAR), refundedAmount=AmountDetail(amount=0.0, currency=SAR), canceledAmount=AmountDetail(amount=0.0, currency=SAR), paidAmount=AmountDetail(amount=0.0, currency=SAR), walletPrepaidAmount=AmountDetail(amount=0.0, currency=SAR), platform=Android, settlementStatus=, storeCode=, createdAt=2023-04-14T16:49:59+00:00, isMobile=true, processing=false, instalments=3)
```

## Authorise order
Merchant will be required to call the Authorize API the order after receiving the notification from Tamara about the order being 'Approved'.
Authorise order by call this method with orderId.
param mandatory: orderId
```
TamaraPayment.authoriseOrder(this, orderId)
```
Example:
Response:
```
AuthoriseOrder(orderId=9af217f1-9e4a-400e-9e06-2b7f4f40687e, status=authorised, orderExpiryTime="2019-08-24T14:15:22Z", paymentType="PAY_BY_INSTALMENTS",
autoCaptured=false)
```

## Cancel order
Merchant will be required to call the Cancel in case the order has been cancelled either by the customer or merchant before the order shipment.
Note: Need call authorise order method before call cancel order
Cancel order reference by call this method with orderId and jsonData.

param mandatory: orderId
jsonData: use library convert class CancelOrder to json (Gson)
```
class CancelOrder (
     @SerializedName("discount_amount") var discountAmount: Amount? = null,
    var items: ArrayList<'Item'> = arrayListOf(),
    @SerializedName("shipping_amount") var shippingAmount: Amount? = null,
    @SerializedName("tax_amount") var taxAmount: Amount? = null,
    @SerializedName("total_amount") var totalAmount: Amount? = null
)
```
class Amount(
    var amount: Double = 0.0, // 50.00
    var currency: String? = "" // SAR
)
class Item(
    var name: String = "", // Lego City 8601
    @SerializedName("reference_id") var referenceId: String = "", // 123456
    var sku: String = "", // SA-12436
    var type: String = "", // SA-12436
    @SerializedName("unit_price") var unitPrice: Amount? = null,
    var quantity: Int = 0, // 1
    @SerializedName("discount_amount") var discountAmount: Amount? = null,
    @SerializedName("tax_amount") var taxAmount: Amount? = null,
    @SerializedName("total_amount") var totalAmount:Amount? = null,
    @SerializedName("image_url") var imageUrl: String? = null,
    @SerializedName("item_url") var itemUrl: String? = null
)
```
TamaraPayment.cancelOrder(this, orderId, jsonData)
```
Example:
Response:
```
Response:
CancelOrderResponse(captureId=5ed77aa5-0c93-4f0d-8435-fb91f5939a82, orderId=9af217f1-9e4a-400e-9e06-2b7f4f40687e)
```

## Update order reference
In case Merchant creates the order after it is authorised (i.e. confirmation of successful payment) then the Update order should be used to update the Order ID.
Update order reference by call this method with orderId and orderReference.

param mandatory: orderId, orderReference
```
TamaraPayment.updateOrderReference(this, orderId, orderReference)
```
Example:
```
Response:
OrderReference(message=Order reference id was updated successfully)
```

## Capture a payment
Merchants can partially or fully capture an order after the order is shipped to the customer depending on partial shipment or full shipment.
Note: Need call authorise order method before call capture a payment
Cancel order reference by call this method with orderId and jsonData.

param mandatory: orderId
jsonData: use library convert class CancelOrder to json (Gson)

```
class CapturePaymentRequest(
    @SerializedName("order_id") var orderId: String? = null,
    @SerializedName("billing_address") var billingAddress: Address? = null,
    @SerializedName("discount_amount") var discountAmount: Amount? = null,
    var items: ArrayList<Item> = arrayListOf(),
    @SerializedName("shipping_address") var shippingAddress: Address? = null,
    @SerializedName("shipping_amount") var shippingAmount: Amount? = null,
    @SerializedName("tax_amount") var taxAmount: Amount? = null,
    @SerializedName("total_amount") var totalAmount: Amount? = null,
    @SerializedName("shipping_info") var shippingInfo: ShippingInfo? = null
)
```
class Address(
    @SerializedName("first_name") var firstName: String = "",
    @SerializedName("last_name") var lastName: String = "",
    @SerializedName("phone_number") var phoneNumber: String = "",
    var line1: String = "",
    var line2: String = "",
    @SerializedName("country_code") var countryCode: String = "",
    var region: String = "",
    var city: String = ""
)

class ShippingInfo (
    @SerializedName("shipped_at") var shippedAt: String? = "",
    @SerializedName("shipping_company") var shippingCompany: String? = "",
    @SerializedName("tracking_number") var trackingNumber: String? = "",
    @SerializedName("tracking_url") var trackingUrl: String? = ""
)
Amount(
    var amount: Double = 0.0, // 50.00
    var currency: String? = "" // SAR
)
```
TamaraPayment.getCapturePayment(this, jsonData)
```
Example:
Response:
```
CapturePayment(captureId=5ed77aa5-0c93-4f0d-8435-fb91f5939a82, orderId=9af217f1-9e4a-400e-9e06-2b7f4f40687e)
```

## Refunds
Cancel order reference by call this method with orderId and jsonData.
Note: Need call authorise order method before call Refunds

param mandatory: orderId
jsonData: use library convert class CancelOrder to json (Gson)
```
class PaymentRefund (
    @SerializedName("comment") var comment: String? = null,
    @SerializedName("total_amount") var totalAmount: EAmount? = null
)
```
class Amount(
    var amount: Double = 0.0, // 50.00
    var currency: String? = "" // SAR
)
```
TamaraPayment.refunds(this, orderId, jsonData)
```
Example:
Response:
```
RefundsResponse(captureId=5ed77aa5-0c93-4f0d-8435-fb91f5939a82, orderId=9af217f1-9e4a-400e-9e06-2b7f4f40687e,refundId=0a17d40a-6180-4f4b-ba7c-498ae79e30dc,comment=Refund)
```

## Render widget cart page
Render widget cart page reference by call this method with language, country, publicKey, amount.
param mandatory: language, country, publicKey, amount

```
TamaraPayment.renderWidgetCartPage(this, language, country, publicKey, amount)
```
Example:
Response:
```
CartPage(script=<script>
        window.tamaraWidgetConfig = {
            lang: "en",
            country: "SA",
            publicKey: "d36c6279-90c2-4239-b4e2-2c91bfda0fe4"
        }
      </script>
      <script defer type="text/javascript" src="https://cdn-sandbox.tamara.co/widget-v2/tamara-widget.js"></script>

      
      <tamara-widget type="tamara-summary" amount="250.0" inline-type="3"></tamara-widget>, url=https://cdn-sandbox.tamara.co/widget-v2/tamara-widget.html?lang=en&public_key=d36c6279-90c2-4239-b4e2-2c91bfda0fe4&country=SA&amount=250.0&inline_type=3)
```
## Render widget product
Render widget product reference by call this method with language, country, publicKey, amount.
param mandatory: language, country, publicKey, amount

```
TamaraPayment.renderWidgetProduct(this, language, country, publicKey, amount)
```
Example:
Response:
```
Product(script=<script>
        window.tamaraWidgetConfig = {
            lang: "en",
            country: "SA",
            publicKey: "d36c6279-90c2-4239-b4e2-2c91bfda0fe4"
        }
      </script>
      <script defer type="text/javascript" src="https://cdn-sandbox.tamara.co/widget-v2/tamara-widget.js"></script>

      
      <tamara-widget type="tamara-summary" amount="250.0" inline-type="3"></tamara-widget>, url=https://cdn-sandbox.tamara.co/widget-v2/tamara-widget.html?lang=en&public_key=d36c6279-90c2-4239-b4e2-2c91bfda0fe4&country=SA&amount=250.0&inline_type=2)
```
## Get result 

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            TamaraPayment.REQUEST_TAMARA_PAYMENT -> {
                if(TamaraPaymentHelper.shouldHandleActivityResult(requestCode, resultCode, data)){
                    var result = TamaraPaymentHelper.getData(data!!)
                    when(result?.status){
                        PaymentResult.STATUS_CANCEL ->{
                            Toast.makeText(requireActivity(), "Payment canceled", Toast.LENGTH_LONG).show()
                        }
                        PaymentResult.STATUS_FAILURE -> {
                            Toast.makeText(requireActivity(), result.getMessage() ?: "Payment error", Toast.LENGTH_LONG).show()
                        }
                        PaymentResult.STATUS_SUCCESS -> {
                            Toast.makeText(requireActivity(), "Payment success", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
            TamaraPayment.REQUEST_TAMARA_ORDER_DETAIL -> {
                if(TamaraInformationHelper.shouldHandleActivityResult(requestCode, resultCode, data)){
                    var result = TamaraInformationHelper.getData(data!!)
                    when(result?.status){
                        InformationResult.STATUS_FAILURE -> {
                            Toast.makeText(requireActivity(), result.getMessage() ?: "Order Detail error", Toast.LENGTH_LONG).show()
                        }
                        InformationResult.STATUS_SUCCESS -> {
                            val orderDetail = TamaraInformationHelper.getDataOrderDetail(data)
                            DialogFactory.dialogOrder(
                                    requireActivity(),
                                    orderDetail.toString()
                                )
//                            Toast.makeText(requireActivity(), "Order Detail success "+orderDetail, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
            TamaraPayment.REQUEST_TAMARA_CAPTURE_PAYMENT-> {
                if(TamaraInformationHelper.shouldHandleActivityResult(requestCode, resultCode, data)){
                    var result = TamaraInformationHelper.getData(data!!)
                    when(result?.status){
                        InformationResult.STATUS_FAILURE -> {
                            DialogFactory.dialogOrder(
                                requireActivity(), result.getMessage() ?: "Capture Payment error"
                            )
//                            Toast.makeText(requireActivity(), result.getMessage() ?: "Capture Payment error", Toast.LENGTH_LONG).show()
                        }
                        InformationResult.STATUS_SUCCESS -> {
                            val capturePayment = TamaraInformationHelper.getDataCapturePayment(data)
                            DialogFactory.dialogOrder(
                                requireActivity(), " "+capturePayment
                            )
//                            Toast.makeText(requireActivity(), "Capture Payment "+capturePayment, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
            TamaraPayment.REQUEST_TAMARA_REFUNDS-> {
                if(TamaraInformationHelper.shouldHandleActivityResult(requestCode, resultCode, data)){
                    var result = TamaraInformationHelper.getData(data!!)
                    when(result?.status){
                        InformationResult.STATUS_FAILURE -> {
                            DialogFactory.dialogOrder(
                                requireActivity(), result.getMessage() ?: "refund Payment error"
                            )
//                            Toast.makeText(requireActivity(), result.getMessage() ?: "Capture Payment error", Toast.LENGTH_LONG).show()
                        }
                        InformationResult.STATUS_SUCCESS -> {
                            val refunds = TamaraInformationHelper.getDataRefunds(data)
                            DialogFactory.dialogOrder(
                                requireActivity(), "Refunds "+refunds
                            )
//                            Toast.makeText(requireActivity(), "Capture Payment "+refunds, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
            TamaraPayment.REQUEST_TAMARA_REFERENCE-> {
                if(TamaraInformationHelper.shouldHandleActivityResult(requestCode, resultCode, data)){
                    var result = TamaraInformationHelper.getData(data!!)
                    when(result?.status){
                        InformationResult.STATUS_FAILURE -> {
                            Toast.makeText(requireActivity(), result.getMessage() ?: "Order Reference error", Toast.LENGTH_LONG).show()
                        }
                        InformationResult.STATUS_SUCCESS -> {
                            val refunds = TamaraInformationHelper.getOrderReference(data)
                            DialogFactory.dialogOrder(
                                requireActivity(),
                                " "+refunds
                            )
//                            Toast.makeText(requireActivity(), "Order Reference "+refunds, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }

            TamaraPayment.REQUEST_TAMARA_CART_PAGE-> {
                if(TamaraInformationHelper.shouldHandleActivityResult(requestCode, resultCode, data)){
                    var result = TamaraInformationHelper.getData(data!!)
                    when(result?.status){
                        InformationResult.STATUS_FAILURE -> {
                            Toast.makeText(requireActivity(), result.getMessage() ?: "Cart page error", Toast.LENGTH_LONG).show()
                        }
                        InformationResult.STATUS_SUCCESS -> {
                            val cartPage = TamaraInformationHelper.getCartPage(data)
                            cartW.settings.javaScriptEnabled = true
                            cartW.loadDataWithBaseURL("file:///android_asset/",cartPage?.script,"text/html","utf-8",null)
//                            cartW.loadData(cartPage?.script, "text/html", "utf-8");
                            urlScriptCart = cartPage?.script.toString()
                            urlCart = cartPage?.url.toString()
                            DialogFactory.dialogOrder(
                                requireActivity(),
                                " "+cartPage
                            )
//                            Toast.makeText(requireActivity(), "Cart Page "+cartPage, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }

            TamaraPayment.REQUEST_TAMARA_PRODUCT-> {
                if(TamaraInformationHelper.shouldHandleActivityResult(requestCode, resultCode, data)){
                    var result = TamaraInformationHelper.getData(data!!)
                    when(result?.status){
                        InformationResult.STATUS_FAILURE -> {
                            Toast.makeText(requireActivity(), result.getMessage() ?: "Product error", Toast.LENGTH_LONG).show()
                        }
                        InformationResult.STATUS_SUCCESS -> {
                            val product = TamaraInformationHelper.getProduct(data)
                            productW.settings.defaultTextEncodingName = "utf-8"
                            productW.settings.javaScriptEnabled = true
                            productW.loadDataWithBaseURL("file:///android_asset/",product?.script,"text/html","utf-8",null)
//                            productW.loadData(product?.script, "text/html", "utf-8");
                            urlScriptProduct = product?.script.toString()
                            urlProduct = product?.url.toString()
                            DialogFactory.dialogOrder(
                                requireActivity(),
                                "Product "+product
                            )
//                            Toast.makeText(requireActivity(), "Product "+cartPage, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }

            TamaraPayment.REQUEST_TAMARA_CANCEL-> {
                if(TamaraInformationHelper.shouldHandleActivityResult(requestCode, resultCode, data)){
                    var result = TamaraInformationHelper.getData(data!!)
                    when(result?.status){
                        InformationResult.STATUS_FAILURE -> {
                            Toast.makeText(requireActivity(), result.getMessage() ?: "Cancel order error", Toast.LENGTH_LONG).show()
                        }
                        InformationResult.STATUS_SUCCESS -> {
                            val cancelOrder = TamaraInformationHelper.getCancelOrder(data)
                            DialogFactory.dialogOrder(
                                requireActivity(), " "+cancelOrder
                            )
//                            Toast.makeText(requireActivity(), "Cancel order "+cancelOrder, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }

            TamaraPayment.REQUEST_TAMARA_AUTHORISE-> {
                if(TamaraInformationHelper.shouldHandleActivityResult(requestCode, resultCode, data)){
                    var result = TamaraInformationHelper.getData(data!!)
                    when(result?.status){
                        InformationResult.STATUS_FAILURE -> {
                            Toast.makeText(requireActivity(), result.getMessage() ?: "Authorise order error", Toast.LENGTH_LONG).show()
                        }
                        InformationResult.STATUS_SUCCESS -> {
                            val authorise = TamaraInformationHelper.getAuthoriseOrder(data)
                            DialogFactory.dialogOrder(
                                requireActivity(),
                                " "+authorise
                            )
//                            Toast.makeText(requireActivity(), "Order Reference "+refunds, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }
