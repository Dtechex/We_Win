package com.co.wewin.adapters_viewholders

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.co.wewin.views.andarBaharGameFragments.AndarBaharBaseFragment
import com.co.wewin.views.fastParityGameFragments.FastParityBaseFragment
import java.util.ArrayList

class FastParityResultsViewPager2Adapter(private val listFragment: ArrayList<FastParityBaseFragment>,
                                         fm: FragmentManager,
                                         lifecycle: Lifecycle
) : FragmentStateAdapter(fm,lifecycle) {
    override fun getItemCount(): Int = listFragment.size

    override fun createFragment(position: Int): Fragment {
        return listFragment[position]
    }
}