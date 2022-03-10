package com.co.wewin.views.andarBaharGameFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.co.wewin.viewModels.andarBahar.AndarBaharBaseFragmentViewModel
import com.co.wewin.viewModels.factory.WeWinViewModelFactory

open class AndarBaharBaseFragment : Fragment() {
    lateinit var andarBaharBottomMyOrderViewModelAndarBahar: AndarBaharBaseFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setViewModel()
    }

    private fun setViewModel() {
        andarBaharBottomMyOrderViewModelAndarBahar= ViewModelProvider(requireActivity(), WeWinViewModelFactory()).get(
            AndarBaharBaseFragmentViewModel::class.java)
    }
}