package com.co.wewin.utilities

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.navOptions
import com.co.wewin.views.PreLoginActivity
import com.co.wewin.R
import com.co.wewin.views.PostLoginActivity

class NavigationUtils {
    companion object{
        fun  goToFragment(activity: Activity,bundle: Bundle=Bundle(),navigateToId:Int){
            var navController:NavController?=null
            if (activity is PostLoginActivity){
                 navController=activity.findNavController(R.id.nav_host_fragment_post_login)
            }else if (activity is PreLoginActivity){
                 navController=activity.findNavController(R.id.nav_host_fragment_pre_login)
            }
            if (bundle.isEmpty){
                navController!!.navigate(navigateToId,null,setTransition())
            }else{
                navController!!.navigate(navigateToId,bundle,setTransition())
            }
        }

//        fun setUpInitialPreLoginScreen(activity: Activity,navigateToId:Int){
//            activity.findNavController(R.id.nav_host_fragment_post_login)
//        }

        fun popBackStack(activity: Activity){
            activity.onBackPressed()
        }

        fun changeActivity(fromActivity:Activity,toActivityClass:Class<*>){
            fromActivity.startActivity(Intent(fromActivity.applicationContext,toActivityClass))

            /* to call from other plase
            NavigationUtils.changeActivity(requireActivity(),PostLoginActivity::class.java)
             */
        }

        private fun setTransition(): NavOptions {
            return navOptions {
                anim {
                    enter = R.anim.slide_in_right
                    exit = R.anim.slide_out_left
                    popEnter = R.anim.slide_in_left
                    popExit = R.anim.slide_out_right
                }
            }
        }
    }
}