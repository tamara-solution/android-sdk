# Android SDK
This project including 2 modules:
- SDK Module: [How to export aar and integrate aar to your project](SDKREADME.md)
- Example app: Simple online shoping app which uses Tamara SDK for payment

## Setup SDK
Copy aar file to your app libs folder: app-> libs->aars->tamara-sdk.aar

#### In app level build.gradle, add tamara-sdk.aar as libary:
```
implementation fileTree(dir: 'libs/aars', include: ['*.aar'])
```

Add library required by SDK
```
implementation 'com.google.dagger:dagger-android-support:2.52'
implementation "com.google.dagger:dagger:2.52"
implementation 'com.squareup.retrofit2:retrofit:2.11.0'
implementation 'com.squareup.retrofit2:converter-gson:2.11.0'
implementation 'androidx.lifecycle:lifecycle-extensions:2.2.0'
implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.6'
implementation 'androidx.legacy:legacy-support-v4:1.0.0'
implementation "androidx.navigation:navigation-fragment-ktx:2.8.3"
implementation 'androidx.navigation:navigation-ui-ktx:2.8.3'
implementation "com.squareup.okhttp3:logging-interceptor:4.12.0"
kapt 'com.google.dagger:dagger-compiler:2.52'
kapt 'com.google.dagger:dagger-android-processor:2.52'
```

Add this code at the begin of build.gradle file
```
apply plugin: 'com.android.library'
apply plugin: 'kotlin-android'
apply plugin: 'kotlin-parcelize'
apply plugin: 'kotlin-kapt'
apply plugin: 'maven-publish
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