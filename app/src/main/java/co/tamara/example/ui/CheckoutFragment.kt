package co.tamara.example.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import co.tamara.example.R
import co.tamara.example.databinding.FragmentCheckoutBinding
import co.tamara.example.model.EAmount
import co.tamara.example.model.EItem
import co.tamara.example.viewmodel.ViewModelFactory
import co.tamara.sdk.TamaraPayment

/**
 * A simple [Fragment] subclass.
 */
class  CheckoutFragment : Fragment() {
    private var _binding: FragmentCheckoutBinding? = null
    private val binding get() = _binding!!
    private var items: List<EItem>? = null
    private lateinit var itemViewModel: OrderViewModel
    private val shippingAmount = EAmount(20.0, "SAR")
    private val discountAmount = EAmount(100.0, "SAR")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCheckoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        itemViewModel = ViewModelProvider(requireActivity(), ViewModelFactory()).get(OrderViewModel::class.java)
        itemViewModel.items.observe(viewLifecycleOwner, Observer { items ->
            this.items = items
            updateUI()
        })
        binding.shippingFeeTxt.text = shippingAmount.getFormattedAmount()
        binding.discountTxt.text = getString(R.string.neg_value,discountAmount.getFormattedAmount())
        binding.addressBtn.setOnClickListener {
            TamaraPayment.setShippingAmount(shippingAmount.amount)
            if(binding.discountCheck.isChecked){
                TamaraPayment.setDiscount(discountAmount.amount, binding.discountCheck.text.toString())
            }
            findNavController(this).navigate(R.id.shippingAddressFragment)
        }
        binding.discountCheck.setOnCheckedChangeListener { _, _ ->
            updateUI()
        }
    }

    private fun updateUI() {
        var total = 0.0
        var totalPrice = 0.0
        val ship = shippingAmount.amount
        val discount = if(binding.discountCheck.isChecked) discountAmount.amount else 0.0
        var tax = 0.0
        var count = 0
        var currency = ""
        this.items?.forEach {
            totalPrice += (it.unitPrice!!.amount - (it.discountAmount?.amount?:0.0)) * it.quantity
            total += (it.unitPrice!!.amount - (it.discountAmount?.amount?:0.0) + it.taxAmount!!.amount) * it.quantity
            tax += it.taxAmount!!.amount * it.quantity
            count += it.quantity
            currency = it.unitPrice!!.currency
        }
        binding.totalItemPriceTxt.text = EAmount(totalPrice, currency).getFormattedAmount()
        binding.taxTxt.text = EAmount(tax, currency).getFormattedAmount()
        binding.totalTxt.text = EAmount(total + ship - discount, currency).getFormattedAmount()
        binding.discountTxt.visibility = if(binding.discountCheck.isChecked) View.VISIBLE else View.GONE
    }

}
