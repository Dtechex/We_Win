package com.co.wewin.views.preLoginFragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import com.co.wewin.R
import com.co.wewin.databinding.FragmentRegisterBinding
import com.co.wewin.model.requests.RegisterRequest
import com.co.wewin.model.requests.SendOtpRequest
import com.co.wewin.utilities.NavigationUtils
import com.co.wewin.utilities.ToastUtils
import com.co.wewin.viewModels.factory.WeWinViewModelFactory
import com.co.wewin.viewModels.preLogin.RegisterViewModel
import com.co.wewin.views.PostLoginActivity

class RegisterFragment : Fragment(),View.OnClickListener {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    lateinit var registerViewModel: RegisterViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setViewModel()
        binding.regBRegFrag.setOnClickListener(this)
        binding.getCodeBRegFrag.setOnClickListener(this)
        binding.loginLFRegFrag.text= HtmlCompat.fromHtml(getString(R.string.registered_user),
            HtmlCompat.FROM_HTML_MODE_LEGACY)
        return root
    }

    private fun setViewModel() {
        registerViewModel =
            ViewModelProvider(this, WeWinViewModelFactory()).get(RegisterViewModel::class.java)

        registerViewModel.sendOtpApiResult.observe(viewLifecycleOwner, {
            if (it != null) {
                if (it.status){
                    ToastUtils.showLongToast("Otp Sent")
                }else{
                    it.message?.let { it1 -> ToastUtils.showShortToast(it1) }
                }
            }
        })

        registerViewModel.registerApiResult.observe(viewLifecycleOwner,{
            if (it != null) {
                if (it.status){
                    ToastUtils.showLongToast("Registered Successfully")
                    NavigationUtils.popBackStack(requireActivity())

                }else{
                    it.message?.let { it1 -> ToastUtils.showLongToast(it1) }
                }
            }
        })
    }

    override fun onClick(v: View?) {
        when(v){
            binding.getCodeBRegFrag->{
                registerViewModel.callSendOtpApi(SendOtpRequest(""+binding.phoneNumberIFRegFrag.text))
            }
            binding.regBRegFrag->{
                registerViewModel.callRegisterApi(RegisterRequest(
                    ""+binding.phoneNumberIFRegFrag.text,
                    ""+binding.passwordIFRegFrag.text,
                    ""+binding.verificationIFLofinFrag.text
                ))
            }
        }
    }
}