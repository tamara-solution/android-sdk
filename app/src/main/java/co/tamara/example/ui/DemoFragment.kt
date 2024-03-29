package co.tamara.example.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import co.tamara.example.R
import co.tamara.example.databinding.FragmentDemoBinding
import co.tamara.example.model.*
import co.tamara.example.model.CancelOrder
import co.tamara.example.model.Capture
import co.tamara.example.model.ShippingInfo
import co.tamara.example.utils.DialogFactory
import co.tamara.sdk.InformationResult
import co.tamara.sdk.PaymentResult
import co.tamara.sdk.TamaraInformationHelper
import co.tamara.sdk.TamaraPayment
import co.tamara.sdk.TamaraPaymentHelper
import com.google.gson.Gson

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DemoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DemoFragment : Fragment() {
    private var _binding: FragmentDemoBinding? = null
    private val binding get() = _binding!!
    var isCancelOrder: Boolean = false
    var isLoad: Boolean = false
    var urlCart: String = ""
    var urlScriptCart: String = ""
    var urlProduct: String = ""
    var urlScriptProduct: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDemoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.testFromFragmentBtn.setOnClickListener{
            TamaraPayment.startPayment(this, "https://checkout-staging.tamara.co/checkout/310fdb59-f447-44df-825b-19f467c6774b?locale=en-US",
                "tamara://success", "tamara://failure", "tamara://cancel")
        }

        binding.init.setOnClickListener {
            DialogFactory.dialogInit(
                requireActivity(),
                object : DialogFactory.DialogListener.Init {
                    override fun init(
                        authToken: String,
                        apiUrl: String,
                        noticationWeb: String,
                        publicKey: String,
                        notificationToken: String
                    ) {
                        TamaraPayment.initialize(authToken, apiUrl, noticationWeb, publicKey, notificationToken, true)
                    }
                })
        }

        binding.createOrderBtn.setOnClickListener {
            findNavController().navigate(R.id.action_demoFragment_to_consumerFragment)
        }

        binding.orderDetail.setOnClickListener {
            DialogFactory.dialogCancelOrder(
                requireActivity(),
                object : DialogFactory.DialogListener.CancelOrder {
                    override fun cancelOrder(orderId: String) {
                        TamaraPayment.getOrderDetail(this@DemoFragment, orderId)
//                        TamaraPayment.getOrderDetail(this, "9af217f1-9e4a-400e-9e06-2b7f4f40687e")
                    }
                })
        }

        binding.updateOrderReference.setOnClickListener {
            DialogFactory.dialogUpdateOrder(
                requireActivity(),
                object : DialogFactory.DialogListener.OrderReference {
                    override fun reference(orderId: String, orderReference: String) {
                        TamaraPayment.updateOrderReference(this@DemoFragment, orderId, orderReference)
                    }
                })
        }

        binding.authoriseOrder.setOnClickListener {
            DialogFactory.dialogCancelOrder(
                requireActivity(),
                object : DialogFactory.DialogListener.CancelOrder {
                    override fun cancelOrder(orderId: String) {
                        TamaraPayment.authoriseOrder(this@DemoFragment, orderId)
                    }
                })
        }

        binding.cancelOrder.setOnClickListener {
            DialogFactory.dialogCancelOrder(
                requireActivity(),
                object : DialogFactory.DialogListener.CancelOrder {
                    override fun cancelOrder(orderId: String) {
                        isCancelOrder = true
                        TamaraPayment.getOrderDetail(this@DemoFragment, orderId)
                    }
                })
        }

        binding.capture.setOnClickListener {
            DialogFactory.dialogCapture(
                requireActivity(),
                object : DialogFactory.DialogListener.Capture {
                    override fun capture(orderId: String, amount: String) {
                        val capture = Capture()
                        capture.orderId = orderId
                        capture.totalAmount = EAmount(amount = amount.toDouble(), currency = "SAR")
                        capture.shippingInfo = ShippingInfo()
                        TamaraPayment.getCapturePayment(this@DemoFragment, Gson().toJson(capture))
                    }
                })
        }

        binding.refunds.setOnClickListener {
            DialogFactory.dialogRefund(
                requireActivity(),
                object : DialogFactory.DialogListener.Refunds {
                    override fun refunds(orderId: String, comment: String, totalEAmount: EAmount) {
                        val paymentRefund = PaymentRefund(comment = comment, totalAmount = totalEAmount)
                        TamaraPayment.refunds(this@DemoFragment, orderId, Gson().toJson(paymentRefund))
                    }
                })
        }

        binding.paymentOptions.setOnClickListener {
            DialogFactory.dialogCheckPayment(requireActivity(),
                object : DialogFactory.DialogListener.CheckPayment {
                    override fun checkPayment(
                        country: String,
                        amount: Double,
                        currency: String,
                        phoneNumber: String?,
                        isVip: Boolean?
                    ) {
                        TamaraPayment.paymentOptions(this@DemoFragment, Gson().toJson(
                            PaymentOptions(country = country, orderValue = EAmount(amount, currency), phoneNumber = phoneNumber,
                                isVip = isVip)))
                    }
            })
        }

        binding.cartPage.setOnClickListener {
            DialogFactory.dialogWidget(
                requireActivity(),
                object : DialogFactory.DialogListener.Widget {
                    override fun widget(
                        language: String,
                        country: String,
                        publicKey: String,
                        amount: Double
                    ) {
                        TamaraPayment.renderWidgetCartPage(this@DemoFragment, language, country, publicKey, amount)
                    }
                })
        }

        binding.product.setOnClickListener {
            DialogFactory.dialogWidget(
                requireActivity(),
                object : DialogFactory.DialogListener.Widget {
                    override fun widget(
                        language: String,
                        country: String,
                        publicKey: String,
                        amount: Double
                    ) {
                        TamaraPayment.renderWidgetProduct(this@DemoFragment, language, country, publicKey, amount)
                    }
                })
        }

        binding.flCart.setOnClickListener {
            if (urlCart.isNotEmpty()) {
                isLoad = true
                val bundle = Bundle()
                bundle.putString("url", urlCart)
                findNavController().navigate(R.id.action_demoFragment_to_webviewFragment, bundle)
            }
        }

        binding.flProduct.setOnClickListener {
            if (urlProduct.isNotEmpty()) {
                isLoad = true
                val bundle = Bundle()
                bundle.putString("url", urlProduct)
                findNavController().navigate(R.id.action_demoFragment_to_webviewFragment, bundle)
            }
        }
    }

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
                            if (isCancelOrder) {
                                isCancelOrder = false
                            }
                            Toast.makeText(requireActivity(), result.getMessage() ?: "Order Detail error", Toast.LENGTH_LONG).show()
                        }
                        InformationResult.STATUS_SUCCESS -> {
                            val orderDetail = TamaraInformationHelper.getDataOrderDetail(data)
                            if (isCancelOrder) {
                                val cancelOrder = CancelOrder()
                                cancelOrder.totalAmount = EAmount(amount = orderDetail?.totalAmount?.amount!!, currency = orderDetail?.totalAmount?.currency!!)
                                TamaraPayment.cancelOrder(this@DemoFragment, orderDetail.orderId.toString(), Gson().toJson(cancelOrder))
                                isCancelOrder = false
                            } else {
                                DialogFactory.dialogOrder(
                                    requireActivity(),
                                    orderDetail.toString()
                                )
                            }
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
                            binding.cartW.settings.javaScriptEnabled = true
                            binding.cartW.loadDataWithBaseURL("file:///android_asset/",
                                cartPage?.script.toString(),"text/html","utf-8",null)
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
                            binding.productW.settings.defaultTextEncodingName = "utf-8"
                            binding.productW.settings.javaScriptEnabled = true
                            binding.productW.loadDataWithBaseURL("file:///android_asset/",
                                product?.script.toString(),"text/html","utf-8",null)
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

            TamaraPayment.REQUEST_TAMARA_PAYMENT_OPTIONS-> {
                if(TamaraInformationHelper.shouldHandleActivityResult(requestCode, resultCode, data)){
                    var result = TamaraInformationHelper.getData(data!!)
                    when(result?.status){
                        InformationResult.STATUS_FAILURE -> {
                            Toast.makeText(requireActivity(), result.getMessage() ?: "Check payment availability error", Toast.LENGTH_LONG).show()
                        }
                        InformationResult.STATUS_SUCCESS -> {
                            val paymentOptions = TamaraInformationHelper.getPaymentOptions(data)
                            DialogFactory.dialogOrder(
                                requireActivity(),
                                " "+paymentOptions
                            )
//                            Toast.makeText(requireActivity(), "Order Reference "+refunds, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (isLoad) {
            if (urlScriptCart.isNotEmpty()) {
                binding.cartW.clearCache(true)
                binding.cartW.clearHistory()
                binding.cartW.settings.javaScriptEnabled = true
                binding.cartW.loadDataWithBaseURL("file:///android_asset/",urlScriptCart,"text/html","utf-8",null)
            }
            if (urlScriptProduct.isNotEmpty()) {
                binding.productW.clearCache(true)
                binding.productW.clearHistory()
                binding.productW.settings.defaultTextEncodingName = "utf-8"
                binding.productW.settings.javaScriptEnabled = true
                binding.productW.loadDataWithBaseURL("file:///android_asset/",urlScriptProduct,"text/html","utf-8",null)
            }
            isLoad = false
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment DemoFragment.
         */
        @JvmStatic
        fun newInstance() =
            DemoFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}