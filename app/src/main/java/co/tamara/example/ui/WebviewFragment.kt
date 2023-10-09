package co.tamara.example.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import co.tamara.example.R
import co.tamara.example.databinding.FragmentWebviewBinding

class WebviewFragment : Fragment() {
    private var _binding: FragmentWebviewBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        requireActivity().window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
//        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
//        requireActivity().window.statusBarColor = ContextCompat.getColor(requireActivity(), R.color.black)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWebviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.webView.setWebChromeClient(WebChromeClient())
        binding.webView.setWebViewClient(WebViewClient())
        binding.webView.getSettings().setJavaScriptEnabled(true)
        val bundle = arguments
        if (bundle != null) {
            val url = bundle.getString("url")
            binding.webView.loadUrl(url!!)
        }

        binding.icClose.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}