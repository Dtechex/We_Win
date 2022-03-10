package com.co.wewin.views.postLoginFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.co.wewin.R
import com.co.wewin.databinding.FragmentFastParityBinding
import com.co.wewin.databinding.FragmentUpdateProfileBinding
import com.co.wewin.model.responses.BaseResponse
import com.co.wewin.model.responses.UserProfileDetailsResponse
import com.co.wewin.utilities.NavigationUtils
import com.co.wewin.utilities.ToastUtils
import com.co.wewin.utilities.network.NetworkConstants
import com.co.wewin.views.PostLoginActivity
import com.squareup.picasso.Picasso

class UpdateProfileFragment : Fragment(),View.OnClickListener {
    private var _binding: FragmentUpdateProfileBinding? = null
    val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewModel()
        init()
        binding.textView28.setOnClickListener(this)
        binding.textView34.setOnClickListener(this)
        binding.textView35.setOnClickListener(this)
    }

    private fun setViewModel() {
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
            binding.textView29.text=response.data.nickName
            binding.textView30.text="User: "+response.data.mobileNo
            binding.textView31.text="Inviter ID: "
            Picasso.get().load(NetworkConstants.BASE_URL+response.data.profilePicUrl).into(binding.circleImageView)
        }
    }

    override fun onResume() {
        super.onResume()
        init()
    }

    private fun init(){
        (activity as PostLoginActivity).postLoginActivityViewModel.callUserDetails()
    }

    override fun onClick(v: View?) {
        when(v){
            binding.textView28->{
                val bundle=Bundle()
                bundle.putString("type",""+getString(R.string.picture))
                NavigationUtils.goToFragment(activity = activity as PostLoginActivity,bundle=bundle, navigateToId = R.id.commonProfileUpdateFragment)
            }
            binding.textView34->{
                val bundle=Bundle()
                bundle.putString("type",""+getString(R.string.nickName))
                NavigationUtils.goToFragment(activity = activity as PostLoginActivity,bundle=bundle, navigateToId = R.id.commonProfileUpdateFragment)
            }
            binding.textView35->{
                val bundle=Bundle()
                bundle.putString("type",""+getString(R.string.password))
                NavigationUtils.goToFragment(activity = activity as PostLoginActivity,bundle=bundle, navigateToId = R.id.commonProfileUpdateFragment)
            }
        }
    }
}