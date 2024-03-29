package co.tamara.example.utils

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.provider.Settings
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import co.tamara.example.R
import co.tamara.example.model.Capture
import co.tamara.example.model.EAmount
import co.tamara.example.model.Refunds
import java.sql.Ref

object DialogFactory {
    interface DialogListener {
        interface OrderReference {
            fun reference(orderId: String, orderReference: String)
        }
        interface CancelOrder {
            fun cancelOrder(orderId: String)
        }

        interface Refunds {
            fun refunds(orderId: String, comment: String, totalEAmount: EAmount)
        }

        interface Widget {
            fun widget(language: String, country: String, publicKey: String,
                        amount: Double)
        }
        interface CheckPayment {
            fun checkPayment(country: String, amount: Double, currency: String,
                             phoneNumber: String?, isVip: Boolean?)
        }
        interface Init {
            fun init(authToken: String, apiUrl: String, noticationWeb: String,
                       publicKey: String, notificationToken: String)
        }

        interface Capture {
            fun capture(orderId: String, amount: String)
        }
    }

    fun dialogOrder(
        activity: Activity,
        message: String?) {
        val dialog = TranslucentDialog(activity)
        dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.dialog_order)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        val tvMessage = dialog.findViewById(R.id.txtContent) as TextView
        tvMessage.text = message
        val btSetup = dialog.findViewById(R.id.btSetup) as TextView
        btSetup.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    fun dialogUpdateOrder(
        activity: Activity,
        listener: DialogListener.OrderReference) {
        val dialog = TranslucentDialog(activity)
        dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.dialog_input_order)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        var txtOrderId = dialog.findViewById(R.id.txtOrderId) as EditText
        val txtOrderReference = dialog.findViewById(R.id.txtOrderReference) as EditText
        val btSetup = dialog.findViewById(R.id.btSetup) as TextView
        txtOrderId.setText("9af217f1-9e4a-400e-9e06-2b7f4f40687e")
        btSetup.setOnClickListener {
            if (txtOrderId.text.toString().isNotEmpty()) {
                listener.reference(txtOrderId.text.toString(), txtOrderReference.text.toString())
            }
            dialog.dismiss()
        }
        dialog.show()
    }

    fun dialogCancelOrder(
        activity: Activity,
        listener: DialogListener.CancelOrder) {
        val dialog = TranslucentDialog(activity)
        dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.dialog_input_order)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        var txtOrderId = dialog.findViewById(R.id.txtOrderId) as EditText
        val txtOrderReference = dialog.findViewById(R.id.txtOrderReference) as EditText
        txtOrderReference.visibility = View.GONE
        val btSetup = dialog.findViewById(R.id.btSetup) as TextView
        txtOrderId.setText("9af217f1-9e4a-400e-9e06-2b7f4f40687e")
        btSetup.setOnClickListener {
            if (txtOrderId.text.toString().isNotEmpty()) {
                listener.cancelOrder(txtOrderId.text.toString())
            }
            dialog.dismiss()
        }
        dialog.show()
    }

    fun dialogRefund(
        activity: Activity,
        listener: DialogListener.Refunds) {
        val dialog = TranslucentDialog(activity)
        dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.dialog_refunds)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        var txtOrderId = dialog.findViewById(R.id.txtOrderId) as EditText
        val amount = dialog.findViewById(R.id.amount) as EditText
        val comment = dialog.findViewById(R.id.comment) as EditText
        val btSetup = dialog.findViewById(R.id.btSetup) as TextView
        txtOrderId.setText("9af217f1-9e4a-400e-9e06-2b7f4f40687e")
        btSetup.setOnClickListener {
            if (comment.text.toString().isNotEmpty() && amount.text.toString().isNotEmpty()) {
                val amount = EAmount(amount = amount.text.toString().toDouble(), currency = "SAR")
                listener.refunds(txtOrderId.text.toString(), comment.text.toString(), amount)
                dialog.dismiss()
            } else {
                Toast.makeText(activity, "Please input", Toast.LENGTH_SHORT).show()
            }
        }
        dialog.show()
    }
    fun dialogCapture(
        activity: Activity,
        listener: DialogListener.Capture) {
        val dialog = TranslucentDialog(activity)
        dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.dialog_capture)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        var txtOrderId = dialog.findViewById(R.id.txtOrderId) as EditText
        val amount = dialog.findViewById(R.id.amount) as EditText
        val btSetup = dialog.findViewById(R.id.btSetup) as TextView
        txtOrderId.setText("9af217f1-9e4a-400e-9e06-2b7f4f40687e")
        btSetup.setOnClickListener {
            if (amount.text.toString().isNotEmpty()) {
                listener.capture(txtOrderId.text.toString(), amount.text.toString())
                dialog.dismiss()
            } else {
                Toast.makeText(activity, "Please input", Toast.LENGTH_SHORT).show()
            }
        }
        dialog.show()
    }
    fun dialogCheckPayment(
        activity: Activity,
        listener: DialogListener.CheckPayment) {
        val dialog = TranslucentDialog(activity)
        dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.dialog_check_payment_options)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        var txtCountry = dialog.findViewById(R.id.txtCountry) as EditText
        val txtAmount = dialog.findViewById(R.id.amount) as EditText
        val currency = dialog.findViewById(R.id.currency) as EditText
        val phoneNumber = dialog.findViewById(R.id.phoneNumber) as EditText
        val isVip = dialog.findViewById(R.id.isVip) as EditText
        val btSetup = dialog.findViewById(R.id.btSetup) as TextView
        btSetup.setOnClickListener {
            if (txtCountry.text.toString().isNotEmpty() && txtAmount.text.toString().isNotEmpty() &&
                currency.text.toString().isNotEmpty()) {
                listener.checkPayment(txtCountry.text.toString(), txtAmount.text.toString().toDouble(),
                    currency.text.toString(), phoneNumber.text.toString(),
                    isVip.text.toString().toBoolean())
            } else {
                Toast.makeText(activity, "Please input required", Toast.LENGTH_SHORT).show()

            }
            dialog.dismiss()
        }
        dialog.show()
    }
    fun dialogWidget(
        activity: Activity,
        listener: DialogListener.Widget) {
        val dialog = TranslucentDialog(activity)
        dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.dialog_widget)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        var txtLanguage = dialog.findViewById(R.id.txtLanguage) as EditText
        val txtCountry = dialog.findViewById(R.id.txtCountry) as EditText
        val publicKey = dialog.findViewById(R.id.publicKey) as EditText
        val amount = dialog.findViewById(R.id.amount) as EditText
        val btSetup = dialog.findViewById(R.id.btSetup) as TextView
        txtLanguage.setText("en")
        txtCountry.setText("SA")
        publicKey.setText("d36c6279-90c2-4239-b4e2-2c91bfda0fe4")
        amount.setText("250")
        btSetup.setOnClickListener {
            if (txtLanguage.text.toString().isNotEmpty() && txtCountry.text.toString().isNotEmpty() &&
                publicKey.text.toString().isNotEmpty() && amount.text.toString().isNotEmpty()) {
                listener.widget(txtLanguage.text.toString(), txtCountry.text.toString(),
                    publicKey.text.toString(), amount.text.toString().toDouble())
            }
            dialog.dismiss()
        }
        dialog.show()
    }

    fun dialogInit(
        activity: Activity,
        listener: DialogListener.Init) {
        val dialog = TranslucentDialog(activity)
        dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.dialog_init)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        var txtAUTH_TOKEN = dialog.findViewById(R.id.txtAUTH_TOKEN) as EditText
        val txtAPI_URL = dialog.findViewById(R.id.txtAPI_URL) as EditText
        val NOTIFICATION_WEB_HOOK_URL = dialog.findViewById(R.id.NOTIFICATION_WEB_HOOK_URL) as EditText
        val PUBLISH_KEY = dialog.findViewById(R.id.PUBLISH_KEY) as EditText
        val NOTIFICATION_TOKEN = dialog.findViewById(R.id.NOTIFICATION_TOKEN) as EditText
        val btSetup = dialog.findViewById(R.id.btSetup) as TextView
        txtAUTH_TOKEN.setText("eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhY2NvdW50SWQiOiJmY2ZiYzk3ZC0wYmIwLTRkYTItYmY3ZS02MjhlOTRkMzM0M2EiLCJ0eXBlIjoibWVyY2hhbnQiLCJzYWx0IjoiNzQxMmZkZjI1NGZiMWJhNmY5N2FmMmY1N2YxYzA1MDYiLCJpYXQiOjE2Nzc4MzIzNzQsImlzcyI6IlRhbWFyYSJ9.WVn2sf3LrW_YI3c2pNrbcOa--tRDAVm9p2GOBRdn7d671QIuqPvDgI9Gz7MNzBirUDnVLATCrL9uvMxDY_1OzXe3Sn1Gawckw-NE2EfL_Kjnl8GcNqwMcMvcin9XGxGRhbDDusgFCFzxaiEYae3DpA-pO0TpyQbEXl49ZLT4a9sEW75Taxc2ofZ-DJ_ciblImk1aJ6p9YhQowvzAVHz6yG-ZRfosxc96t8BK15bVTvTLnT9hzEnCqifqKO7vSu1e2mKEG8lC46pZHSr-ZpvfjSytrMX2QAZuXqxtlvbg3aRZeGiJ-SKVcbRdlId1wSRTZ5lntrw3pyrLS1dpxcfSOA")
        txtAPI_URL.setText("https://api-sandbox.tamara.co/")
        NOTIFICATION_WEB_HOOK_URL.setText("https://tamara.co/pushnotification")
        PUBLISH_KEY.setText("d36c6279-90c2-4239-b4e2-2c91bfda0fe4")
        NOTIFICATION_TOKEN.setText("aeae44a2-5f57-475e-a384-0e9b8a802326")
        btSetup.setOnClickListener {
            if (txtAUTH_TOKEN.text.toString().isNotEmpty() && txtAPI_URL.text.toString().isNotEmpty() &&
                NOTIFICATION_WEB_HOOK_URL.text.toString().isNotEmpty() && PUBLISH_KEY.text.toString().isNotEmpty()
                && NOTIFICATION_TOKEN.text.toString().isNotEmpty()) {
                listener.init(txtAUTH_TOKEN.text.toString(), txtAPI_URL.text.toString(),
                    NOTIFICATION_WEB_HOOK_URL.text.toString(), PUBLISH_KEY.text.toString(),
                    NOTIFICATION_TOKEN.text.toString())
            }
            dialog.dismiss()
        }
        dialog.show()
    }
}