package com.beibei.init.common.networkJava.model

import android.os.Parcel
import android.os.Parcelable

/**
 * project_name:   init
 * package_name:   com.beibei.init.common.networkJava.model
 * author:   beibei
 * create_time:    2018/9/19 18:35
 * class_desc:
 * remarks:
 */

//用户信息
data class UserInfoBean(
        val userId: String,
        val gender: String,
        val nickName: String,
        val userUrl: String,
        val realName: String,
        val personType: String,
        val gridlist: String,
        val mobileAccount: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userId)
        parcel.writeString(gender)
        parcel.writeString(nickName)
        parcel.writeString(userUrl)
        parcel.writeString(realName)
        parcel.writeString(personType)
        parcel.writeString(gridlist)
        parcel.writeString(mobileAccount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserInfoBean> {
        override fun createFromParcel(parcel: Parcel): UserInfoBean {
            return UserInfoBean(parcel)
        }

        override fun newArray(size: Int): Array<UserInfoBean?> {
            return arrayOfNulls(size)
        }
    }
}