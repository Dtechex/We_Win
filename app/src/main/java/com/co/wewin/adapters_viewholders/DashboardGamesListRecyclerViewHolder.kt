package com.co.wewin.adapters_viewholders

import android.app.Activity
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.co.wewin.R
import com.co.wewin.WeWinApplication.AppConstants
import com.co.wewin.databinding.DashboardGamesListRecyclerLayoutBinding
import com.co.wewin.model.responses.*
import com.co.wewin.utilities.NavigationUtils
import com.co.wewin.utilities.ToastUtils
import com.co.wewin.utilities.network.NetworkConstants
import com.co.wewin.views.PostLoginActivity
import com.squareup.picasso.Picasso

class DashboardGamesListRecyclerViewHolder(private val binding: DashboardGamesListRecyclerLayoutBinding):RecyclerView.ViewHolder(binding.root) {
    fun bindTo(activity: Activity, dashBoardGameListResponse: DashBoardGameListResponse?){
        binding.imageView2
        if (dashBoardGameListResponse != null) {
            Picasso.get().load(NetworkConstants.BASE_URL+dashBoardGameListResponse.gameImage).into(binding.imageView2)
            binding.imageView2.setOnClickListener {
                if (dashBoardGameListResponse._id == AppConstants.AndarBaharKeys.game_id) {
                    NavigationUtils.goToFragment(activity = activity, navigateToId = R.id.andarBaharFragment)
                    (activity as PostLoginActivity).binding.bottomNav.visibility= View.GONE
                } else if (dashBoardGameListResponse._id == AppConstants.CrashKeys.game_id) {
//                NavigationUtils.goToFragment(activity = activity, navigateToId = R.id.andarBaharFragment)
                    ToastUtils.showShortToast("In Progress `Crash Game`")
                } else if (dashBoardGameListResponse._id == AppConstants.DiceKeys.game_id) {
                NavigationUtils.goToFragment(activity = activity, navigateToId = R.id.diceFragment)
                    (activity as PostLoginActivity).binding.bottomNav.visibility= View.GONE
                } else if (dashBoardGameListResponse._id == AppConstants.FastParityKeys.game_id) {
                NavigationUtils.goToFragment(activity = activity, navigateToId = R.id.fastParityFragment)
                    (activity as PostLoginActivity).binding.bottomNav.visibility= View.GONE
                }
            }

        }
    }
}