package com.co.wewin.views.fastParityGameFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.co.wewin.viewModels.factory.WeWinViewModelFactory
import com.co.wewin.viewModels.fastParity.FastParityBaseFragmentViewModel

open class FastParityBaseFragment : Fragment() {
    lateinit var fastParityBottomBaseViewModel: FastParityBaseFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setViewModel()
    }

    private fun setViewModel() {
        fastParityBottomBaseViewModel= ViewModelProvider(requireActivity(), WeWinViewModelFactory()).get(
            FastParityBaseFragmentViewModel::class.java)
    }
}