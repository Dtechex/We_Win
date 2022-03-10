package com.co.wewin.views.postLoginFragments

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.co.wewin.adapters_viewholders.DashboardGamesListRecyclerAdapter
import com.co.wewin.databinding.FragmentDashboardBinding
import com.co.wewin.model.responses.BaseResponse
import com.co.wewin.model.responses.UserProfileDetailsResponse
import com.co.wewin.utilities.Socket
import com.co.wewin.utilities.ToastUtils
import com.co.wewin.utilities.progressUtils.SocketUtils
import com.co.wewin.viewModels.factory.WeWinViewModelFactory
import com.co.wewin.viewModels.postLogin.DashboardGameListViewModle
import com.co.wewin.views.PostLoginActivity

class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding?=null
    lateinit var dashboardGameListViewModle: DashboardGameListViewModle
    private var dashboardGamesListRecyclerAdapter: DashboardGamesListRecyclerAdapter?=null
    val binding get()=_binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentDashboardBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewModel()
        init()
        setDashBoardDetails()

        val layoutManager = GridLayoutManager(activity,2)
        binding.dashBoardRVDashBoardFragment.layoutManager = layoutManager

    }

    override fun onResume() {
        super.onResume()
        setDashBoardDetails()
        (activity as PostLoginActivity).binding.bottomNav.visibility= View.VISIBLE
    }

    private fun setDashBoardDetails() {
        (activity as PostLoginActivity).postLoginActivityViewModel.callUserDetails()
    }

    private fun init() {
        dashboardGameListViewModle.callDashboardGameListResult()

    }

    private fun setViewModel() {
        dashboardGameListViewModle=
            ViewModelProvider(this, WeWinViewModelFactory()).get(DashboardGameListViewModle::class.java)
        dashboardGameListViewModle.dashboardGameListResponse.observe(viewLifecycleOwner,{
            if (it != null) {
                if (it.status){
                    if (it.data!=null){
                        dashboardGamesListRecyclerAdapter= DashboardGamesListRecyclerAdapter(requireActivity(), it.data.gameList)
                        binding.dashBoardRVDashBoardFragment.adapter=dashboardGamesListRecyclerAdapter
                    }

                }else{
                    ToastUtils.showShortToast(it.message!!)
                }
            }
        })

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
        if (response.data!=null){
            binding.textView18.text=""+response.data.walletBalance+".00"
            binding.textView20.text="ID: "+response.data._id


        }
    }
}