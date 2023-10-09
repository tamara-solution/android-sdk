package co.tamara.example.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.tamara.example.R
import co.tamara.example.databinding.FragmentShippingAddressBinding
import co.tamara.example.model.EAddress
import co.tamara.example.viewmodel.ViewModelFactory
import co.tamara.sdk.TamaraPayment

/**
 * A simple [Fragment] subclass.
 */
class ShippingAddressFragment : Fragment() {
    private var _binding: FragmentShippingAddressBinding? = null
    private val binding get() = _binding!!
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
    ): View {
        _binding = FragmentShippingAddressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addressViewModel = ViewModelProvider(this, ViewModelFactory()).get(AddressViewModel::class.java)
        addressViewModel.addresses.observe(viewLifecycleOwner, Observer {
            addresses = it
            fillAddress()
        })
        binding.useForBillingCheck.setOnCheckedChangeListener { _, checked ->
            if(checked){
                binding.actionBtn.text = getString(R.string.pay_via_tamara)
            } else {
                binding.actionBtn.text = getString(R.string.select_billing_address)
            }
        }
        binding.actionBtn.setOnClickListener {
            if(step == STEP_SHIPPING_ADDRESS){
                TamaraPayment.setShippingAddress(binding.firstNameEdit.text.toString(),binding.lastNameEdit.text.toString(),
                    binding.phoneEdit.text.toString(), binding.address1Edit.text.toString(), binding.address2Edit.text.toString(),
                    binding.countryEdit.text.toString(), binding.regionEdit.text.toString(), binding.cityEdit.text.toString())
                if(binding.useForBillingCheck.isChecked){
                    TamaraPayment.setBillingAddress(binding.firstNameEdit.text.toString(),binding.lastNameEdit.text.toString(),
                        binding.phoneEdit.text.toString(), binding.address1Edit.text.toString(), binding.address2Edit.text.toString(),
                        binding.countryEdit.text.toString(), binding.regionEdit.text.toString(), binding.cityEdit.text.toString())
                    processPayment()
                } else {
                    if (addresses != null && addresses!!.size > 1) {
                        updateUI(addresses!![0])
                    }
                    binding.useForBillingCheck.visibility = View.GONE
                    binding.actionBtn.text = getString(R.string.pay_via_tamara)
                    step = STEP_BILLING_ADDRESS
                }
            } else {
                TamaraPayment.setBillingAddress(binding.firstNameEdit.text.toString(),binding.lastNameEdit.text.toString(),
                    binding.phoneEdit.text.toString(), binding.address1Edit.text.toString(), binding.address2Edit.text.toString(),
                    binding.countryEdit.text.toString(), binding.regionEdit.text.toString(), binding.cityEdit.text.toString())
                processPayment()
            }
        }
    }

    private fun processPayment() {
        TamaraPayment.startPayment(requireActivity())
    }

    private fun fillAddress() {
        if(!addresses.isNullOrEmpty()) {
            if (binding.useForBillingCheck.visibility == View.VISIBLE) {
                updateUI(addresses!![0])
            } else if(addresses!!.size > 1){
                updateUI(addresses!![1])
            }
        }
    }

    private fun updateUI(eAddress: EAddress) {
        binding.firstNameEdit.setText(eAddress.firstName)
        binding.lastNameEdit.setText(eAddress.lastName)
        binding.phoneEdit.setText(eAddress.phoneNumber)
        binding.address1Edit.setText(eAddress.line1)
        binding.address2Edit.setText(eAddress.line2)
        binding.countryEdit.setText(eAddress.countryCode)
        binding.regionEdit.setText(eAddress.region)
        binding.cityEdit.setText(eAddress.city)
    }

}
