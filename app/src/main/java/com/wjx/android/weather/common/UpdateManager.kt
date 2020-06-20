package com.wjx.android.weather.common

import android.app.ProgressDialog
import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.pgyersdk.update.DownloadFileListener
import com.pgyersdk.update.PgyUpdateManager
import com.pgyersdk.update.UpdateManagerListener
import com.pgyersdk.update.javabean.AppBean
import com.wjx.android.weather.R
import java.io.File

fun checkUpdate(context: Context, isShowToast: Boolean) {
    val dialog = ProgressDialog(context)
    PgyUpdateManager.Builder()
        .setForced(false) //设置是否强制提示更新,非自定义回调更新接口此方法有用
        .setUserCanRetry(false) //失败后是否提示重新下载，非自定义下载 apk 回调此方法有用
        .setDeleteHistroyApk(false) // 检查更新前是否删除本地历史 Apk， 默认为true
        .setUpdateManagerListener(object : UpdateManagerListener {
            override fun onNoUpdateAvailable() {
                //没有更新是回调此方法
                if (isShowToast) {
                    Toast.makeText(context, R.string.no_version, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onUpdateAvailable(appBean: AppBean) {
                //有更新回调此方法
                //调用以下方法，DownloadFileListener 才有效；
                //如果完全使用自己的下载方法，不需要设置DownloadFileListener
                AlertDialog.Builder(context)
                    .setMessage(context.getString(R.string.new_version) + appBean.versionName)
                    .setNegativeButton(R.string.cancel) { _, _ -> }
                    .setPositiveButton(R.string.done) { _, _ ->
                        PgyUpdateManager.downLoadApk(appBean.downloadURL)
                    }.show()
            }

            override fun checkUpdateFailed(e: Exception) {
                //更新检测失败回调
                //更新拒绝（应用被下架，过期，不在安装有效期，下载次数用尽）以及无网络情况会调用此接口
                if (isShowToast) {
                    Toast.makeText(context, R.string.check_update_fail, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }) //注意 ：
        //下载方法调用 PgyUpdateManager.downLoadApk(appBean.getDownloadURL()); 此回调才有效
        //此方法是方便用户自己实现下载进度和状态的 UI 提供的回调
        //想要使用蒲公英的默认下载进度的UI则不设置此方法
        .setDownloadFileListener(object : DownloadFileListener {
            override fun downloadFailed() {
                //下载失败
                if (isShowToast) {
                    Toast.makeText(context, R.string.down_load_fail, Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onProgressUpdate(vararg args: Int?) {
                dialog.setTitle(context.getString(R.string.updating))
                dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
                dialog.progress = if (args.get(0) == null) 0 else args.get(0)!!
                dialog.show()
            }

            override fun downloadSuccessful(file: File?) {
                // 使用蒲公英提供的安装方法提示用户 安装apk
                dialog.dismiss()
                PgyUpdateManager.installApk(file)
            }
        })
        .register()
}