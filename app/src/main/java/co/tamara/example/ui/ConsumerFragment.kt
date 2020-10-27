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
import co.tamara.example.viewmodel.ViewModelFactory
import co.tamara.sdk.TamaraPayment
import kotlinx.android.synthetic.main.fragment_consumer.*
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class ConsumerFragment : Fragment() {

    private lateinit var consumerViewModel: ConsumerViewModel

    override fun onStart() {
        super.onStart()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_consumer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        TamaraPayment.createOrder(createTransactionID(), "Description")
    }

    @Throws(Exception::class)
    fun createTransactionID(): String {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        consumerViewModel = ViewModelProvider(this, ViewModelFactory()).get(ConsumerViewModel::class.java)
        consumerViewModel.consumer.observe(viewLifecycleOwner, Observer {consumer->
            firstNameTxt.text = consumer.firstName
            lastNameTxt.text = consumer.lastName
            emailTxt.text = consumer.email
            phoneTxt.text = consumer.phoneNumber
            firstOrderCheck.isChecked = consumer?.isFirstOrder ?: false
        })
        shopBtn.setOnClickListener {
            TamaraPayment.setCustomerInfo(firstNameTxt.text.toString(), lastNameTxt.text.toString(),phoneTxt.text.toString(),
                emailTxt.text.toString(),firstOrderCheck.isChecked)
            findNavController(this).navigate(R.id.shopFragment)
        }
    }


}
