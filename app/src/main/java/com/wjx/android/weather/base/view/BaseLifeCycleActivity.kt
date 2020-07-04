package com.wjx.android.weather.base.view


import android.text.TextUtils
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.kingja.loadsir.callback.SuccessCallback
import com.wjx.android.wanandroidmvvm.common.callback.*
import com.wjx.android.weather.base.viewmodel.BaseViewModel
import com.wjx.android.weather.common.getAppViewModel
import com.wjx.android.weather.common.state.State
import com.wjx.android.weather.common.state.StateType
import com.wjx.android.weather.module.app.AppViewModel

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/3 22:51
 */
abstract class BaseLifeCycleActivity<VM : BaseViewModel<*>, DB : ViewDataBinding> :
    BaseActivity<VM, DB>() {

    val appViewModel: AppViewModel by lazy { getAppViewModel() }

    override fun initView() {
        showSuccess()
        mViewModel.loadState.observe(this, observer)
        initDataObserver()
    }

    open fun initDataObserver() {}


    open fun showLoading() {
        loadService.showCallback(LoadingCallBack::class.java)
    }

    open fun showSuccess() {
        loadService.showCallback(SuccessCallback::class.java)
    }

    open fun showError(msg: String) {
        if (!TextUtils.isEmpty(msg)) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
        loadService.showCallback(ErrorCallBack::class.java)
    }

    open fun showEmpty() {
        loadService.showCallback(EmptyCallBack::class.java)
    }

    /**
     * 分发应用状态
     */
    private val observer by lazy {
        Observer<State> {
            it?.let {
                when (it.code) {
                    StateType.SUCCESS -> showSuccess()
                    StateType.LOADING -> showLoading()
                    StateType.ERROR -> showError("网络出现问题啦")
                    StateType.NETWORK_ERROR -> showError("网络出现问题啦")
                    StateType.EMPTY -> showEmpty()
                }
            }
        }
    }
}