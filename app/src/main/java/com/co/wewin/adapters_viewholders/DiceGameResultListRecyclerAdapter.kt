package com.co.wewin.adapters_viewholders

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.co.wewin.databinding.GameResultRecyclerLayoutBinding
import com.co.wewin.model.responses.andarBaharResponses.AndarBaharGameResultResponse
import com.co.wewin.model.responses.diceResponse.DiceGameResultResponse
import com.co.wewin.model.responses.fastParityResponses.FastParityGameResultResponse

class DiceGameResultListRecyclerAdapter(val activity: Activity, private val mEntries: ArrayList<DiceGameResultResponse?>?):RecyclerView.Adapter<DiceGameResultListRecyclerViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DiceGameResultListRecyclerViewHolder {
        val layoutInflater=LayoutInflater.from(parent.context)
        val binding=GameResultRecyclerLayoutBinding.inflate(layoutInflater,parent,false)
        return DiceGameResultListRecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DiceGameResultListRecyclerViewHolder, position: Int) {
        val item=mEntries?.get(position)
        item?.let {
            holder.bindTo(activity,it)
        }
    }

    override fun getItemCount(): Int {
        return mEntries?.size ?:0
    }
}

//AndarBaharGameResultResponse