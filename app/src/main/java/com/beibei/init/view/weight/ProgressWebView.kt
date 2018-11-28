package com.beibei.init.view.weight

import android.R
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.AbsoluteLayout
import android.widget.ProgressBar

/**
 * author: anbeibei
 *
 * date: 2018/4/18
 *
 * eventDesc:带进度条的WebView
 */

class ProgressWebView(context: Context, attrs: AttributeSet) : WebView(context, attrs) {
    private val progressbar: ProgressBar = ProgressBar(context, null, R.attr.progressBarStyleHorizontal)

    init {
        progressbar.layoutParams = AbsoluteLayout.LayoutParams(AbsoluteLayout.LayoutParams.MATCH_PARENT, 5, 0, 0)
        addView(progressbar)
        webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                if (newProgress == 100) {
                    progressbar.visibility = View.GONE
                } else {
                    if (progressbar.visibility == View.GONE) {
                        progressbar.visibility = View.VISIBLE
                    }
                    progressbar.progress = newProgress
                }
                super.onProgressChanged(view, newProgress)
            }
        }
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        val lp = progressbar.layoutParams as AbsoluteLayout.LayoutParams
        lp.x = l
        lp.y = t
        progressbar.layoutParams = lp
        super.onScrollChanged(l, t, oldl, oldt)
    }
}