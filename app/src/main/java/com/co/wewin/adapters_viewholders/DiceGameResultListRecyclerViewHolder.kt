package com.co.wewin.adapters_viewholders

import android.app.Activity
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.co.wewin.R
import com.co.wewin.WeWinApplication.AppConstants
import com.co.wewin.databinding.GameResultRecyclerLayoutBinding
import com.co.wewin.model.responses.andarBaharResponses.AndarBaharGameResultResponse
import com.co.wewin.model.responses.diceResponse.DiceGameResultResponse
import com.co.wewin.model.responses.fastParityResponses.FastParityGameResultResponse
import kotlin.collections.ArrayList

class DiceGameResultListRecyclerViewHolder(private val binding: GameResultRecyclerLayoutBinding):RecyclerView.ViewHolder(binding.root) {
    fun bindTo(activity: Activity, itemOrderArray: DiceGameResultResponse){
        binding.textView.text=itemOrderArray.gameSession
        if (itemOrderArray.randomNumber!=null){
            binding.textView2.text=itemOrderArray.randomNumber
            binding.resultCircleCard.background.setTint(activity.resources.getColor(R.color.dice_result_non_null_bg) )
        } else {
            binding.resultCircleCard.background.setTint(activity.resources.getColor(R.color.dice_result_null_bg) )
            binding.textView2.text="?"
        }
    }
}