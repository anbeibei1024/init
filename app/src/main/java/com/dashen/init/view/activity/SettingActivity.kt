package com.dashen.init.view.activity

import android.view.View
import com.dashen.init.R
import com.dashen.init.base.BaseActivity
import com.dashen.init.common.network.appUpdate.AppDownloadManager
import com.dashen.init.common.newNetwork.model.UpdateBean
import com.dashen.init.common.newNetwork.request.VersionUpdateRequest
import com.dashen.init.common.utils.SystemUtil
import com.dashen.init.presenter.SettingHelper
import com.dashen.init.presenter.viewinter.SettingView
import com.dashen.utils.ToastUtils
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
        tv.setOnClickListener(this)
    }

    override fun initData() {
        mHelper?.getUpdateInfo(VersionUpdateRequest(SystemUtil.getAppVersionName(this)))
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv -> {

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
        ToastUtils.showToast(this, "要更新")
        mDownloadManager.downloadApk(it.downloadUrl, "titleAA", "desc")
//        if (it.isUpdate == "1") {
//            tv_version_hint.text = "发现新版本"
//            view_point.visibility = View.VISIBLE
//        } else {
//            tv_version_hint.text = ""
//            view_point.visibility = View.GONE
//        }
//        mUpdateBean = it
    }

//    override fun getUpdateInfoMessage(errorCode: Int, msg: String) {
//        DialogUtils.stopLoading()
//    }
}