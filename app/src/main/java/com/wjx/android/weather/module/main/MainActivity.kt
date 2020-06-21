package com.wjx.android.weather.module.main

import android.Manifest
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.wjx.android.weather.R
import com.wjx.android.weather.base.view.BaseLifeCycleActivity
import com.wjx.android.weather.common.checkUpdate
import com.wjx.android.weather.common.permission.PermissionResult
import com.wjx.android.weather.common.permission.Permissions
import com.wjx.android.weather.databinding.ActivityMainBinding
import pub.devrel.easypermissions.AppSettingsDialog

class MainActivity : BaseLifeCycleActivity<AppViewModel, ActivityMainBinding>() {
    private val mPermissions = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.INTERNET,
        Manifest.permission.ACCESS_NETWORK_STATE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    override fun getLayoutId() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkUpdate(this, false)
        initPermission()
    }

    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.nav_main).navigateUp()
    }

    private fun initPermission() {
        Permissions(this).request(*mPermissions).observe(
            this, Observer {
                when (it) {
                    is PermissionResult.Grant -> {

                    }
                    // 进入设置界面申请权限
                    is PermissionResult.Rationale -> {
                        AppSettingsDialog.Builder(this)
                            .setTitle("申请权限")
                            .setRationale("没有相关权限应用将无法正常运行，点击确定进入权限设置界面来进行更改")
                            .build()
                            .show()
                        finish()
                    }
                    // 进入设置界面申请权限
                    is PermissionResult.Deny -> {
                        AppSettingsDialog.Builder(this)
                            .setTitle("申请权限")
                            .setRationale("没有相关权限应用将无法正常运行，点击确定进入权限设置界面来进行更改")
                            .build()
                            .show()
                        finish()
                    }
                }
            }
        )
    }
}