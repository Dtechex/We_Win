package com.co.wewin.views.diceGameFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.co.wewin.viewModels.dice.DiceBaseFragmentViewModel
import com.co.wewin.viewModels.factory.WeWinViewModelFactory

open class DiceBaseFragment:Fragment() {
    lateinit var diceBaseViewModel: DiceBaseFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setViewModel()
    }

    private fun setViewModel() {
        diceBaseViewModel= ViewModelProvider(requireActivity(), WeWinViewModelFactory()).get(
            DiceBaseFragmentViewModel::class.java)
    }
}