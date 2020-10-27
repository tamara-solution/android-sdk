## Tarama Pay SDK's requirements
To intergrate Tamara Pay SDK to your app, you need AUTH_TOKEN, API_URL and your server NOTIFICATION_WEBHOOK_URL:
```
const val API_URL = "https://api-sandbox.tamara.co/"

const val AUTH_TOKEN ="eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhY2NvdW50SWQiOiI0NWQwMzAzOC1kM2I2LTQ1ODctYWY2Ny1hNDNlY2FlYjFiZDMiLCJ0eXBlIjoibWVyY2hhbnQiLCJzYWx0IjoiOTIwNDFjZDVlOTJlNDQ1MDg1ZTQ2NzgyZWFhYTY3NjkiLCJpYXQiOjE1ODc3NjM2MzksImlzcyI6IlRhbWFyYSJ9.jPwKTNMwGFZ-gueED91piwXk8EA2OcqlYDlDZwg67Oo7ec7E-7Qao89BTJc-gfdyQ8542JqdAuGlg3PlC7-he5fhCUyGAbnEVy9r2pO8ROO9sINXCkdi-CotLTnO_ENWd6AMCnNlNx7hZ1wsVCVSQS6RNZjOm8iEdHxyCRU13pLWxzsSR2WGsqplVprgeMxSdMKjLatdGEk7ipp4mVUTPba4rSYvselIOfpVvX8XDN1y_aYPIIVlCtpLeJ7MRrkyH0LBZ-4b2Ac1iDAjg51K_qvYng4xNiNEyflmy9kmtkNIrPMP1IlAR0ZEx2epAlhKU7TgIiVC1bs0hjMe6M2QMQ"

const val NOTIFICATION_WEB_HOOK_URL = "https://your.site/pushnotification"
```

## Initializes SDK
Initialize TamaraPayment before using it:
```
TamaraPayment.initialize(AUTH_TOKEN, API_URL, NOTIFICATION_WEB_HOOK_URL)
```

## Create and pay order
Before adding order's information, create Order by call this method with refId and description.
RefId is your unique id of your order.
```
TamaraPayment.createOrder(refId, description)
```

### These informations are mandatory:

Set customer's information:
```
TamaraPayment.setCustomerInfo(firstName, lastName,
            phoneNumber, email, isFirstOrder)
```

Add Item with its price, tax and discount:
```
TamaraPayment.addItem(name, referenceId ,sku, unitPrice,
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
