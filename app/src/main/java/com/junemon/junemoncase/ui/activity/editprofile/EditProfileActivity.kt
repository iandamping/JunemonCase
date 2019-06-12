package com.junemon.junemoncase.ui.activity.editprofile

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.ian.app.helper.util.fullScreenAnimation
import com.ian.app.helper.util.logE
import com.ian.app.helper.util.requestError
import com.ian.app.helper.util.startActivity
import com.junemon.junemoncase.JunemonApps.Companion.userDatabaseReference
import com.junemon.junemoncase.R
import com.junemon.junemoncase.model.GeneralProvinceModel
import com.junemon.junemoncase.model.UserProfileModel
import com.junemon.junemoncase.ui.activity.MainActivity
import com.junemon.junemoncase.util.Constant
import com.junemon.junemoncase.util.alertHelperToLoginActivity
import kotlinx.android.synthetic.main.activity_edit_profile.*
import org.jetbrains.anko.selector

/**
 *
Created by Ian Damping on 26/04/2019.
Github = https://github.com/iandamping
 */
class EditProfileActivity : AppCompatActivity(), EditProfileView {
    private lateinit var userData: UserProfileModel
    private lateinit var presenter: EditProfilePresenter
    private var autoTextProvinceAdapter: ArrayAdapter<String>? = null
    private var autoTextCityAdapter: ArrayAdapter<String>? = null
    private var currentUserId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreenAnimation()
        setContentView(R.layout.activity_edit_profile)
        presenter = EditProfilePresenter(userDatabaseReference).apply {
            attachView(this@EditProfileActivity, this@EditProfileActivity)
            onCreate()
        }
    }

    override fun onGetProvinceData(data: List<GeneralProvinceModel>?) {
        val tmpProvinceData: MutableList<String> = mutableListOf()
        tmpProvinceData.clear()
        data?.forEach { province ->
            province.provinceName?.let { notNullProvince -> tmpProvinceData.add(notNullProvince) }
        }
        autoTextProvinceAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, tmpProvinceData)
        etEditProvince.setAdapter(autoTextProvinceAdapter)
        etEditProvince.threshold = 1
        selectorEditProvince.setOnClickListener {
            selector(getString(R.string.pilih_kabupaten), tmpProvinceData) { _, i ->
                etEditProvince.setText(tmpProvinceData[i])
            }
        }
    }

    override fun onSuccessEditProfile() {
        startActivity<MainActivity> {
            putExtra(Constant.switchBackToMain, "1")
        }
    }

    override fun onGetCityData(data: List<GeneralProvinceModel>?) {
        val tmpCityData: MutableList<String> = mutableListOf()
        tmpCityData.clear()
        data?.forEach { cities ->
            cities.cityName?.let { notNullCities -> tmpCityData.add(notNullCities) }
        }
        autoTextCityAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, tmpCityData)
        etEditCity.setAdapter(autoTextCityAdapter)
        etEditCity.threshold = 1
        selectorEditCity.setOnClickListener {
            selector(getString(R.string.pilih_kota), tmpCityData) { _, i ->
                etEditCity.setText(tmpCityData[i])
            }
        }

    }

    override fun onGetUserData(data: UserProfileModel?) {
        this.currentUserId = data?.userID
        etEditName.setText(data?.nameUser)
        etEditUserPhoneNumber.setText(data?.phoneNumberUser)
        etEditUserEmail.setText(data?.emailUser)
        etEditUserAddress.setText(data?.addressUser)
        etEditCity.setText(data?.cityUser)
        etEditProvince.setText(data?.provinceUser)

    }

    override fun onFailGetRajaOngkirData(msg: String?) {
    }

    override fun onFailEditProfile(msg: String?) {
        logE(msg)
    }

    override fun onNotLoginYet() {
        alertHelperToLoginActivity(resources.getString(R.string.login_first))
    }


    override fun initView() {
        btnSaveEditUser.setOnClickListener {
            val tmpName = etEditName.text.toString().trim()
            val tmpAddress = etEditUserAddress.text.toString().trim()
            val tmpProvince = etEditProvince.text.toString().trim()
            val tmpCity = etEditCity.text.toString().trim()
            val tmpPhoneNumber = etEditUserPhoneNumber.text.toString().trim()
            val tmpEmail = etEditUserEmail.text.toString().trim()
            when {
                tmpName.isBlank() -> etEditName.requestError(getString(R.string.not_null))
                tmpAddress.isBlank() -> etEditUserAddress.requestError(getString(R.string.not_null))
                tmpProvince.isBlank() -> etEditProvince.requestError(getString(R.string.not_null))
                tmpCity.isBlank() -> etEditCity.requestError(getString(R.string.not_null))
                tmpPhoneNumber.isBlank() -> etEditUserPhoneNumber.requestError(getString(R.string.not_null))
                tmpEmail.isBlank() -> etEditUserEmail.requestError(getString(R.string.not_null))
                else -> {
                    userData =
                        UserProfileModel(
                            null,
                            currentUserId,
                            null,
                            tmpName,
                            tmpEmail,
                            tmpPhoneNumber,
                            tmpAddress,
                            tmpProvince,
                            tmpCity
                        )
                    presenter.updateUserData(userData)
                }
            }
        }
    }

}