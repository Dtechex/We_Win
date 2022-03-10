package com.co.wewin.views.diceGameFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.co.wewin.adapters_viewholders.DiceGameResultListRecyclerAdapter
import com.co.wewin.adapters_viewholders.FastParityGameResultListRecyclerAdapter
import com.co.wewin.databinding.FragmentAndarBaharBottomOrderAndResultBinding
import com.co.wewin.utilities.ToastUtils
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

class DiceBottomResultFragment:DiceBaseFragment() {
    private var _binding: FragmentAndarBaharBottomOrderAndResultBinding?=null
    val binding get()=_binding!!
    private var gameResultListRecyclerAdapter: DiceGameResultListRecyclerAdapter?=null
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
        val layoutManager = FlexboxLayoutManager(requireContext())
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.CENTER
        binding.resultsAndMyOrdersRVMyOrderFragment.layoutManager = layoutManager
    }

    private fun init() {
        diceBaseViewModel.callDiceGameResult()
    }

    private fun setViewModel() {
        diceBaseViewModel.diceGameResultResponse.observe(viewLifecycleOwner,{
            if (it != null) {
                if (it.status){
                    if (it.data!=null){
                        gameResultListRecyclerAdapter= DiceGameResultListRecyclerAdapter(requireActivity(), it.data.results)
                        binding.resultsAndMyOrdersRVMyOrderFragment.adapter=gameResultListRecyclerAdapter
                    }
                }else{
                    ToastUtils.showShortToast(it.message!!)
                }
            }
        })
    }
}