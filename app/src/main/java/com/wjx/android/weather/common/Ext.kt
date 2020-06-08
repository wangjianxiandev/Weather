package com.wjx.android.weather.common

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wjx.android.weather.base.repository.BaseRepository
import com.wjx.android.weather.base.viewmodel.BaseViewModel
import com.wjx.android.weather.common.state.State
import com.wjx.android.weather.network.NetExceptionHandle
import kotlinx.coroutines.launch

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/8 21:30
 */
fun <T : BaseRepository> BaseViewModel<T>.initiateRequest(
    block: suspend () -> Unit,
    loadState: MutableLiveData<State>
) {
    viewModelScope.launch {
        runCatching {
            block()
            Log.d("WJX", "success")
        }.onSuccess {
            Log.d("WJX", "success1")
        }.onFailure {
            Log.d("WJX", "fail")
            NetExceptionHandle.handleException(it, loadState)
        }
    }
}