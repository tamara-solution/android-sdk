package co.tamara.example.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.tamara.example.R
import co.tamara.example.model.EAddress
import co.tamara.example.viewmodel.ViewModelFactory
import co.tamara.sdk.TamaraPayment
import kotlinx.android.synthetic.main.fragment_shipping_address.*

/**
 * A simple [Fragment] subclass.
 */
class ShippingAddressFragment : Fragment() {

    private var addresses: List<EAddress>? = null
    private lateinit var addressViewModel: AddressViewModel
    private var step = STEP_SHIPPING_ADDRESS

    companion object {
        const val STEP_SHIPPING_ADDRESS = 1
        const val STEP_BILLING_ADDRESS = 2
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shipping_address, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addressViewModel = ViewModelProvider(this, ViewModelFactory()).get(AddressViewModel::class.java)
        addressViewModel.addresses.observe(viewLifecycleOwner, Observer {
            addresses = it
            fillAddress()
        })
        useForBillingCheck.setOnCheckedChangeListener { _, checked ->
            if(checked){
                actionBtn.text = getString(R.string.pay_via_tamara)
            } else {
                actionBtn.text = getString(R.string.select_billing_address)
            }
        }
        actionBtn.setOnClickListener {
            if(step == STEP_SHIPPING_ADDRESS){
                TamaraPayment.setShippingAddress(firstNameEdit.text.toString(),lastNameEdit.text.toString(),
                    phoneEdit.text.toString(), address1Edit.text.toString(), address2Edit.text.toString(),
                    countryEdit.text.toString(), regionEdit.text.toString(), cityEdit.text.toString())
                if(useForBillingCheck.isChecked){
                    TamaraPayment.setBillingAddress(firstNameEdit.text.toString(),lastNameEdit.text.toString(),
                        phoneEdit.text.toString(), address1Edit.text.toString(), address2Edit.text.toString(),
                        countryEdit.text.toString(), regionEdit.text.toString(), cityEdit.text.toString())
                    processPayment()
                } else {
                    if (addresses != null && addresses!!.size > 1) {
                        updateUI(addresses!![1])
                    }
                    useForBillingCheck.visibility = View.GONE
                    actionBtn.text = getString(R.string.pay_via_tamara)
                    step = STEP_BILLING_ADDRESS
                }
            } else {
                TamaraPayment.setBillingAddress(firstNameEdit.text.toString(),lastNameEdit.text.toString(),
                    phoneEdit.text.toString(), address1Edit.text.toString(), address2Edit.text.toString(),
                    countryEdit.text.toString(), regionEdit.text.toString(), cityEdit.text.toString())
                processPayment()
            }
        }
    }

    private fun processPayment() {
        TamaraPayment.startPayment(requireActivity())
    }

    private fun fillAddress() {
        if(!addresses.isNullOrEmpty()) {
            if (useForBillingCheck.visibility == View.VISIBLE) {
                updateUI(addresses!![0])
            } else if(addresses!!.size > 1){
                updateUI(addresses!![1])
            }
        }
    }

    private fun updateUI(eAddress: EAddress) {
        firstNameEdit.setText(eAddress.firstName)
        lastNameEdit.setText(eAddress.lastName)
        phoneEdit.setText(eAddress.phoneNumber)
        address1Edit.setText(eAddress.line1)
        address2Edit.setText(eAddress.line2)
        countryEdit.setText(eAddress.countryCode)
        regionEdit.setText(eAddress.region)
        cityEdit.setText(eAddress.city)
    }

}
