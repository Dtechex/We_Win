package com.co.wewin.adapters_viewholders

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.co.wewin.databinding.MyOrderRecyclerLayoutBinding
import com.co.wewin.model.responses.andarBaharResponses.AndarBaharMyOrderResponse

class MyOrderListRecyclerAdapter(val activity: Activity, private val mEntries: ArrayList<AndarBaharMyOrderResponse?>?):RecyclerView.Adapter<MyOrderListRecyclerViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyOrderListRecyclerViewHolder {
        val layoutInflater=LayoutInflater.from(parent.context)
        val binding=MyOrderRecyclerLayoutBinding.inflate(layoutInflater,parent,false)
        return MyOrderListRecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyOrderListRecyclerViewHolder, position: Int) {
        val item=mEntries?.get(position)
        item?.let {
            holder.bindTo(activity,it)
        }
    }

    override fun getItemCount(): Int {
        return mEntries?.size ?:0
    }
}