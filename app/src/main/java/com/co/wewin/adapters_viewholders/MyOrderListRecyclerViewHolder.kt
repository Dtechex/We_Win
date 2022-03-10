package com.co.wewin.adapters_viewholders

import android.app.Activity
import androidx.recyclerview.widget.RecyclerView
import com.co.wewin.databinding.MyOrderRecyclerLayoutBinding
import com.co.wewin.model.responses.andarBaharResponses.AndarBaharMyOrderResponse
import java.text.SimpleDateFormat
import java.util.*

class MyOrderListRecyclerViewHolder(private val binding:MyOrderRecyclerLayoutBinding):RecyclerView.ViewHolder(binding.root) {
    fun bindTo(activity: Activity, itemOrderArray: AndarBaharMyOrderResponse){
        binding.textView.text="At "+itemOrderArray.createdAt
        val dateFormat: String? = "dd/MM/yyyy hh:mm:ss.SSS"
        val formatter = SimpleDateFormat(dateFormat)
        formatter.timeZone=(TimeZone.getTimeZone("UTC"))
        val calendar = Calendar.getInstance()
//        var date=formatter.parse(itemOrderArray.createdAt)


//        val serverTime: Date = OtherUtilities.getDate(itemOrderArray.createdAt!!.toLong())
        binding.textView2.text=" For Rs "+itemOrderArray.amount
    }
}