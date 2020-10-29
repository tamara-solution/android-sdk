
## Setup SDK
Copy tamara-sdk.aar file to your app libs folder

#### In app level build.gradle, add tamara-sdk.aar as libary:
```
implementation (name:'tamara-sdk', ext:'aar')
```

Add library required by SDK
```
implementation 'com.google.dagger:dagger-android-support:2.21'
implementation "com.google.dagger:dagger:2.24"
implementation 'com.squareup.retrofit2:retrofit:2.4.0'
implementation 'com.squareup.retrofit2:converter-gson:2.4.0'
implementation "com.squareup.okhttp3:logging-interceptor:4.0.1"
implementation "androidx.navigation:navigation-fragment-ktx:2.2.2"
kapt 'com.google.dagger:dagger-compiler:2.21'
kapt 'com.google.dagger:dagger-android-processor:2.21'
```

Add this code at the begin of build.gradle file
```
apply plugin: 'kotlin-android'
apply plugin: 'kotlin-android-extensions'
apply plugin: 'kotlin-kapt'
```

#### In project level build.gradle:
```
allprojects {
    repositories {
        flatDir {
            dirs 'libs'
        }
    }
}
```
Sync project with gradle


# Pay without creating order

If you created order from your Back End, use checkout URL to process payment

```
TamaraPayment.startPayment(activity, checkoutURL)
```

If you want to create order first read this:
- [Create order and process payment](ORDERREADME.md)


## Get status of payment
In your activity, use TamaraPaymentHelper to handle data returned from the SDK
```
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if(TamaraPaymentHelper.shouldHandleActivityResult(requestCode, resultCode, data)){
        var result = TamaraPaymentHelper.getData(data!!)
        when(result?.status){
            PaymentResult.STATUS_CANCEL ->{
                //Payment has been cancelled
            }
            PaymentResult.STATUS_FAILURE -> {
                //Payment has occurred an error
            }
            PaymentResult.STATUS_SUCCESS -> {
                //Payment has been made successfully
            }
        }
    }
}
```

## Add Tamara Widget To your view
![alt Tamara Widget](tamara_widget.png)

```
<co.tamara.sdk.widget.PayWithTamaraWidget
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"/>
```