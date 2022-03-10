package com.co.wewin.viewModels.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.co.wewin.utilities.network.ApiDataSource
import com.co.wewin.viewModels.andarBahar.AndarBaharViewModel
import com.co.wewin.viewModels.andarBahar.AndarBaharBaseFragmentViewModel
import com.co.wewin.viewModels.dice.DiceBaseFragmentViewModel
import com.co.wewin.viewModels.dice.DiceViewModel
import com.co.wewin.viewModels.fastParity.FastParityBaseFragmentViewModel
import com.co.wewin.viewModels.fastParity.FastParityViewModel
import com.co.wewin.viewModels.postLogin.CommonProfileUpdateViewModel
import com.co.wewin.viewModels.postLogin.DashboardGameListViewModle
import com.co.wewin.viewModels.postLogin.MyProfileViewModel
import com.co.wewin.viewModels.postLogin.PostLoginActivityViewModel
import com.co.wewin.viewModels.preLogin.LoginViewModel
import com.co.wewin.viewModels.preLogin.RegisterViewModel

class WeWinViewModelFactory:ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(LoginViewModel::class.java)->{
                LoginViewModel(ApiDataSource()) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java)->{
                RegisterViewModel(ApiDataSource()) as T
            }
            modelClass.isAssignableFrom(AndarBaharViewModel::class.java)->{
                AndarBaharViewModel(ApiDataSource()) as T
            }
            modelClass.isAssignableFrom(DashboardGameListViewModle::class.java)->{
                DashboardGameListViewModle(ApiDataSource()) as T
            }
            modelClass.isAssignableFrom(AndarBaharBaseFragmentViewModel::class.java)->{
                AndarBaharBaseFragmentViewModel(ApiDataSource()) as T
            }
            modelClass.isAssignableFrom(FastParityViewModel::class.java)->{
                FastParityViewModel(ApiDataSource()) as T
            }
            modelClass.isAssignableFrom(FastParityBaseFragmentViewModel::class.java)->{
                FastParityBaseFragmentViewModel(ApiDataSource()) as T
            }
            modelClass.isAssignableFrom(DiceViewModel::class.java)->{
                DiceViewModel(ApiDataSource()) as T
            }
            modelClass.isAssignableFrom(DiceBaseFragmentViewModel::class.java)->{
                DiceBaseFragmentViewModel(ApiDataSource()) as T
            }
            modelClass.isAssignableFrom(CommonProfileUpdateViewModel::class.java)->{
                CommonProfileUpdateViewModel(ApiDataSource()) as T
            }
            modelClass.isAssignableFrom(MyProfileViewModel::class.java)->{
                MyProfileViewModel(ApiDataSource()) as T
            }
            modelClass.isAssignableFrom(PostLoginActivityViewModel::class.java)->{
                PostLoginActivityViewModel(ApiDataSource()) as T
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}