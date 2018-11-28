package com.beibei.init.view.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.beibei.init.R
import com.beibei.init.base.BaseActivity
import com.beibei.init.common.utils.ActivityManagerUtils
import com.dashen.utils.LogUtils
import kotlinx.android.synthetic.main.activity_webview.*
import kotlinx.android.synthetic.main.head_view1.*

/**
 * author: anbeibei
 *
 * date: 2018/4/18
 *
 * eventDesc: h5页面
 */

class WebViewActivity : BaseActivity() {
    override val layoutId: Int
        get() = R.layout.activity_webview

    /**
     * 初始化控件
     */
    @SuppressLint("SetJavaScriptEnabled")
    override fun initView() {
//        StatusBarUtil.setColor(this, 0, null, false)
        tv_head_title.text = "内容详情"
//        tv_head_title.text = intent.extras?.getString("title", "内容详情")
        iv_head_back.setOnClickListener(this)

        web_view!!.settings.javaScriptEnabled = true
        //自适应屏幕
        web_view!!.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        web_view!!.settings.loadWithOverviewMode = true
        web_view!!.settings.domStorageEnabled = true// 开启 DOM storage API 功能
        web_view!!.settings.databaseEnabled = true
        web_view!!.settings.setAppCacheEnabled(true)
        //设置可以访问文件
        web_view!!.settings.allowFileAccess = true

    }


    /**
     * 初始化数据
     */
    override fun initData() {
        //        int randomNum = (int) (Math.random() * 100);
//        val url = intent.extras?.getString("url","http://www.galaxyclub.cn/exchange")
        val url = "http://www.galaxyclub.cn/exchange"
        //            url = getIntent().getExtras().getString("url") + "?&" + randomNum;
        LogUtils.d("----WebViewActivity--url>" + url!!)
        web_view!!.visibility = View.VISIBLE
        web_view!!.loadUrl(url)
        //设置Web视图
        web_view!!.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean { //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                if (url.startsWith("http:") || url.startsWith("https:")) {
                    view.loadUrl(url)
                    return false
                }
                if (url.startsWith("tel:")) { //"tel:4120-6612345"
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse(url))
                    startActivity(intent)
                }
                return true
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                LogUtils.d("---onPageFinished->")
            }

            override fun onReceivedError(view: WebView, errorCode: Int, description: String, failingUrl: String) {
                super.onReceivedError(view, errorCode, description, failingUrl)
                LogUtils.e("-----errorcode---->$errorCode/description------->$description")
            }
        }
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.iv_head_back -> {
                if (web_view!!.canGoBack()) {
                    // 返回键退回
                    web_view!!.goBack()
                } else {
                    ActivityManagerUtils.instance.killActivity(this)
                }
            }

        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        // Check if the key event was the Back button and if there's history
        if (keyCode == KeyEvent.KEYCODE_BACK && web_view!!.canGoBack()) {
            // 返回键退回
            web_view!!.goBack()
            return true
        } else {
            onBackPressed()
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (web_view != null) {
            val parent = web_view.parent
            if (parent != null) {
                (parent as ViewGroup).removeView(web_view)
            }
            web_view.removeAllViews()
            //上面代码是使用destroy()后无法反注册mContext.unregisterComponentCallbacks(mComponentCallbacks)
            web_view.clearCache(true)
            web_view.destroy()// 窗体销毁，要webview销毁；考虑安全问题和内存溢出
        }
    }
}
