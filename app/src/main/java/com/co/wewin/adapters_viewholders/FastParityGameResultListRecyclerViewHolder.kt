package com.co.wewin.adapters_viewholders

import android.app.Activity
import androidx.recyclerview.widget.RecyclerView
import com.co.wewin.R
import com.co.wewin.WeWinApplication.AppConstants
import com.co.wewin.databinding.GameResultRecyclerLayoutBinding
import com.co.wewin.model.responses.andarBaharResponses.AndarBaharGameResultResponse
import com.co.wewin.model.responses.fastParityResponses.FastParityGameResultResponse
import kotlin.collections.ArrayList

class FastParityGameResultListRecyclerViewHolder(private val binding: GameResultRecyclerLayoutBinding):RecyclerView.ViewHolder(binding.root) {
    fun bindTo(activity: Activity, itemOrderArray: FastParityGameResultResponse){
        binding.textView.text=itemOrderArray.gameSession
        if (itemOrderArray.winColor!=null){
            if (itemOrderArray.winColor.equals(AppConstants.FastParityKeys.green,true))
                binding.resultCircleCard.background.setTint(activity.resources.getColor(R.color.join_green_color) )
            if (itemOrderArray.winColor.equals(AppConstants.FastParityKeys.red,true))
                binding.resultCircleCard.background.setTint(activity.resources.getColor(R.color.join_red_color) )
            if (itemOrderArray.winColor.equals(AppConstants.FastParityKeys.violet,true)){
                binding.resultCircleCard.background.setTint(activity.resources.getColor(R.color.join_violet_color) )
            }

            if (itemOrderArray.winNumber != "")
                binding.textView2.text=itemOrderArray.winNumber
            else
                binding.textView2.text="?"

        }

    }
}