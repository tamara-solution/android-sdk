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
import co.tamara.example.databinding.FragmentConsumerBinding
import co.tamara.example.viewmodel.ViewModelFactory
import co.tamara.sdk.TamaraPayment
import java.util.*
/**
 * A simple [Fragment] subclass.
 */
class ConsumerFragment : Fragment() {
    private var _binding: FragmentConsumerBinding? = null
    private val binding get() = _binding!!
    private lateinit var consumerViewModel: ConsumerViewModel

    override fun onStart() {
        super.onStart()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConsumerBinding.inflate(inflater, container, false)
        return binding.root
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
            binding.firstNameTxt.setText(consumer.firstName)
            binding.lastNameTxt.setText(consumer.lastName)
            binding.emailTxt.setText(consumer.email)
            binding.phoneTxt.setText(consumer.phoneNumber)
            binding.firstOrderCheck.isChecked = consumer?.isFirstOrder ?: false
        })
        binding.shopBtn.setOnClickListener {
            TamaraPayment.setPaymentType("PAY_BY_INSTALMENTS")
            TamaraPayment.setCustomerInfo(binding.firstNameTxt.text.toString(), binding.lastNameTxt.text.toString(),binding.phoneTxt.text.toString(),
                binding.emailTxt.text.toString(),binding.firstOrderCheck.isChecked)
            findNavController(this).navigate(R.id.shopFragment)
        }
    }


}
