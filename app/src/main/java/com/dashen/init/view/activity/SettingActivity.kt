package com.dashen.init.view.activity

import android.view.View
import com.dashen.init.R
import com.dashen.init.base.BaseActivity
import com.dashen.init.common.constant.Constant
import com.dashen.init.common.network.appUpdate.AppDownloadManager
import com.dashen.init.common.network.appUpdate.HProgressDialogUtils
import com.dashen.init.common.newNetwork.model.UpdateBean
import com.dashen.init.common.newNetwork.request.VersionUpdateRequest
import com.dashen.init.common.utils.SharedPreferencesUtils
import com.dashen.init.common.utils.SystemUtil
import com.dashen.init.presenter.SettingHelper
import com.dashen.init.presenter.viewinter.SettingView
import com.dashen.utils.LogUtils
import kotlinx.android.synthetic.main.layout_setting.*

/**
 * project_name:   init
 * package_name:   com.dashen.init.view.activity
 * author:   beibei
 * create_time:    2018/9/17 17:22
 * class_desc:  设置页面
 * remarks:
 */
class SettingActivity : BaseActivity(), SettingView {
    private var mHelper: SettingHelper? = null//MainActivity业务处理类
    private lateinit var mDownloadManager: AppDownloadManager

    override val layoutId: Int
        get() = R.layout.layout_setting

    override fun initView() {
        initHeadView("设置")

        mHelper = SettingHelper(this, this, lifecycle)
        mDownloadManager = AppDownloadManager(this)
        ll_version.setOnClickListener(this)
    }

    override fun initData() {

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.ll_version -> {
                mHelper?.getUpdateInfo(VersionUpdateRequest(SystemUtil.getAppVersionName(this)))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (mDownloadManager != null) {
            mDownloadManager.resume()
        }
    }

    override fun onPause() {
        super.onPause()
        if (mDownloadManager != null) {
            mDownloadManager.onPause()
        }
    }

    override fun getUpdateInfoSuccess(it: UpdateBean) {
        mDownloadManager.downloadApk(it.downloadUrl, getString(R.string.app_name), "")
        HProgressDialogUtils.showHorizontalProgressDialog(this, "下载进度", false)
        mDownloadManager.setUpdateListener { currentByte, totalByte ->
            HProgressDialogUtils.setProgress(Math.round((currentByte.toFloat() / totalByte) * 100))
            LogUtils.e("--------------" + Math.round((currentByte.toFloat() / totalByte) * 100))
            if (currentByte == totalByte) {
                HProgressDialogUtils.cancel()
            }
        }
    }

    override fun getUpdateInfoMessage(errorCode: Int, msg: String) {
    }
}