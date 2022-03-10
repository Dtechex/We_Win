package com.co.wewin.adapters_viewholders

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.co.wewin.databinding.AndarBaharBottomBiddingRecyclerLayoutBinding
import com.co.wewin.model.responses.andarBaharResponses.BuyOrderAnderBaharAndFastParityAndDiceResponse

class DashboardBottomBiddingListRecyclerAdapter(val activity: Activity, private val mEntries: ArrayList<BuyOrderAnderBaharAndFastParityAndDiceResponse?>?):RecyclerView.Adapter<DashboardBottomBiddingListRecyclerViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DashboardBottomBiddingListRecyclerViewHolder {
        val layoutInflater=LayoutInflater.from(parent.context)
        val binding= AndarBaharBottomBiddingRecyclerLayoutBinding.inflate(layoutInflater,parent,false)
        return DashboardBottomBiddingListRecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DashboardBottomBiddingListRecyclerViewHolder, position: Int) {
        val item=mEntries?.get(position)
        item?.let {
            holder.bindTo(activity,it)
        }
    }

    override fun getItemCount(): Int {
        return mEntries?.size ?:0
    }
}