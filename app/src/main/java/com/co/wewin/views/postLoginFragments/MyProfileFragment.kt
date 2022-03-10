package com.co.wewin.views.postLoginFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.co.wewin.R
import com.co.wewin.databinding.FragmentMyProfileBinding
import com.co.wewin.databinding.FragmentUpdateProfileBinding
import com.co.wewin.model.responses.BaseResponse
import com.co.wewin.model.responses.UserProfileDetailsResponse
import com.co.wewin.utilities.NavigationUtils
import com.co.wewin.utilities.ToastUtils
import com.co.wewin.utilities.network.NetworkConstants
import com.co.wewin.viewModels.factory.WeWinViewModelFactory
import com.co.wewin.viewModels.postLogin.CommonProfileUpdateViewModel
import com.co.wewin.viewModels.postLogin.MyProfileViewModel
import com.co.wewin.views.PostLoginActivity
import com.squareup.picasso.Picasso

class MyProfileFragment : Fragment(),View.OnClickListener {
    private var _binding: FragmentMyProfileBinding? = null
    val binding get() = _binding!!
    lateinit var myProfileViewModel:MyProfileViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMyProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewModel()
        init()
        binding.textView33.setOnClickListener(this)
    }

    private fun init() {
        (activity as PostLoginActivity).postLoginActivityViewModel.callUserDetails()
    }

    private fun setViewModel() {
        myProfileViewModel = ViewModelProvider(this, WeWinViewModelFactory()).get(
            MyProfileViewModel::class.java
        )
        (activity as PostLoginActivity).postLoginActivityViewModel.userProfileDetailsResponse.observe(viewLifecycleOwner, {
            if (it != null) {
                if (it.status) {
                    onSuccessProfileDetails(it)
                } else {
                    it.message?.let { it1 -> ToastUtils.showShortToast(it1) }
                }
            }
        })
    }

    private fun onSuccessProfileDetails(response: BaseResponse<UserProfileDetailsResponse?>) {
        if (response.data!=null) {
            binding.textView31.text="ID: "+response.data._id
            binding.textView30.text="Phone: "+response.data.mobileNo
            binding.textView32.text="NickName: "+response.data.nickName
            Picasso.get().load(NetworkConstants.BASE_URL+response.data.profilePicUrl).into(binding.myProfileImage)

        }
    }

    override fun onResume() {
        super.onResume()
        init()
    }

    override fun onClick(v: View?) {
        when(v){
            binding.textView33->{
                NavigationUtils.goToFragment(activity=activity as PostLoginActivity, navigateToId = R.id.updateProfileFragment)
            }
        }
    }
}