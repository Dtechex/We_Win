package com.co.wewin.views.preLoginFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import com.co.wewin.views.PreLoginActivity
import com.co.wewin.R
import com.co.wewin.WeWinApplication.AppConstants
import com.co.wewin.databinding.FragmentLoginBinding
import com.co.wewin.model.requests.LoginRequest
import com.co.wewin.utilities.NavigationUtils
import com.co.wewin.utilities.StorageUtils2
import com.co.wewin.utilities.ToastUtils
import com.co.wewin.viewModels.factory.WeWinViewModelFactory
import com.co.wewin.viewModels.preLogin.LoginViewModel
import com.co.wewin.views.PostLoginActivity


class LoginFragment : Fragment(),View.OnClickListener {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var loginViewModel : LoginViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setViewModel()
        binding.registerLFLoginFrag.setOnClickListener(this)
        binding.loginBLoginFrag.setOnClickListener(this)
        binding.registerLFLoginFrag.text= HtmlCompat.fromHtml(getString(R.string.not_registered),HtmlCompat.FROM_HTML_MODE_LEGACY)
        return root
    }

    private fun setViewModel() {
        loginViewModel =
            ViewModelProvider(this, WeWinViewModelFactory()).get(LoginViewModel::class.java)

        loginViewModel.apilogin.observe(viewLifecycleOwner,{
            if (it != null) {
                if (it.status){
                    it.data!!.token?.let { it1 ->
                        StorageUtils2.storeString(AppConstants.StorageUtilKeys.userToken,
                            it1
                        )
                    }
                    it.data!!.mobileNo?.let { it1 ->
                        StorageUtils2.storeString(AppConstants.StorageUtilKeys.userMobile,
                            it1
                        )
                    }
                    NavigationUtils.changeActivity(requireActivity(),PostLoginActivity::class.java)
                }else{
                    ToastUtils.showLongToast(it.message!!)
                }
            }
        })
    }

    override fun onClick(v: View?) {
        when(v){
            binding.registerLFLoginFrag->{
                NavigationUtils.goToFragment(activity = activity as PreLoginActivity, navigateToId = R.id.regFragment)
            }
            binding.loginBLoginFrag->{
                loginViewModel.callLoginApi(LoginRequest(""+binding.phoneNumberIFLoginFrag.text,
                ""+binding.passwordIFLoginFrag.text))
            }
        }
    }
}