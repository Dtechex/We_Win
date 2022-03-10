package com.co.wewin.views

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.co.wewin.R
import com.co.wewin.databinding.ActivityPostLoginBinding
import com.co.wewin.utilities.NavigationUtils
import com.co.wewin.utilities.ToastUtils
import com.co.wewin.utilities.progressUtils.SocketUtils
import com.co.wewin.viewModels.factory.WeWinViewModelFactory
import com.co.wewin.viewModels.postLogin.PostLoginActivityViewModel
import android.os.Looper

import android.widget.Toast




/**
 * @author Vikas Sharma
 */

class PostLoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityPostLoginBinding
    lateinit var postLoginActivityViewModel:PostLoginActivityViewModel
    var bottomHome=true
    var bottomInvite=true
    var bottomRecharge=true
    var bottomMy=true
    var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setViewModel()
        binding = ActivityPostLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        SocketUtils.setUpSocket(this)
        var navController = findNavController(R.id.nav_host_fragment_post_login)
        binding.bottomNav.isItemHorizontalTranslationEnabled=true
        binding.bottomNav.setupWithNavController(navController)
        binding.bottomNav.setOnItemSelectedListener{ item ->
            when(item.itemId){
                R.id.home->{
                    if (bottomHome){
                        NavigationUtils.goToFragment(activity = this, navigateToId = R.id.dashboardFragment)
                        bottomHome=false
                         bottomInvite=true
                         bottomRecharge=true
                         bottomMy=true
                    }
                    return@setOnItemSelectedListener true
                }
                R.id.invite->{
                    if (bottomInvite){
                        ToastUtils.showShortToast("invite")
                        bottomHome=true
                        bottomInvite=false
                        bottomRecharge=true
                        bottomMy=true
                    }
                    return@setOnItemSelectedListener true
                }
                R.id.recharge->{
                    if (bottomRecharge){
                        ToastUtils.showShortToast("recharge")
                        bottomHome=true
                        bottomInvite=true
                        bottomRecharge=false
                        bottomMy=true
                    }
                    return@setOnItemSelectedListener true
                }
                R.id.my->{
                    if (bottomMy){
                        NavigationUtils.goToFragment(activity = this, navigateToId = R.id.myProfileFragment)
                        bottomHome=true
                        bottomInvite=true
                        bottomRecharge=true
                        bottomMy=false
                    }
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    private fun setViewModel() {
        postLoginActivityViewModel = ViewModelProvider(this, WeWinViewModelFactory()).get(
            PostLoginActivityViewModel::class.java
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        if (SocketUtils.mSocket!=null){
            if (SocketUtils.mSocket!!.connected())
                SocketUtils.mSocket!!.disconnect()
        }
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            doubleBackToExitPressedOnce = false
        }, 2000)
    }
}