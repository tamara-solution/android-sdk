package co.tamara.example.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import co.tamara.example.R
import co.tamara.example.viewmodel.ViewModelFactory
import co.tamara.sdk.PaymentResult
import co.tamara.sdk.TamaraPayment
import co.tamara.sdk.TamaraPaymentHelper
import kotlinx.android.synthetic.main.fragment_consumer.*
import java.util.*

class ConsumerFragment : Fragment() {

    private lateinit var consumerViewModel: ConsumerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_consumer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentBtn.setOnClickListener {
            TamaraPayment.startPayment(
                fragment = this,
                checkOutUrl = "https://checkout-staging.tamara.co/checkout/310fdb59-f447-44df-825b-19f467c6774b?locale=en-US",
                successCallbackUrl = "tamara://success",
                failureCallbackUrl = "tamara://failure",
                cancelCallbackUrl = "tamara://cancel"
            )
        }

        TamaraPayment.createOrder(createTransactionID(), "Description")
    }

    @Throws(Exception::class)
    fun createTransactionID(): String {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (TamaraPaymentHelper.shouldHandleActivityResult(requestCode, resultCode, data)) {
            val result = TamaraPaymentHelper.getData(data!!)
            when (result?.status) {
                PaymentResult.STATUS_CANCEL -> {
                    Toast.makeText(requireContext(), R.string.payment_cancel_from_fragment, Toast.LENGTH_LONG).show()
                }
                PaymentResult.STATUS_FAILURE -> {
                    Toast.makeText(requireContext(), result.getMessage() ?: getString(R.string.payment_error_from_fragment), Toast.LENGTH_LONG).show()
                }
                PaymentResult.STATUS_SUCCESS -> {
                    Toast.makeText(requireContext(), R.string.payment_success_from_fragment, Toast.LENGTH_LONG).show()

                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        consumerViewModel = ViewModelProvider(this, ViewModelFactory()).get(ConsumerViewModel::class.java)
        consumerViewModel.consumer.observe(viewLifecycleOwner, Observer { consumer ->
            firstNameTxt.text = consumer.firstName
            lastNameTxt.text = consumer.lastName
            emailTxt.text = consumer.email
            phoneTxt.text = consumer.phoneNumber
            firstOrderCheck.isChecked = consumer?.isFirstOrder ?: false
        })
        shopBtn.setOnClickListener {
            TamaraPayment.setCustomerInfo(
                firstNameTxt.text.toString(), lastNameTxt.text.toString(), phoneTxt.text.toString(),
                emailTxt.text.toString(), firstOrderCheck.isChecked
            )
            findNavController(this).navigate(R.id.shopFragment)
        }
    }


}
