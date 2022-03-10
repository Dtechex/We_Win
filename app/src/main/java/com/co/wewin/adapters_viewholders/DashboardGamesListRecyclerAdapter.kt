package com.co.wewin.adapters_viewholders

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.co.wewin.databinding.DashboardGamesListRecyclerLayoutBinding
import com.co.wewin.databinding.GameResultRecyclerLayoutBinding
import com.co.wewin.databinding.MyOrderRecyclerLayoutBinding
import com.co.wewin.model.responses.*

class DashboardGamesListRecyclerAdapter(val activity: Activity, private val mEntries: ArrayList<DashBoardGameListResponse?>?):RecyclerView.Adapter<DashboardGamesListRecyclerViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DashboardGamesListRecyclerViewHolder {
        val layoutInflater=LayoutInflater.from(parent.context)
        val binding= DashboardGamesListRecyclerLayoutBinding.inflate(layoutInflater,parent,false)
        return DashboardGamesListRecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DashboardGamesListRecyclerViewHolder, position: Int) {
        val item=mEntries?.get(position)
        item?.let {
            holder.bindTo(activity,it)
        }
    }

    override fun getItemCount(): Int {
        return mEntries?.size ?:0
    }
}