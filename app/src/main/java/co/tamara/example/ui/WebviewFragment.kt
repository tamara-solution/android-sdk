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
import kotlinx.android.synthetic.main.fragment_webview.*

class WebviewFragment : Fragment() {
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_webview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webView.setWebChromeClient(WebChromeClient())
        webView.setWebViewClient(WebViewClient())
        webView.getSettings().setJavaScriptEnabled(true)
        val bundle = arguments
        if (bundle != null) {
            val url = bundle.getString("url")
            webView.loadUrl(url!!)
        }

        icClose.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}