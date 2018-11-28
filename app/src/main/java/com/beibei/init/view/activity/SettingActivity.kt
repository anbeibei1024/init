package com.beibei.init.view.activity

import android.view.View
import com.beibei.appupdate.AppDownloadManager
import com.beibei.appupdate.HProgressDialogUtils
import com.beibei.init.R
import com.beibei.init.base.BaseActivity
import com.beibei.init.common.newNetwork.model.UpdateBean
import com.beibei.init.common.newNetwork.request.VersionUpdateRequest
import com.beibei.init.common.utils.SystemUtil
import com.beibei.init.presenter.SettingHelper
import com.beibei.init.presenter.viewinter.SettingView
import com.dashen.utils.LogUtils
import kotlinx.android.synthetic.main.layout_setting.*

/**
 * project_name:   init
 * package_name:   com.beibei.init.view.activity
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
        ll_version.setOnClickListener(this)

        mDownloadManager = AppDownloadManager(this)
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

    override fun onDestroy() {
        super.onDestroy()
        if (mDownloadManager != null) {
            mDownloadManager.onDestroy()
        }
    }

    override fun getUpdateInfoSuccess(it: UpdateBean) {
        if (!mDownloadManager.existNewVersionApk()) {
            mDownloadManager.downloadStart(it.downloadUrl, getString(R.string.app_name), "下载完成点击安装")
            HProgressDialogUtils.showHorizontalProgressDialog(this, "下载进度", false)
            mDownloadManager.setUpdateListener { currentByte, totalByte ->
                HProgressDialogUtils.setProgress(Math.round((currentByte.toFloat() / totalByte) * 100))
                LogUtils.e("--------------" + Math.round((currentByte.toFloat() / totalByte) * 100))
                if (currentByte == totalByte) {
                    HProgressDialogUtils.cancel()
                }
            }
        }
    }

    override fun getUpdateInfoMessage(errorCode: Int, msg: String) {
    }
}