package com.co.wewin.adapters_viewholders

import android.app.Activity
import androidx.recyclerview.widget.RecyclerView
import com.co.wewin.R
import com.co.wewin.WeWinApplication.AppConstants
import com.co.wewin.databinding.AndarBaharBottomBiddingRecyclerLayoutBinding
import com.co.wewin.model.responses.andarBaharResponses.BuyOrderAnderBaharAndFastParityAndDiceResponse

class DashboardBottomBiddingListRecyclerViewHolder(private val binding: AndarBaharBottomBiddingRecyclerLayoutBinding):RecyclerView.ViewHolder(binding.root) {
    fun bindTo(activity: Activity, data: BuyOrderAnderBaharAndFastParityAndDiceResponse?){
        if (data!=null){
            binding.textView16.text=data.gameSession
            binding.textView15.text=data.userMobileNo
            if (data.option.equals(AppConstants.AndarBaharKeys.A_as_andar,true))
                binding.resultCircleCard.background.setTint(activity.resources.getColor(R.color.andar_card_color) )
            if (data.option.equals(AppConstants.AndarBaharKeys.B_as_bahar,true))
                binding.resultCircleCard.background.setTint(activity.resources.getColor(R.color.bahar_card_color) )
            if (data.option.equals(AppConstants.AndarBaharKeys.T_as_tie,true))
                binding.resultCircleCard.background.setTint(activity.resources.getColor(R.color.tie_card_color) )
            binding.textView2.text=data.option
            binding.textView14.text="Rs "+data.amount
        }
    }
}