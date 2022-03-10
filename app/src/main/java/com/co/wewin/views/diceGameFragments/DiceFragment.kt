package com.co.wewin.views.diceGameFragments

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.co.wewin.R
import com.co.wewin.WeWinApplication.AppConstants
import com.co.wewin.adapters_viewholders.DashboardBottomBiddingListRecyclerAdapter
import com.co.wewin.adapters_viewholders.DiceResultsViewPager2Adapter
import com.co.wewin.databinding.AndarBaharOrderBottomSheetLayoutBinding
import com.co.wewin.databinding.FragmentDiceBinding
import com.co.wewin.model.requests.andarBaharGameRequests.AndarBaharAndFastParityAndDiceMyOrderRequest
import com.co.wewin.model.requests.andarBaharGameRequests.BuyOrderAnderBaharAndFastParityAndDiceRequest
import com.co.wewin.model.responses.BaseResponse
import com.co.wewin.model.responses.andarBaharResponses.BuyOrderAnderBaharAndFastParityAndDiceResponse
import com.co.wewin.model.responses.diceResponse.DiceNewSessionResponse
import com.co.wewin.utilities.OtherUtilities
import com.co.wewin.utilities.ToastUtils
import com.co.wewin.utilities.progressUtils.SocketUtils
import com.co.wewin.viewModels.dice.DiceViewModel
import com.co.wewin.viewModels.factory.WeWinViewModelFactory
import com.co.wewin.views.PostLoginActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.slider.Slider
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class DiceFragment : DiceBaseFragment(), View.OnClickListener {
    private var _binding: FragmentDiceBinding? = null
    val binding get() = _binding!!
    lateinit var diceViewModel: DiceViewModel
    private var countDownTimerSession:CountDownTimer?=null
    lateinit var fastParityBottomSheetDialogForOrder: BottomSheetDialog
    private var biddingList:ArrayList<BuyOrderAnderBaharAndFastParityAndDiceResponse?>? = null
    lateinit var dashboardBottomBiddingListRecyclerAdapter: DashboardBottomBiddingListRecyclerAdapter
    private val TAG = "DiceFragment"
    private var lessThenValue1=0
    private var lessThenValue2=0
    private var lessThenRatio1=0F
    private var lessThenRatio2=0F


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentDiceBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewModel()
        init()
        findViews()
        emitJoinSocket()
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.bottomBiddingRVDiceFrag.layoutManager = layoutManager
        biddingList=ArrayList()
        dashboardBottomBiddingListRecyclerAdapter= DashboardBottomBiddingListRecyclerAdapter(requireActivity(),
            biddingList)
        binding.bottomBiddingRVDiceFrag.adapter=dashboardBottomBiddingListRecyclerAdapter
    }


    private fun emitJoinSocket() {
        val data = JSONObject()
        data.put("gameName", AppConstants.DiceKeys.game_name)
        data.put("_id", AppConstants.DiceKeys.game_id)
        SocketUtils.customSocketEmit(AppConstants.SocketKeys.join,data)

        listenAndarBaharSocketEvent()
    }

    private fun listenAndarBaharSocketEvent() {
        SocketUtils.mSocket!!.on(AppConstants.SocketKeys.andarBahar) { args ->
            val data = args[0] as JSONObject
            val newData=data.getJSONObject("data")
            val userId:String
            val amount:String
            val option:String
            val userMobileNo:String
            val gameSession:String
            val avatar:String
            val gameName:String
            val createdAt:String
            val updatedAt:String
            userId = if (newData.has("userId")){
                newData.getString("userId")
            }else{
                "null"
            }
            amount = if (newData.has("amount")){
                newData.getString("amount")
            }else{
                "null"
            }
            option = if (newData.has("option")){
                newData.getString("option")
            }else{
                "null"
            }
            userMobileNo = if (newData.has("userMobileNo")){
                newData.getString("userMobileNo")
            }else{
                "null"
            }
            gameSession = if (newData.has("gameSession")){
                newData.getString("gameSession")
            }else{
                "null"
            }
            avatar = if (newData.has("avatar")){
                newData.getString("avatar")
            }else{
                "null"
            }
            gameName = if (newData.has("gameName")){
                newData.getString("gameName")
            }else{
                "null"
            }
            createdAt = if (newData.has("createdAt")){
                newData.getString("createdAt")
            }else{
                "null"
            }
            updatedAt = if (newData.has("updatedAt")){
                newData.getString("updatedAt")
            }else{
                "null"
            }
            val andarBaharMyOrderResponseItem= BuyOrderAnderBaharAndFastParityAndDiceResponse(
                userId = userId,
                amount = amount,
                option = option,
                userMobileNo=userMobileNo,
                gameSession = gameSession,
                avatar = avatar,
                gameName = gameName,
                _id="",
                createdAt = createdAt,
                updatedAt = updatedAt,
            )
            biddingList?.add(andarBaharMyOrderResponseItem)
//            dashboardBottomBiddingListRecyclerAdapter=DashboardBottomBiddingListRecyclerAdapter(requireActivity(),biddingList)


            val msg: String?
            //                msg = data.getString("message")
            requireActivity().runOnUiThread {
                if (SocketUtils.mSocket!!.id() != null) {
                    dashboardBottomBiddingListRecyclerAdapter.notifyDataSetChanged()
                }
            }
        }
    }


    private fun findViews() {
        fastParityBottomSheetDialogForOrder = BottomSheetDialog(requireContext())
        binding.button3.setOnClickListener(this)


        val resultsFragmentList = arrayListOf(
            DiceBottomResultFragment(),
            DiceBottomMyOrderFragment()
        )

        val diceResultsViewPager2Adapter = DiceResultsViewPager2Adapter(resultsFragmentList, activity?.supportFragmentManager!!, lifecycle)
        binding.diceResultsViewPager2.adapter=diceResultsViewPager2Adapter

        TabLayoutMediator(binding.tabLayout,binding.diceResultsViewPager2){tab,position->
            binding.diceResultsViewPager2.setCurrentItem(tab.position,true)
            if (position==1)
                tab.text = getString(R.string.tabMyOrders)
            if (position==0)
                tab.text = getString(R.string.tabResults)
        }.attach()

        binding.slider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {

            }

            override fun onStopTrackingTouch(slider: Slider) {
                val decimalVal=slider.value
                if (decimalVal<lessThenValue1){
                    binding.button3.text="Less Than "+lessThenValue1
                    binding.lessThenValueLFDiceFrag.text=""+lessThenValue1
                    binding.multiplierValueLFDiceFrag.text=""+lessThenRatio1
                }else if (decimalVal>=lessThenValue1 && decimalVal<lessThenValue2){
                    binding.button3.text="Less Than "+lessThenValue2
                    binding.lessThenValueLFDiceFrag.text=""+lessThenValue2
                    binding.multiplierValueLFDiceFrag.text=""+lessThenRatio2
                }else if(decimalVal>=lessThenValue2){
                    binding.button3.text="Less Than "+lessThenValue2
                    binding.slider.value=lessThenValue2.toFloat()
                    binding.lessThenValueLFDiceFrag.text=""+lessThenValue2
                    binding.multiplierValueLFDiceFrag.text=""+lessThenRatio2
                }
            }
        })

        binding.slider.addOnChangeListener { slider, value, fromUser ->
            // Responds to when slider's value is changed
        }
    }

    private fun init() {
        diceViewModel.callDiceNewSessionApi()

    }

    private fun setViewModel() {
        diceViewModel=ViewModelProvider(this,WeWinViewModelFactory()).get(DiceViewModel::class.java)

        diceViewModel.diceNewSessionApiResult.observe(viewLifecycleOwner, {
            if (it != null) {
                if (it.status) {
                    onSuccessDiceNewSession(it)
                } else {
                    it.message?.let { it1 -> ToastUtils.showShortToast(it1) }
                }
            }
        })

        diceViewModel.diceOrder.observe(viewLifecycleOwner, {
            if (it != null) {
                if (it.status) {
                    onSuccessDiceBuyOrder(it)
                } else {
                    it.message?.let { it1 -> ToastUtils.showShortToast(it1) }
                }
            }
        })


    }

    private fun onSuccessDiceBuyOrder(responseBuyAndFastParityAndDice: BaseResponse<BuyOrderAnderBaharAndFastParityAndDiceResponse?>?) {
        if (responseBuyAndFastParityAndDice != null) {
            responseBuyAndFastParityAndDice.message?.let { ToastUtils.showShortToast(it) }
            fastParityBottomSheetDialogForOrder.dismiss()
            val data = JSONObject()
            data.put("status",true)
            data.put("_id", AppConstants.FastParityKeys.game_id)
            val jsonResponseDataString = Gson().toJson(responseBuyAndFastParityAndDice.data)
            val jsonResponseDataObject= JSONObject(jsonResponseDataString)
            Log.e(TAG, "onSuccessAndarBaharBuyOrder: "+jsonResponseDataString )
            data.put("data",jsonResponseDataObject)
            SocketUtils.customSocketEmit(AppConstants.SocketKeys.andarBahar,data)
            diceBaseViewModel.callDiceMyOrders(
                AndarBaharAndFastParityAndDiceMyOrderRequest(
                    AppConstants.DiceKeys.game_name
                )
            )
        }
    }

    private fun onSuccessDiceNewSession(response: BaseResponse<DiceNewSessionResponse?>) {
        if (response != null) {
            if (response.data != null) {
                binding.periodIdLFDiceFrag.text = response.data.gameSession
                binding.multiplierValueLFDiceFrag.text=response.data.rationNumberlessThanFifty
                binding.lessThenValueLFDiceFrag.text="50"
                if (response.data.rationNumberlessThanFifty!=null && response.data.rationNumberlessThanNinety!=null) {
                    lessThenRatio1=(response.data.rationNumberlessThanFifty).toFloat()
                    lessThenRatio2=(response.data.rationNumberlessThanNinety).toFloat()
                    lessThenValue1=50
                    lessThenValue2=92
                    binding.slider.value=lessThenValue1.toFloat()
                }
                if (response.data.createdAt!=null && response.data.gameSession!=null) {
                    setCountDown(response.data.createdAt,response.data.gameSession)
                }
            }
        }
    }

    private fun setCountDown(createdAt: Long,sessionId:String) {
        val currenttimeMilliSecLong: Long = System.currentTimeMillis()
        val currentTimeDate: Date = OtherUtilities.getDate(currenttimeMilliSecLong)
        val serverCreatedAtDate: Date = OtherUtilities.getDate(createdAt)
        val diffInMs: Long = currentTimeDate.time - serverCreatedAtDate.time
        val diffInSec: Long = TimeUnit.MILLISECONDS.toSeconds(diffInMs)
        val tempMilliSeconds=5000
        val milliSecondsInFuture = (60000 - diffInMs)+tempMilliSeconds
        var secondLeft = TimeUnit.MILLISECONDS.toSeconds(milliSecondsInFuture)
        if (secondLeft<0){

            val dateFormat: String = "dd/MM/yyyy hh:mm:ss.SSS"
            val formatter = SimpleDateFormat(dateFormat, Locale.ENGLISH)
            formatter.timeZone=(TimeZone.getTimeZone("UTC"));
            ToastUtils.showLongToast("Server Time :- "+ OtherUtilities.convertToLocalDateFromMilliseconds(serverCreatedAtDate)+"\nDevice Time "+ OtherUtilities.convertToLocalDateFromMilliseconds(currentTimeDate))
            return
        }
        countDownTimerSession = object : CountDownTimer((milliSecondsInFuture), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.countDownValueLFDiceFrag.text = "" + secondLeft--
            }

            override fun onFinish() {
                diceBaseViewModel.callDiceGameResult()
                binding.countDownValueLFDiceFrag.text = "Over"
                diceViewModel.callDiceNewSessionApi()
                biddingList?.clear()
                dashboardBottomBiddingListRecyclerAdapter.notifyDataSetChanged()
            }
        }.start()
    }

    override fun onClick(v: View?) {
        when(v){
            binding.button3->{
                showBottomSheet(binding.slider.value.toInt().toString())
            }
        }
    }

    private fun showBottomSheet(type: String) {
        val bottomSheeetBinding =
            AndarBaharOrderBottomSheetLayoutBinding.inflate(layoutInflater, null, false)
        fastParityBottomSheetDialogForOrder.setContentView(bottomSheeetBinding.root)
        fastParityBottomSheetDialogForOrder.show()

        bottomSheeetBinding.typeLFBottomAndarBaharFrag.text =AppConstants.FastParityKeys.parity+" - "+type
        bottomSheeetBinding.buyBBottomAndarBaharFrag.setOnClickListener {
            diceViewModel.callDiceBuyOrderApi(
                BuyOrderAnderBaharAndFastParityAndDiceRequest(
                    amount = bottomSheeetBinding.amountLFBottomAndarBaharFrag.text.toString(),
                    option = type,
                    gameSession = binding.periodIdLFDiceFrag.text.toString(),
                    avatar = "",
                    gameName = AppConstants.DiceKeys.game_name
                )
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimerSession?.cancel()
        if (SocketUtils.mSocket!=null){
            if (SocketUtils.mSocket!!.connected())
                SocketUtils.mSocket!!.off()
        }
    }


}