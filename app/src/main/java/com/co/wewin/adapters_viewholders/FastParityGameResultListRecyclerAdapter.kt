package com.co.wewin.adapters_viewholders

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.co.wewin.databinding.GameResultRecyclerLayoutBinding
import com.co.wewin.model.responses.andarBaharResponses.AndarBaharGameResultResponse
import com.co.wewin.model.responses.fastParityResponses.FastParityGameResultResponse

class FastParityGameResultListRecyclerAdapter(val activity: Activity, private val mEntries: ArrayList<FastParityGameResultResponse?>?):RecyclerView.Adapter<FastParityGameResultListRecyclerViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FastParityGameResultListRecyclerViewHolder {
        val layoutInflater=LayoutInflater.from(parent.context)
        val binding=GameResultRecyclerLayoutBinding.inflate(layoutInflater,parent,false)
        return FastParityGameResultListRecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FastParityGameResultListRecyclerViewHolder, position: Int) {
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