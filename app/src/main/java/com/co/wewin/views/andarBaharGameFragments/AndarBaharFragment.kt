package com.co.wewin.views.andarBaharGameFragments

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.co.wewin.R
import com.co.wewin.WeWinApplication.AppConstants
import com.co.wewin.adapters_viewholders.AndarBaharResultsViewPager2Adapter
import com.co.wewin.adapters_viewholders.DashboardBottomBiddingListRecyclerAdapter
import com.co.wewin.databinding.AndarBaharOrderBottomSheetLayoutBinding
import com.co.wewin.databinding.FragmentAndarBaharBinding
import com.co.wewin.model.requests.andarBaharGameRequests.BuyOrderAnderBaharAndFastParityAndDiceRequest
import com.co.wewin.model.requests.andarBaharGameRequests.AndarBaharSessionResultRequest
import com.co.wewin.model.responses.*
import com.co.wewin.utilities.CustomAlertDialogBox
import com.co.wewin.utilities.OtherUtilities
import com.co.wewin.utilities.ToastUtils
import com.co.wewin.viewModels.factory.WeWinViewModelFactory
import com.co.wewin.viewModels.andarBahar.AndarBaharViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import com.co.wewin.model.requests.andarBaharGameRequests.AndarBaharAndFastParityAndDiceMyOrderRequest
import android.animation.PropertyValuesHolder
import android.graphics.Typeface
import android.view.Gravity
import android.view.View.TRANSLATION_X
import android.view.View.TRANSLATION_Y
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.animation.addListener
import com.co.wewin.model.responses.andarBaharResponses.AndarBaharNewSessionResponse
import com.co.wewin.model.responses.andarBaharResponses.BuyOrderAnderBaharAndFastParityAndDiceResponse
import com.co.wewin.model.responses.andarBaharResponses.AndarBaharSessionResultResponse
import android.widget.LinearLayout
import com.co.wewin.utilities.progressUtils.SocketUtils
import com.co.wewin.views.PostLoginActivity


class AndarBaharFragment : AndarBaharBaseFragment(), View.OnClickListener {
    private var _binding: FragmentAndarBaharBinding? = null
    val binding get() = _binding!!
    lateinit var andarBagarViewModel: AndarBaharViewModel
    lateinit var dashboardBottomBiddingListRecyclerAdapter: DashboardBottomBiddingListRecyclerAdapter
    private val TAG = "AndarBaharFragment"
    private var biddingList:ArrayList<BuyOrderAnderBaharAndFastParityAndDiceResponse?>? = null
    lateinit var andarBaharBottomSheetDialogForOrder:BottomSheetDialog
    private var countDownTimerSession:CountDownTimer?=null
    lateinit var front_animation: AnimatorSet
    lateinit var back_animation: AnimatorSet
    lateinit var up_animation: AnimatorSet
    lateinit var down_animation: AnimatorSet
    var isFront =true
    var isUp =true
    var isFragmentRunning=true
    private var andar_history_index=0
    private var bahar_history_index=0
    private lateinit var pvhXForBahar: PropertyValuesHolder
    private lateinit var pvhYForBahar: PropertyValuesHolder
    private lateinit var objectAnimatorToBaharCard:ObjectAnimator

    private lateinit var pvhXForAndar: PropertyValuesHolder
    private lateinit var pvhYForAndar: PropertyValuesHolder
    private lateinit var objectAnimatorToAndarCard:ObjectAnimator

    private lateinit var pvhXForZero: PropertyValuesHolder
    private lateinit var pvhYForZero: PropertyValuesHolder
    private lateinit var objectAnimatorToZero:ObjectAnimator

    private lateinit var pvhXForShow: PropertyValuesHolder
    private lateinit var pvhYForShow: PropertyValuesHolder
    private lateinit var objectAnimatorToShowCard:ObjectAnimator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAndarBaharBinding.inflate(inflater, container, false)
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
        binding.bottomBiddingRVAndarBaharFrag.layoutManager = layoutManager
        biddingList=ArrayList()
        dashboardBottomBiddingListRecyclerAdapter= DashboardBottomBiddingListRecyclerAdapter(requireActivity(),
            biddingList)
        binding.bottomBiddingRVAndarBaharFrag.adapter=dashboardBottomBiddingListRecyclerAdapter
    }

    private fun emitJoinSocket() {
        val data = JSONObject()
        data.put("gameName", AppConstants.AndarBaharKeys.game_name)
        data.put("_id", AppConstants.AndarBaharKeys.game_id)
        SocketUtils.customSocketEmit(AppConstants.SocketKeys.join,data)

        listenAndarBaharSocketEvent()
    }

    private fun listenAndarBaharSocketEvent() {
        SocketUtils.mSocket!!.on(AppConstants.SocketKeys.andarBahar) { args ->
            val data = args[0] as JSONObject
            val newData = data.getJSONObject("data")
            val userId: String
            val amount: String
            val option: String
            val userMobileNo: String
            val gameSession: String
            val avatar: String
            val gameName: String
            val createdAt: String
            val updatedAt: String
            userId = if (newData.has("userId")) {
                newData.getString("userId")
            } else {
                "null"
            }
            amount = if (newData.has("amount")) {
                newData.getString("amount")
            } else {
                "null"
            }
            option = if (newData.has("option")) {
                newData.getString("option")
            } else {
                "null"
            }
            userMobileNo = if (newData.has("userMobileNo")) {
                newData.getString("userMobileNo")
            } else {
                "null"
            }
            gameSession = if (newData.has("gameSession")) {
                newData.getString("gameSession")
            } else {
                "null"
            }
            avatar = if (newData.has("avatar")) {
                newData.getString("avatar")
            } else {
                "null"
            }
            gameName = if (newData.has("gameName")) {
                newData.getString("gameName")
            } else {
                "null"
            }
            createdAt = if (newData.has("createdAt")) {
                newData.getString("createdAt")
            } else {
                "null"
            }
            updatedAt = if (newData.has("updatedAt")) {
                newData.getString("updatedAt")
            } else {
                "null"
            }
            val andarBaharMyOrderResponseItem = BuyOrderAnderBaharAndFastParityAndDiceResponse(
                userId = userId,
                amount = amount,
                option = option,
                userMobileNo = userMobileNo,
                gameSession = gameSession,
                avatar = avatar,
                gameName = gameName,
                _id = "",
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
            Log.e(TAG, "andarBahar: $data")
        }
    }
    private fun findViews() {
        andarBaharBottomSheetDialogForOrder = BottomSheetDialog(requireContext())
        binding.mainShowCardCVAndarBaharFrag.setOnClickListener(this)
        binding.mainBuyAndarCVAndarBaharFrag.setOnClickListener(this)
        binding.mainBuyBaharCVAndarBaharFrag.setOnClickListener(this)
        binding.mainBuyTieCVAndarBaharFrag.setOnClickListener(this)
        binding.mainBaharCardCVAndarBaharFrag.setOnClickListener(this)

        val resultsFragmentList = arrayListOf(
            AndarBaharBottomResutsFragment(),
            AndarBaharBottomMyOrderFragment()
        )

        val andarBaharResultsViewPager2Adapter = AndarBaharResultsViewPager2Adapter(resultsFragmentList, activity?.supportFragmentManager!!, lifecycle)
        binding.andarBaharResultsViewPager2.adapter=andarBaharResultsViewPager2Adapter

        TabLayoutMediator(binding.tabLayout,binding.andarBaharResultsViewPager2){tab,position->
            binding.andarBaharResultsViewPager2.setCurrentItem(tab.position,true)
            if (position==1)
                tab.text = getString(R.string.tabMyOrders)
            if (position==0)
                tab.text = getString(R.string.tabResults)
        }.attach()


        // Now Create Animator Object
        // For this we add animator folder inside res
        // Now we will add the animator to our card
        // we now need to modify the camera scale
        var scale = requireContext().resources.displayMetrics.density

        //front
        binding.deckShowViewConsVAndarBaharFrag2.cameraDistance = 8000 * scale
        //back
        binding.deckShowViewConsVAndarBaharFrag.cameraDistance = 8000 * scale


        // Now we will set the front animation
        front_animation = AnimatorInflater.loadAnimator(requireContext(), R.animator.front_animator) as AnimatorSet
        back_animation = AnimatorInflater.loadAnimator(requireContext(), R.animator.back_animator) as AnimatorSet
        up_animation = AnimatorInflater.loadAnimator(requireContext(), R.animator.up_animator) as AnimatorSet
        down_animation = AnimatorInflater.loadAnimator(requireContext(), R.animator.down_animator) as AnimatorSet


    }

    private fun init() {
        andarBagarViewModel.callAndarBaharNewSessionApi()
//        andarBagarViewModel.callAndarBaharUpdateApi(
//            UpdateAndarBaharRequest(
//            gameSession = "30004"
//        )
//        )
    }

    private fun setViewModel() {
        andarBagarViewModel =
            ViewModelProvider(this, WeWinViewModelFactory()).get(AndarBaharViewModel::class.java)

        andarBagarViewModel.andarBaharNewSessionApiResult.observe(viewLifecycleOwner, {
            if (it != null) {
                if (it.status) {
                    onSuccessAndarBaharNewSession(it)
                } else {
                    it.message?.let { it1 -> ToastUtils.showShortToast(it1) }
                }
            }
        })

        andarBagarViewModel.andarBaharBuyOrder.observe(viewLifecycleOwner, {
            if (it != null) {
                if (it.status) {
                    onSuccessAndarBaharBuyOrder(it)
                } else {
                    it.message?.let { it1 -> ToastUtils.showShortToast(it1) }
                }
            }
        })

        andarBagarViewModel.andarBaharResultDeckUnfold.observe(viewLifecycleOwner, {
            if (it != null) {
                if (it.status) {
                    onSuccessAndarBaharUpdate(it)
                } else {
                    it.message?.let { it1 -> ToastUtils.showShortToast(it1) }
                }
            }
        })
    }

    private fun onSuccessAndarBaharBuyOrder(responseBuyAndFastParityAndDice: BaseResponse<BuyOrderAnderBaharAndFastParityAndDiceResponse?>?) {
        if (responseBuyAndFastParityAndDice != null) {
            responseBuyAndFastParityAndDice.message?.let { ToastUtils.showShortToast(it) }
            andarBaharBottomSheetDialogForOrder.dismiss()
            val data = JSONObject()
            data.put("status",true)
            data.put("_id", AppConstants.AndarBaharKeys.game_id)
            val jsonResponseDataString = Gson().toJson(responseBuyAndFastParityAndDice.data)
            val jsonResponseDataObject=JSONObject(jsonResponseDataString)
            Log.e(TAG, "onSuccessAndarBaharBuyOrder: "+jsonResponseDataString )
            data.put("data",jsonResponseDataObject)
            SocketUtils.customSocketEmit(AppConstants.SocketKeys.andarBahar,data)
//            customSocketEmit(AppConstants.SocketKeys.andarBahar,data)
            andarBaharBottomMyOrderViewModelAndarBahar.callAndarBaharMyOrders(
                AndarBaharAndFastParityAndDiceMyOrderRequest(
                AppConstants.AndarBaharKeys.game_name
            )
            )
        }
    }

    private fun onSuccessAndarBaharUpdate(sessionResultResponse: BaseResponse<AndarBaharSessionResultResponse?>?) {
        if (sessionResultResponse != null) {
            ToastUtils.showShortToast(sessionResultResponse.message!!)
            showResultToUser(sessionResultResponse.data)
        }
    }

    private fun showAndarSlideAnimation(index:Int,data: AndarBaharSessionResultResponse){
        front_animation.setTarget(binding.deckShowViewConsVAndarBaharFrag2)
        back_animation.setTarget(binding.deckShowViewConsVAndarBaharFrag)
        if (data.deck!=null){
            binding.deckCardShapeImage.setImageDrawable(resources.getDrawable(setOpenCardShape(data.deck[index]?.cardShape),null))

            binding.deckCardNumber.text=data.deck[index]?.cardNo
        }
        front_animation.start()
        back_animation.start()


        pvhXForAndar = PropertyValuesHolder.ofFloat(TRANSLATION_X, (binding.mainAndarCardCVAndarBaharFrag.x-binding.cardViewDeckPlaceHolderCVAndarBaharFrag.x))
        pvhYForAndar = PropertyValuesHolder.ofFloat(TRANSLATION_Y, (binding.mainAndarCardCVAndarBaharFrag.y-binding.cardViewDeckPlaceHolderCVAndarBaharFrag.y))
        objectAnimatorToAndarCard=ObjectAnimator.ofPropertyValuesHolder(binding.cardView2,pvhXForAndar,pvhYForAndar).apply {
            duration=500
            start()
        }
        objectAnimatorToAndarCard.addListener(
            onEnd = {
                if (isFragmentRunning){
                    if (data.deck!=null){
                        if (index!=0)
                            creatHistoryUnfoldedDeck(AppConstants.AndarBaharKeys.andar,andar_history_index,data.deck[index-2]?.cardNo)
                    }
                    pvhXForZero = PropertyValuesHolder.ofFloat(TRANSLATION_X, 0F)
                    pvhYForZero = PropertyValuesHolder.ofFloat(TRANSLATION_Y, 0F)
                    objectAnimatorToZero=ObjectAnimator.ofPropertyValuesHolder(binding.cardView2,pvhXForZero,pvhYForZero).apply {
                        duration=1
                        start()
                    }
                    objectAnimatorToZero.addListener(
                        onEnd = {
                            if (data.deck!=null){
                                binding.andarCardLFAndarBaharFrag.visibility=View.GONE
                                binding.andarCardImageIVAndarBaharFrag.visibility=View.VISIBLE
                                binding.andarCardNumberLFAndarBaharFrag.visibility=View.VISIBLE
                                if (context!=null){
                                    binding.andarCardImageIVAndarBaharFrag.setImageDrawable(resources.getDrawable(setOpenCardShape(data.deck[index]?.cardShape),null))
                                }
                                binding.andarCardNumberLFAndarBaharFrag.text=data.deck[index]?.cardNo
                                when {
                                    data.cardNo == data.deck[index]?.cardNo -> showWinAlertAndStartNewSession(data)
                                    index==data.deck.size-1 -> showWinAlertAndStartNewSession(data)
                                    else -> showBaharSlideAnimation(index+1,data)
                                }

                            }
                        }
                    )
                }


            })

    }

    private fun showBaharSlideAnimation(index:Int,data: AndarBaharSessionResultResponse){
        front_animation.setTarget(binding.deckShowViewConsVAndarBaharFrag2)
        back_animation.setTarget(binding.deckShowViewConsVAndarBaharFrag)
        if (data.deck!=null){
            binding.deckCardShapeImage.setImageDrawable(resources.getDrawable(setOpenCardShape(data.deck[index]?.cardShape),null))
            binding.deckCardNumber.text=data.deck[index]?.cardNo
        }
        front_animation.start()
        back_animation.start()

        pvhXForBahar = PropertyValuesHolder.ofFloat(TRANSLATION_X, (binding.mainBaharCardCVAndarBaharFrag.x-binding.cardViewDeckPlaceHolderCVAndarBaharFrag.x))
        pvhYForBahar = PropertyValuesHolder.ofFloat(TRANSLATION_Y, (binding.mainBaharCardCVAndarBaharFrag.y-binding.cardViewDeckPlaceHolderCVAndarBaharFrag.y))
        objectAnimatorToBaharCard=ObjectAnimator.ofPropertyValuesHolder(binding.cardView2,pvhXForBahar,pvhYForBahar).apply {
            duration = 500
            start()
        }
        objectAnimatorToBaharCard.addListener(
            onEnd = {
                if (isFragmentRunning){
                    if (data.deck!=null){
                        if (index!=1)
                            creatHistoryUnfoldedDeck(AppConstants.AndarBaharKeys.bahar,bahar_history_index,data.deck[index-2]?.cardNo)
                    }
                    pvhXForZero = PropertyValuesHolder.ofFloat(TRANSLATION_X, 0F)
                    pvhYForZero = PropertyValuesHolder.ofFloat(TRANSLATION_Y, 0F)
                    objectAnimatorToZero=ObjectAnimator.ofPropertyValuesHolder(binding.cardView2,pvhXForZero,pvhYForZero).apply {
                        duration=1
                        start()
                    }
                    objectAnimatorToZero.addListener(
                        onEnd = {
                            if (data.deck!=null){
                                binding.baharCardLFAndarBaharFrag.visibility=View.GONE
                                binding.baharCardImageIVAndarBaharFrag.visibility=View.VISIBLE
                                binding.baharCardNumberLFAndarBaharFrag.visibility=View.VISIBLE
                                if (context!=null){
                                    binding.baharCardImageIVAndarBaharFrag.setImageDrawable(resources.getDrawable(setOpenCardShape(data.deck[index]?.cardShape),null))
                                }
                                binding.baharCardNumberLFAndarBaharFrag.text=data.deck[index]?.cardNo
                                when {
                                    data.cardNo == data.deck[index]?.cardNo -> showWinAlertAndStartNewSession(data)
                                    index==data.deck.size-1 -> showWinAlertAndStartNewSession(data)
                                    else -> showAndarSlideAnimation(index+1,data)
                                }
                            }
                        }
                    )
                }

            })
    }

    private fun showResultToUser(data: AndarBaharSessionResultResponse?) {
        if (data!=null){
            if (data.deck!=null){
                var cardShowCountIndex=0
                showAndarSlideAnimation(cardShowCountIndex,data)
            }
        }

    }

    private fun showWinAlertAndStartNewSession(data: AndarBaharSessionResultResponse) {
        binding.andarCardLFAndarBaharFrag.visibility=View.VISIBLE
        binding.andarCardImageIVAndarBaharFrag.visibility=View.GONE
        binding.andarCardNumberLFAndarBaharFrag.visibility=View.GONE
        binding.baharCardLFAndarBaharFrag.visibility=View.VISIBLE
        binding.baharCardImageIVAndarBaharFrag.visibility=View.GONE
        binding.baharCardNumberLFAndarBaharFrag.visibility=View.GONE
        front_animation.setTarget(binding.deckShowViewConsVAndarBaharFrag)
        front_animation.start()
        var answer = data.answer
        if (answer.equals(AppConstants.AndarBaharKeys.A_as_andar))
            answer = "Andar"
        if (answer.equals(AppConstants.AndarBaharKeys.B_as_bahar))
            answer = "Bahar"
        if (answer.equals(AppConstants.AndarBaharKeys.T_as_tie))
            answer = "Tie"
        CustomAlertDialogBox.alertDialogShow(requireContext(), "Winner is $answer")
        clearHistoryDecks()
        andarBagarViewModel.callAndarBaharNewSessionApi()
        andarBaharBottomMyOrderViewModelAndarBahar.callAndarBaharGameResult()
        biddingList?.clear()
        dashboardBottomBiddingListRecyclerAdapter.notifyDataSetChanged()

    }

    private fun clearHistoryDecks() {
        binding.andarShowHistoryLayout.removeAllViews()
        binding.baharShowHistoryLayout.removeAllViews()
        bahar_history_index=0
        andar_history_index=0
    }

    private fun setOpenCardShape(cardShape: String?):Int {
        if (cardShape.equals(AppConstants.CardTypeKeys.clubs_temp,true))
            return R.drawable.club_icon
        if (cardShape.equals(AppConstants.CardTypeKeys.hearts_temp,true))
            return R.drawable.heart_icon
        if (cardShape.equals(AppConstants.CardTypeKeys.dimonds_temp,true))
            return R.drawable.dimond_icon
        if (cardShape.equals(AppConstants.CardTypeKeys.spades_temp,true))
            return R.drawable.spades_icon
        return R.drawable.ic_notifications_black_24dp
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimerSession?.cancel()
        isFragmentRunning=false
        if (this::objectAnimatorToZero.isInitialized){
            if (objectAnimatorToZero.isStarted){
                objectAnimatorToZero.cancel()
            }
            objectAnimatorToZero.removeAllListeners()
        }
        if (this::objectAnimatorToAndarCard.isInitialized){
            if (objectAnimatorToAndarCard.isStarted){
                objectAnimatorToAndarCard.cancel()
            }
            objectAnimatorToAndarCard.removeAllListeners()
        }
        if (this::objectAnimatorToBaharCard.isInitialized){
            if (objectAnimatorToBaharCard.isStarted){
                objectAnimatorToBaharCard.cancel()
            }
            objectAnimatorToBaharCard.removeAllListeners()
        }
        if (this::objectAnimatorToShowCard.isInitialized){
            if (objectAnimatorToShowCard.isStarted){
                objectAnimatorToShowCard.cancel()
            }
            objectAnimatorToShowCard.removeAllListeners()
        }



        if (SocketUtils.mSocket!=null){
            if (SocketUtils.mSocket!!.connected())
                SocketUtils.mSocket!!.off()
        }

    }

    private fun onSuccessAndarBaharNewSession(response: BaseResponse<AndarBaharNewSessionResponse?>?) {
        if (response != null){
            if (response.data!=null){
                binding.deckShowViewConsVAndarBaharFrag.setBackgroundColor(resources.getColor(R.color.main_card_bg))
                pvhXForShow = PropertyValuesHolder.ofFloat(TRANSLATION_X, (binding.mainShowCardCVAndarBaharFrag.x-binding.cardViewDeckPlaceHolderCVAndarBaharFrag.x))
                pvhYForShow = PropertyValuesHolder.ofFloat(TRANSLATION_Y, (binding.mainShowCardCVAndarBaharFrag.y-binding.cardViewDeckPlaceHolderCVAndarBaharFrag.y))
                objectAnimatorToShowCard=ObjectAnimator.ofPropertyValuesHolder(binding.cardView2,pvhXForShow,pvhYForShow).apply {
                    duration = 500
                    start()
                }
                up_animation.setTarget(binding.deckShowViewConsVAndarBaharFrag2)
                down_animation.setTarget(binding.deckShowViewConsVAndarBaharFrag)
                up_animation.start()
                down_animation.start()
                objectAnimatorToShowCard.addListener(
                    onEnd = {
                        if (isFragmentRunning){
                            up_animation.setTarget(binding.deckShowViewConsVAndarBaharFrag)
                            up_animation.start()
                            pvhXForZero = PropertyValuesHolder.ofFloat(TRANSLATION_X, 0F)
                            pvhYForZero = PropertyValuesHolder.ofFloat(TRANSLATION_Y, 0F)
                            objectAnimatorToZero=ObjectAnimator.ofPropertyValuesHolder(binding.cardView2,pvhXForZero,pvhYForZero).apply {
                                duration=1
                                start()
                            }
                            objectAnimatorToZero.addListener(
                                onEnd = {
                                    binding.deckShowViewConsVAndarBaharFrag.setBackgroundColor(resources.getColor(R.color.white))
                                }
                            )
                            binding.periodIdLFAndarBaharFrag.text = response.data.gameSession
                            binding.mainShowCardNumberLFAndarBaharFrag.text = response.data.cardNo
                            binding.ratioAnderLFAndarBaharFrag.text =response.data.ratioAndar
                            binding.ratioTieLFAndarBaharFrag.text =response.data.ratioTie
                            binding.ratioBaharLFAndarBaharFrag.text =response.data.ratioBahar
                            if (response.data.cardShape.equals(AppConstants.CardTypeKeys.clubs_temp, true)) {
                                binding.mainShowCardImageIVAndarBaharFrag.setImageDrawable(
                                    getDrawable(
                                        requireContext(),
                                        R.drawable.club_icon
                                    )
                                )
                            }
                            if (response.data.cardShape.equals(AppConstants.CardTypeKeys.dimonds_temp, true)) {
                                binding.mainShowCardImageIVAndarBaharFrag.setImageDrawable(
                                    getDrawable(
                                        requireContext(),
                                        R.drawable.dimond_icon
                                    )
                                )
                            }
                            if (response.data.cardShape.equals(AppConstants.CardTypeKeys.spades_temp, true)) {
                                binding.mainShowCardImageIVAndarBaharFrag.setImageDrawable(
                                    getDrawable(
                                        requireContext(),
                                        R.drawable.spades_icon
                                    )
                                )
                            }
                            if (response.data.cardShape.equals(AppConstants.CardTypeKeys.hearts_temp, true)) {
                                binding.mainShowCardImageIVAndarBaharFrag.setImageDrawable(
                                    getDrawable(
                                        requireContext(),
                                        R.drawable.heart_icon
                                    )
                                )
                            }
                            if (response.data.createdAt!=null && response.data.gameSession!=null) {
                                setCountDown(response.data.createdAt,response.data.gameSession)
                            }
                        }
                    }
                )
            }

        }

    }

    private fun setCountDown(createdAt: Long,sessionId:String) {
        val currenttimeMilliSecLong: Long = System.currentTimeMillis()
        val currentTimeDate: Date = OtherUtilities.getDate(currenttimeMilliSecLong)
        val serverCreatedAtDate: Date = OtherUtilities.getDate(createdAt)
        val diffInMs: Long = currentTimeDate.time - serverCreatedAtDate.time
        val diffInSec: Long = TimeUnit.MILLISECONDS.toSeconds(diffInMs)
        val milliSecondsInFuture = 60000 - diffInMs
        var secondLeft = TimeUnit.MILLISECONDS.toSeconds(milliSecondsInFuture)
        if (secondLeft<0){

            val dateFormat: String = "dd/MM/yyyy hh:mm:ss.SSS"
            val formatter = SimpleDateFormat(dateFormat,Locale.ENGLISH)
            formatter.timeZone=(TimeZone.getTimeZone("UTC"));
            ToastUtils.showLongToast("Server Time :- "+OtherUtilities.convertToLocalDateFromMilliseconds(serverCreatedAtDate)+"\nDevice Time "+OtherUtilities.convertToLocalDateFromMilliseconds(currentTimeDate))
            return
        }
        countDownTimerSession = object : CountDownTimer(milliSecondsInFuture, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.countDownValueLFAndarBaharFrag.text = "" + secondLeft--
            }

            override fun onFinish() {
                andarBagarViewModel.callAndarBaharResultDeckUnfoldApi(
                    AndarBaharSessionResultRequest(
                    gameSession = sessionId
                )
                )
                binding.countDownValueLFAndarBaharFrag.text = "Over"
            }
        }.start()
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.mainShowCardCVAndarBaharFrag -> {


                if (isUp){
                    up_animation.setTarget(binding.deckShowViewConsVAndarBaharFrag)
                    down_animation.setTarget(binding.deckShowViewConsVAndarBaharFrag2)
                    up_animation.start()
                    down_animation.start()
                    isUp=!isUp
                }else{
                    up_animation.setTarget(binding.deckShowViewConsVAndarBaharFrag2)
                    down_animation.setTarget(binding.deckShowViewConsVAndarBaharFrag)
                    up_animation.start()
                    down_animation.start()
                    isUp=!isUp
                }
            }

            binding.mainBaharCardCVAndarBaharFrag -> {


            }

            binding.mainBuyAndarCVAndarBaharFrag -> {
                showBottomSheet(AppConstants.AndarBaharKeys.andar)
            }
            binding.mainBuyBaharCVAndarBaharFrag -> {
                showBottomSheet(AppConstants.AndarBaharKeys.bahar)
            }
            binding.mainBuyTieCVAndarBaharFrag -> {
                showBottomSheet(AppConstants.AndarBaharKeys.tie)
            }
        }
    }

    private fun creatHistoryUnfoldedDeck(type:String, index: Int,cardNo:String?) {
        Log.e(TAG, "creatHistoryUnfoldedDeck: unfoald", )
        if (isFragmentRunning){
            var cardView = CardView(requireContext())
            cardView.elevation = 20F
            cardView.radius = 20F
            val layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 100
            )
            layoutParams.gravity = Gravity.CENTER
            layoutParams.setMargins(0, 60 * index, 0, 0)
            cardView.layoutParams = layoutParams

            var textView = TextView(requireContext())
            textView.text = "" + cardNo
            textView.textSize = 18F
            textView.setTypeface(null, Typeface.BOLD);
            textView.gravity = Gravity.CENTER_HORIZONTAL

            cardView.addView(textView)

            if (type==AppConstants.AndarBaharKeys.andar){
                binding.andarShowHistoryLayout.addView(cardView)
                andar_history_index+=1
            }else if (type==AppConstants.AndarBaharKeys.bahar){
                binding.baharShowHistoryLayout.addView(cardView)
                bahar_history_index+=1
            }
        }

    }

    private fun showBottomSheet(type: String) {
        var option=""
        if (type.equals(AppConstants.AndarBaharKeys.andar))
            option=AppConstants.AndarBaharKeys.A_as_andar
        if (type.equals(AppConstants.AndarBaharKeys.bahar))
            option=AppConstants.AndarBaharKeys.B_as_bahar
        if (type.equals(AppConstants.AndarBaharKeys.tie))
            option=AppConstants.AndarBaharKeys.T_as_tie

        val bottomSheeetBinding =
            AndarBaharOrderBottomSheetLayoutBinding.inflate(layoutInflater, null, false)
        andarBaharBottomSheetDialogForOrder.setContentView(bottomSheeetBinding.root)
        andarBaharBottomSheetDialogForOrder.show()

        bottomSheeetBinding.typeLFBottomAndarBaharFrag.text = type
        bottomSheeetBinding.buyBBottomAndarBaharFrag.setOnClickListener {
            andarBagarViewModel.callAndarBaharBuyOrderApi(
                BuyOrderAnderBaharAndFastParityAndDiceRequest(
                    amount = bottomSheeetBinding.amountLFBottomAndarBaharFrag.text.toString(),
                    option = option,
                    gameSession = binding.periodIdLFAndarBaharFrag.text.toString(),
                    avatar = "",
                    gameName = AppConstants.AndarBaharKeys.game_name
                )
            )
        }
    }

}