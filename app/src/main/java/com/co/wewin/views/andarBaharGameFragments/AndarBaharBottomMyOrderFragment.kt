package com.co.wewin.views.andarBaharGameFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.co.wewin.WeWinApplication.AppConstants
import com.co.wewin.adapters_viewholders.MyOrderListRecyclerAdapter
import com.co.wewin.databinding.FragmentAndarBaharBottomOrderAndResultBinding
import com.co.wewin.model.requests.andarBaharGameRequests.AndarBaharAndFastParityAndDiceMyOrderRequest
import com.co.wewin.utilities.ToastUtils

class AndarBaharBottomMyOrderFragment : AndarBaharBaseFragment() {
    private var _binding:FragmentAndarBaharBottomOrderAndResultBinding?=null
    val binding get()=_binding!!
    lateinit var myOrderListRecyclerAdapter:MyOrderListRecyclerAdapter
//    private var myOrderList:ArrayList<AndarBaharMyOrderResponse?>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentAndarBaharBottomOrderAndResultBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewModel()
        init()
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.resultsAndMyOrdersRVMyOrderFragment.layoutManager = layoutManager
//        myOrderList=arrayListOf(
//            AndarBaharMyOrderResponse("","","","","","","","")
//        )
//        myOrderListRecyclerAdapter= MyOrderListRecyclerAdapter(requireActivity(), myOrderList)
//        binding.resultsAndMyOrdersRVMyOrderFragment.adapter=myOrderListRecyclerAdapter
    }

    private fun init() {
        andarBaharBottomMyOrderViewModelAndarBahar.callAndarBaharMyOrders(
            AndarBaharAndFastParityAndDiceMyOrderRequest(
            AppConstants.AndarBaharKeys.game_name
        )
        )

    }

    private fun setViewModel() {
        andarBaharBottomMyOrderViewModelAndarBahar.andarBaharMyOrder.observe(viewLifecycleOwner,{
            if (it != null) {
                if (it.status){
                    if (it.data!=null){
                        if (it.data.orders!=null){
                            myOrderListRecyclerAdapter= MyOrderListRecyclerAdapter(requireActivity(), it.data.orders)
                            binding.resultsAndMyOrdersRVMyOrderFragment.adapter=myOrderListRecyclerAdapter
//                            for (item in it.data.orders)
//                                myOrderList?.add(item)
//                            myOrderListRecyclerAdapter.notifyDataSetChanged()
                        }
                    }

                }else{
                    ToastUtils.showShortToast(it.message!!)
                }
            }
        })
    }
}