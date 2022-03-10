package com.co.wewin.views.fastParityGameFragments

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
import com.co.wewin.adapters_viewholders.FastParityResultsViewPager2Adapter
import com.co.wewin.databinding.AndarBaharOrderBottomSheetLayoutBinding
import com.co.wewin.databinding.FragmentFastParityBinding
import com.co.wewin.model.requests.andarBaharGameRequests.AndarBaharAndFastParityAndDiceMyOrderRequest
import com.co.wewin.model.requests.andarBaharGameRequests.BuyOrderAnderBaharAndFastParityAndDiceRequest
import com.co.wewin.model.responses.BaseResponse
import com.co.wewin.model.responses.andarBaharResponses.BuyOrderAnderBaharAndFastParityAndDiceResponse
import com.co.wewin.model.responses.fastParityResponses.FastParityNewSessionResponse
import com.co.wewin.utilities.OtherUtilities
import com.co.wewin.utilities.ToastUtils
import com.co.wewin.utilities.progressUtils.SocketUtils
import com.co.wewin.viewModels.factory.WeWinViewModelFactory
import com.co.wewin.viewModels.fastParity.FastParityViewModel
import com.co.wewin.views.PostLoginActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class FastParityFragment : FastParityBaseFragment(),View.OnClickListener {
    private var _binding: FragmentFastParityBinding? = null
    val binding get() = _binding!!
    lateinit var fastParityViewModel: FastParityViewModel
    private var countDownTimerSession:CountDownTimer?=null
    lateinit var fastParityBottomSheetDialogForOrder: BottomSheetDialog
    private var biddingList:ArrayList<BuyOrderAnderBaharAndFastParityAndDiceResponse?>? = null
    lateinit var dashboardBottomBiddingListRecyclerAdapter: DashboardBottomBiddingListRecyclerAdapter
    private  val TAG = "FastParityFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFastParityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewModel()
        findViews()
        init()
        emitJoinSocket()
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.bottomBiddingRVFastParityFrag.layoutManager = layoutManager
        biddingList=ArrayList()
        dashboardBottomBiddingListRecyclerAdapter= DashboardBottomBiddingListRecyclerAdapter(requireActivity(),
            biddingList)
        binding.bottomBiddingRVFastParityFrag.adapter=dashboardBottomBiddingListRecyclerAdapter
    }

    private fun emitJoinSocket() {
        val data = JSONObject()
        data.put("gameName", AppConstants.FastParityKeys.game_name)
        data.put("_id", AppConstants.FastParityKeys.game_id)
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
        binding.mainBuyGreenCVFastParityFrag.setOnClickListener(this)
        binding.mainBuyVoiletCVFastParityFrag.setOnClickListener(this)
        binding.mainBuyRedCVFastParityFrag.setOnClickListener(this)
        binding.mainBuy1CVFastParityFrag.setOnClickListener(this)
        binding.mainBuy2CVFastParityFrag.setOnClickListener(this)
        binding.mainBuy3CVFastParityFrag.setOnClickListener(this)
        binding.mainBuy4CVFastParityFrag.setOnClickListener(this)
        binding.mainBuy5CVFastParityFrag.setOnClickListener(this)
        binding.mainBuy6CVFastParityFrag.setOnClickListener(this)
        binding.mainBuy7CVFastParityFrag.setOnClickListener(this)
        binding.mainBuy8CVFastParityFrag.setOnClickListener(this)
        binding.mainBuy9CVFastParityFrag.setOnClickListener(this)
        binding.mainBuy0CVFastParityFrag.setOnClickListener(this)

        val resultsFragmentList = arrayListOf(
            FastParityBottomResutsFragment(),
            FastParityBottomMyOrderFragment()
        )

        val fastParityResultsViewPager2Adapter = FastParityResultsViewPager2Adapter(resultsFragmentList, activity?.supportFragmentManager!!, lifecycle)
        binding.fastParityResultsViewPager2.adapter=fastParityResultsViewPager2Adapter

        TabLayoutMediator(binding.tabLayout,binding.fastParityResultsViewPager2){tab,position->
            binding.fastParityResultsViewPager2.setCurrentItem(tab.position,true)
            if (position==1)
                tab.text = getString(R.string.tabMyOrders)
            if (position==0)
                tab.text = getString(R.string.tabResults)
        }.attach()


    }
    private fun init() {
        fastParityViewModel.callFastParityNewSessionApi()
    }

    private fun setViewModel() {
        fastParityViewModel =
            ViewModelProvider(this, WeWinViewModelFactory()).get(FastParityViewModel::class.java)

        fastParityViewModel.fastParityNewSessionApiResult.observe(viewLifecycleOwner, {
            if (it != null) {
                if (it.status) {
                    onSuccessFastPArityNewSession(it)
                } else {
                    it.message?.let { it1 -> ToastUtils.showShortToast(it1) }
                }
            }
        })

        fastParityViewModel.fastParityOrder.observe(viewLifecycleOwner, {
            if (it != null) {
                if (it.status) {
                    onSuccessFastParityBuyOrder(it)
                } else {
                    it.message?.let { it1 -> ToastUtils.showShortToast(it1) }
                }
            }
        })
    }

    private fun onSuccessFastParityBuyOrder(responseBuyAndFastParityAndDice: BaseResponse<BuyOrderAnderBaharAndFastParityAndDiceResponse?>?) {
        if (responseBuyAndFastParityAndDice != null) {
            responseBuyAndFastParityAndDice.message?.let { ToastUtils.showShortToast(it) }
            fastParityBottomSheetDialogForOrder.dismiss()
            val data = JSONObject()
            data.put("status",true)
            data.put("_id", AppConstants.FastParityKeys.game_id)
            val jsonResponseDataString = Gson().toJson(responseBuyAndFastParityAndDice.data)
            val jsonResponseDataObject=JSONObject(jsonResponseDataString)
            Log.e(TAG, "onSuccessAndarBaharBuyOrder: "+jsonResponseDataString )
            data.put("data",jsonResponseDataObject)
            SocketUtils.customSocketEmit(AppConstants.SocketKeys.andarBahar,data)
            fastParityBottomBaseViewModel.callFastParityMyOrders(
                AndarBaharAndFastParityAndDiceMyOrderRequest(
                    AppConstants.FastParityKeys.game_name
                )
            )
        }
    }

    private fun onSuccessFastPArityNewSession(response: BaseResponse<FastParityNewSessionResponse?>) {
        if (response != null) {
            if (response.data != null) {
                binding.periodIdLFFastParityFrag.text = response.data.gameSession
                binding.ratioGreenLFFastParityFrag.text=response.data.ratioGreen
                binding.ratioVioletLFFastParityFrag.text=response.data.ratioViolet
                binding.ratioRedLFFastParityFrag.text=response.data.ratioRed
                binding.ratioNumberLFFastParityFrag.text=response.data.ratioNumber
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
                binding.countDownValueLFFastParityFrag.text = "" + secondLeft--
            }

            override fun onFinish() {
                fastParityBottomBaseViewModel.callFastParityrGameResult()
                binding.countDownValueLFFastParityFrag.text = "Over"
                fastParityViewModel.callFastParityNewSessionApi()
                biddingList?.clear()
                dashboardBottomBiddingListRecyclerAdapter.notifyDataSetChanged()
            }
        }.start()
    }

    override fun onClick(v: View?) {
        when (v){
            binding.mainBuyGreenCVFastParityFrag -> {
                showBottomSheet(AppConstants.FastParityKeys.green)
            }
            binding.mainBuyVoiletCVFastParityFrag -> {
                showBottomSheet(AppConstants.FastParityKeys.red)
            }
            binding.mainBuyRedCVFastParityFrag -> {
                showBottomSheet(AppConstants.FastParityKeys.violet)
            }
            binding.mainBuy1CVFastParityFrag -> {
                showBottomSheet(AppConstants.FastParityKeys.parityOne)
            }
            binding.mainBuy2CVFastParityFrag -> {
                showBottomSheet(AppConstants.FastParityKeys.parityTwo)
            }
            binding.mainBuy3CVFastParityFrag -> {
                showBottomSheet(AppConstants.FastParityKeys.parityThree)
            }
            binding.mainBuy4CVFastParityFrag -> {
                showBottomSheet(AppConstants.FastParityKeys.parityFour)
            }
            binding.mainBuy5CVFastParityFrag -> {
                showBottomSheet(AppConstants.FastParityKeys.parityFive)
            }
            binding.mainBuy6CVFastParityFrag -> {
                showBottomSheet(AppConstants.FastParityKeys.paritySix)
            }
            binding.mainBuy7CVFastParityFrag -> {
                showBottomSheet(AppConstants.FastParityKeys.paritySeven)
            }
            binding.mainBuy8CVFastParityFrag -> {
                showBottomSheet(AppConstants.FastParityKeys.parityEight)
            }
            binding.mainBuy9CVFastParityFrag -> {
                showBottomSheet(AppConstants.FastParityKeys.parityNine)
            }
            binding.mainBuy0CVFastParityFrag -> {
                showBottomSheet(AppConstants.FastParityKeys.parityZero)
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
            fastParityViewModel.callFastParityBuyOrderApi(
                BuyOrderAnderBaharAndFastParityAndDiceRequest(
                    amount = bottomSheeetBinding.amountLFBottomAndarBaharFrag.text.toString(),
                    option = type,
                    gameSession = binding.periodIdLFFastParityFrag.text.toString(),
                    avatar = "",
                    gameName = AppConstants.FastParityKeys.game_name
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