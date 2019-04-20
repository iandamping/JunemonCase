package com.junemon.junemoncase.ui.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.junemon.junemoncase.R
import com.junemon.junemoncase.ui.activity.upload.UploadActivity
import com.junemon.junemoncase.ui.fragment.home.HomeFragment
import com.junemon.junemoncase.ui.fragment.profile.ProfileFragment
import com.junemon.junemoncase.util.Constant
import com.junemon.junemoncase.util.startActivity
import com.junemon.junemoncase.util.switchFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initBottomNav()
        moveToSpesificFragment(intent?.getStringExtra(Constant.switchBackToMain))
    }

    private fun moveToSpesificFragment(dataCallback: String?) {
        if (dataCallback != null && dataCallback.contentEquals("1")) {
            supportFragmentManager.switchFragment(null, HomeFragment())
            bottom_navigation.selectedItemId = R.id.navigation_home
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.navigation_home -> {
                supportFragmentManager.switchFragment(null, HomeFragment())
                true
            }

            R.id.navigation_order -> {
                startActivity<UploadActivity>().also {
                    finish()
                }
                true
            }

            R.id.navigation_profile -> {
                supportFragmentManager.switchFragment(null, ProfileFragment())
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
