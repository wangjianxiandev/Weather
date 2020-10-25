package com.wjx.android.weather.module.about

import android.content.SharedPreferences

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.navigation.Navigation
import androidx.preference.Preference
import com.wjx.android.weather.R
import androidx.preference.PreferenceFragmentCompat
import com.wjx.android.weather.common.checkUpdate
import com.wjx.android.weather.module.main.view.MainActivity
import kotlinx.android.synthetic.main.custom_bar.view.*

class AboutFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var parentActivity: MainActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //这里重写根据PreferenceFragmentCompat 的布局 ，往他的根布局插入了一个toolbar
        val containerView = view.findViewById<FrameLayout>(android.R.id.list_container)
        containerView.let {
            //转为线性布局
            val linearLayout = it.parent as? LinearLayout
            linearLayout?.run {
                val toolbarView =
                    LayoutInflater.from(activity).inflate(R.layout.custom_bar, null)
                toolbarView.detail_start.setOnClickListener {
                    Navigation.findNavController(it).navigateUp()
                }
                toolbarView.detail_end.visibility = View.INVISIBLE
                toolbarView.detail_title.text = "设置"
                //添加到第一个
                addView(toolbarView, 0)
            }
        }

    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.setting_fragment)
        parentActivity = activity as MainActivity
        init()
    }


    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }


    private fun init() {
        val version = "当前版本 " + parentActivity.packageManager.getPackageInfo(
            parentActivity.packageName,
            0
        ).versionName

        findPreference<Preference>("version")?.setOnPreferenceClickListener {
            checkUpdate(parentActivity, true)
            false
        }

        findPreference<Preference>("csdn")?.setOnPreferenceClickListener {
            view?.let {
                if (Navigation.findNavController(it).currentDestination?.id == R.id.aboutFragment) {
                    Navigation.findNavController(it)
                        .navigate(R.id.action_aboutFragment_to_webFragment, Bundle().apply {
                            putString("title", "DLUT_WJX")
                            putString("url", "https://blog.csdn.net/qq_39424143")
                        })
                }
            }
            false
        }

        findPreference<Preference>("project")?.setOnPreferenceClickListener {
            view?.let {
                if (Navigation.findNavController(it).currentDestination?.id == R.id.aboutFragment) {
                    Navigation.findNavController(it)
                        .navigate(R.id.action_aboutFragment_to_webFragment, Bundle().apply {
                            putString("title", "Weather")
                            putString("url", "https://github.com/wangjianxiandev/Weather")
                        })
                }
            }
            false
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
    }
}