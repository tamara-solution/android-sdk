package co.tamara.example.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import co.tamara.example.R
import co.tamara.example.model.EAmount
import co.tamara.example.model.EItem
import co.tamara.example.viewmodel.ViewModelFactory
import co.tamara.sdk.TamaraPayment
import kotlinx.android.synthetic.main.fragment_checkout.*

/**
 * A simple [Fragment] subclass.
 */
class  CheckoutFragment : Fragment() {
    private var items: List<EItem>? = null
    private lateinit var itemViewModel: OrderViewModel
    private val shippingAmount = EAmount(20.0, "SAR")
    private val discountAmount = EAmount(100.0, "SAR")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_checkout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        itemViewModel = ViewModelProvider(requireActivity(), ViewModelFactory()).get(OrderViewModel::class.java)
        itemViewModel.items.observe(viewLifecycleOwner, Observer { items ->
            this.items = items
            updateUI()
        })
        shippingFeeTxt.text = shippingAmount.getFormattedAmount()
        discountTxt.text = getString(R.string.neg_value,discountAmount.getFormattedAmount())
        addressBtn.setOnClickListener {
            TamaraPayment.setShippingAmount(shippingAmount.amount)
            if(discountCheck.isChecked){
                TamaraPayment.setDiscount(discountAmount.amount, discountCheck.text.toString())
            }
            findNavController(this).navigate(R.id.shippingAddressFragment)
        }
        discountCheck.setOnCheckedChangeListener { _, _ ->
            updateUI()
        }
    }

    private fun updateUI() {
        var total = 0.0
        var totalPrice = 0.0
        val ship = shippingAmount.amount
        val discount = if(discountCheck.isChecked) discountAmount.amount else 0.0
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
        totalItemPriceTxt.text = EAmount(totalPrice, currency).getFormattedAmount()
        taxTxt.text = EAmount(tax, currency).getFormattedAmount()
        totalTxt.text = EAmount(total + ship - discount, currency).getFormattedAmount()
        discountTxt.visibility = if(discountCheck.isChecked) View.VISIBLE else View.GONE
    }

}
