package com.co.wewin.adapters_viewholders

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.co.wewin.databinding.GameResultRecyclerLayoutBinding
import com.co.wewin.model.responses.andarBaharResponses.AndarBaharGameResultResponse

class GameResultListRecyclerAdapter(val activity: Activity, private val mEntries: ArrayList<AndarBaharGameResultResponse?>?):RecyclerView.Adapter<GameResultListRecyclerViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GameResultListRecyclerViewHolder {
        val layoutInflater=LayoutInflater.from(parent.context)
        val binding=GameResultRecyclerLayoutBinding.inflate(layoutInflater,parent,false)
        return GameResultListRecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GameResultListRecyclerViewHolder, position: Int) {
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