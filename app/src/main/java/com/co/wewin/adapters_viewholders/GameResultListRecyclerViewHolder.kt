package com.co.wewin.adapters_viewholders

import android.app.Activity
import androidx.recyclerview.widget.RecyclerView
import com.co.wewin.R
import com.co.wewin.WeWinApplication.AppConstants
import com.co.wewin.databinding.GameResultRecyclerLayoutBinding
import com.co.wewin.model.responses.andarBaharResponses.AndarBaharGameResultResponse
import kotlin.collections.ArrayList

class GameResultListRecyclerViewHolder(private val binding: GameResultRecyclerLayoutBinding):RecyclerView.ViewHolder(binding.root) {
    fun bindTo(activity: Activity, itemOrderArray: AndarBaharGameResultResponse){
        binding.textView.text=itemOrderArray.gameSession
        if (itemOrderArray.answer!=null){
            binding.textView2.text=itemOrderArray.answer
            if (itemOrderArray.answer.equals(AppConstants.AndarBaharKeys.A_as_andar,true))
                binding.resultCircleCard.background.setTint(activity.resources.getColor(R.color.andar_card_color) )
            if (itemOrderArray.answer.equals(AppConstants.AndarBaharKeys.B_as_bahar,true))
                binding.resultCircleCard.background.setTint(activity.resources.getColor(R.color.bahar_card_color) )
            if (itemOrderArray.answer.equals(AppConstants.AndarBaharKeys.T_as_tie,true))
                binding.resultCircleCard.background.setTint(activity.resources.getColor(R.color.tie_card_color) )

        }
        else
            binding.textView2.text="?"

    }
}