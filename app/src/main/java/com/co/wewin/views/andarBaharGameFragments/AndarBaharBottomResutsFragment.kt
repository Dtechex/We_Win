package com.co.wewin.views.andarBaharGameFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.co.wewin.adapters_viewholders.GameResultListRecyclerAdapter
import com.co.wewin.databinding.FragmentAndarBaharBottomOrderAndResultBinding
import com.co.wewin.utilities.ToastUtils
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

class AndarBaharBottomResutsFragment : AndarBaharBaseFragment() {
    private var _binding: FragmentAndarBaharBottomOrderAndResultBinding?=null
    val binding get()=_binding!!
    private var gameResultListRecyclerAdapter:GameResultListRecyclerAdapter?=null
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
//        val decoration = DividerItemDecoration(context, RecyclerView.HORIZONTAL)
//        decoration.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.spades_icon)!!)
//        binding.resultsAndMyOrdersRVMyOrderFragment.addItemDecoration(decoration)
        val layoutManager = FlexboxLayoutManager(requireContext())
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.CENTER
        binding.resultsAndMyOrdersRVMyOrderFragment.layoutManager = layoutManager

//        val layoutManager = LinearLayoutManager(activity)
//        layoutManager.orientation = LinearLayoutManager.VERTICAL
//        binding.resultsAndMyOrdersRVMyOrderFragment.layoutManager = layoutManager
    }

    private fun init() {
        andarBaharBottomMyOrderViewModelAndarBahar.callAndarBaharGameResult()
    }

    private fun setViewModel() {
        andarBaharBottomMyOrderViewModelAndarBahar.andarBaharGameResultResponse.observe(viewLifecycleOwner,{
            if (it != null) {
                if (it.status){
                    if (it.data!=null){
                        gameResultListRecyclerAdapter= GameResultListRecyclerAdapter(requireActivity(), it.data.results)
                        binding.resultsAndMyOrdersRVMyOrderFragment.adapter=gameResultListRecyclerAdapter
                    }

                }else{
                    ToastUtils.showShortToast(it.message!!)
                }
            }
        })
    }
}