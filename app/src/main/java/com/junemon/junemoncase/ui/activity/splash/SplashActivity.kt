package com.junemon.junemoncase.ui.activity.splash

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.ian.app.helper.util.fullScreenAnimation
import com.ian.app.helper.util.startActivity
import com.junemon.junemoncase.R
import com.junemon.junemoncase.ui.activity.MainActivity
import com.junemon.junemoncase.util.Constant.delayMillis

/**
 *
Created by Ian Damping on 15/04/2019.
Github = https://github.com/iandamping
 */
class SplashActivity : AppCompatActivity() {
    private var mDelayHandler: Handler? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreenAnimation()
        setContentView(R.layout.activity_splash)
        mDelayHandler = Handler()
        mDelayHandler!!.postDelayed(mRunnable, delayMillis)
    }

    private val mRunnable: Runnable = Runnable {
        if (!isFinishing) {
            startActivity<MainActivity>().also {
                finish()
            }
        }
    }


    public override fun onDestroy() {
        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }

        super.onDestroy()
    }
}