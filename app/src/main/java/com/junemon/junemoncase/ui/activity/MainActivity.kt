package com.junemon.junemoncase.ui.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ian.app.helper.util.fullScreenAnimation
import com.ian.app.helper.util.switchFragment
import com.junemon.junemoncase.R
import com.junemon.junemoncase.ui.fragment.home.HomeFragment
import com.junemon.junemoncase.ui.fragment.orderbucketlist.BucketListFragment
import com.junemon.junemoncase.ui.fragment.profile.ProfileFragment
import com.junemon.junemoncase.util.Constant
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreenAnimation()
        setContentView(R.layout.activity_main)
        initBottomNav()
        moveToSpesificFragment(intent?.getStringExtra(Constant.switchBackToMain))
    }

    private fun moveToSpesificFragment(dataCallback: String?) {
        when {
            dataCallback != null && dataCallback.contentEquals("1") -> {
                supportFragmentManager.switchFragment(null, R.id.main_container, HomeFragment())
                bottom_navigation.selectedItemId = R.id.navigation_home
            }

            dataCallback != null && dataCallback.contentEquals("4") -> {
                supportFragmentManager.switchFragment(null, R.id.main_container, ProfileFragment())
                bottom_navigation.selectedItemId = R.id.navigation_profile
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.navigation_home -> {
                supportFragmentManager.switchFragment(null, R.id.main_container, HomeFragment())
                true
            }

            R.id.navigation_order -> {
                supportFragmentManager.switchFragment(null, R.id.main_container, BucketListFragment())
//                startActivity<UploadActivity>().also {
//                    finish()
//                }
                true
            }

            R.id.navigation_profile -> {
                supportFragmentManager.switchFragment(null, R.id.main_container, ProfileFragment())
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun initBottomNav() {
        bottom_navigation.setOnNavigationItemSelectedListener(this)
        supportFragmentManager.beginTransaction().replace(R.id.main_container, HomeFragment())
            .commit()
    }
}
