package com.beibei.init.common.newNetwork.model

import android.os.Parcel
import android.os.Parcelable

/**
 *
 * 项目名称:  Demeter-Android
 * 包名:     com.beibei.init.common.network.model
 * 创建人 :   whj
 * 创建时间:  2018/2/7 10:50
 * 类描述:
 * 备注:
 *
 */


data class LoginDataBean(
        val utype: String = "",
        val photo: String = "",
        val isPerfectInformation: Int = 0,
        val account: String = "",
        val token: String = ""
)


/**
 * 版本更新
 */
data class UpdateBean(
        val isUpdate: String, //是否有新版本（0-否 1-是）
        val isForce: String,
        val downloadUrl: String,
        val versionCode: String
)


data class PushAdBean(
        val msgId: Int = 0,
        val picUrl: String = "",
        val msgUrl: String = ""
)

data class MsgContentBean(
        var url: String = ""
)

data class PushMsgBean(
        var msgId: Int = 0,
        var msgType: Int = 0,
        var msgTitle: String? = "",
        var msgTime: String? = "",
        var msgContent: String? = ""
)


data class VersionSearchBean(
        var download_url: String = "",
        var result: String = "",
        var new_version_name: String = ""
)

class FileDetailsBean {
    var fileName: String? = ""
    var filePath: String? = ""
    var fileTimeLength: String? = ""
    var fileDuring: Long = 0
    var lastModified: Long = 0
    override fun toString(): String {
        return "FileDetailsBean(fileName=$fileName, filePath=$filePath, fileTimeLength=$fileTimeLength, fileDuring=$fileDuring, lastModified=$lastModified)"
    }
}

//
//data class UserInfoBean(
//        val id: Int = 0,
//        val mobile: String = "",
//        val pwd: String = "",
//        val vcode: String = "",
//        val nm: String = "",
//        val sex: String = "",
//        val email: String = "",
//        val birthday: String = "",
//        val vocation: String = "",
//        val lan: String = "",
//        val lan2: String = "",
//        val lan3: String = "",
//        val edu: String = "",
//        val province: String = "",
//        val city: String = "",
//        val utype: String = "",
//        val status: String = "",
//        val point: String = "",
//        val creDt: String = "",
//        val modDt: String = "",
//        val userSource: String = "",
//        val lastLoginDt: String = "",
//        val picId: String = "",
//        val isHasLanguageAbility: Int = 0
//)


data class VoiceLibListBean(
        val pageNo: Int = 0,
        val pageSize: Int = 0,
        val list: List<VoiceLibBean> = listOf(),
        val totalRecordNo: Int = 0,
        val totalPageNo: Int = 0,
        val start: Int = 0,
        val end: Int = 0
)

//任务中心语料库跟可领取任务数量bean
data class TaskCenterCountsBean(
        val allTaskCount: Int,
        val libCount: Int
)


data class MyTaskCountBean(
        var taskCount: Int,
        var libCount: Int,
        var totalPoint: Int
)


data class BannerItem(
		var id: Int,
		var type: String,
		var fileId: String,
		var title: String,
		var state: String
)

//首页统计数据
data class HomeStatistics(
		var myLibCount: Int,
		var myTaskCount: Int,
		var completedTaskCount: Int,
		var undoneTaskCount: Int
)

data class VoiceLibBean(
        val taskCount: Int = 0,
        val checkStatus: String = "",
        val libCd: String = "",
        val remark: String = "",
        val title: String = ""
)


data class TaskListBean(
        var pageNo: Int,
        var pageSize: Int,
        var list: List<TaskItem>,
        var totalRecordNo: Int,
        var totalPageNo: Int,
        var start: Int,
        var end: Int
)

data class TaskItem(
        var collectDuration: Int,
        var lan1Nm: String,
        var taskSubCd: String,
        var senCount: Int,
        var lan3: String,
        var collectEndDt: String,
        var lan4: String,
        var lan1: String,
        var lan2: String,
        var lan3Nm: String,
        var lan2Nm: String,
        var taskCollPoint: Int,
        var lan4Nm: String,
        var taskCd: String,
        var id: Int
)


data class MyTaskListBean(
        var pageNo: Int,
        var pageSize: Int,
        var list: List<MyTaskItem>,
        var totalRecordNo: Int,
        var totalPageNo: Int,
        var start: Int,
        var end: Int
)

data class MyTaskItem(
        var score: Int,
        var sentencePerTask: Int,
        var taskSubCd: String,
        var projectCd: String,
        var libCd: String,
        var finishCount: Int,
        var taskCd: String,
        var collectEndTime: String,
        var lan1: String,
        var id: Int,
        var totalCount: Int,
        var status: String
)

//任务详情
data class TaskDetail(
        var score: Int,
        var sentenceList: List<Sentence>,
        var taskSubCd: String?,
        var projectCd: String?,
        var libCd: String?,
        var taskCd: String?,
        var collectEndTime: String?,
        var lan1: String?,
        var id: Int,
        var totalCount: Int,
        var sentencePerTask: Int,
        var finishCount: Int,
        var status: String?
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readInt(),
            ArrayList<Sentence>().apply { source.readList(this, Sentence::class.java.classLoader) },
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readInt(),
            source.readInt(),
            source.readInt(),
            source.readInt(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(score)
        writeList(sentenceList)
        writeString(taskSubCd)
        writeString(projectCd)
        writeString(libCd)
        writeString(taskCd)
        writeString(collectEndTime)
        writeString(lan1)
        writeInt(id)
        writeInt(totalCount)
        writeInt(sentencePerTask)
        writeInt(finishCount)
        writeString(status)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<TaskDetail> = object : Parcelable.Creator<TaskDetail> {
            override fun createFromParcel(source: Parcel): TaskDetail = TaskDetail(source)
            override fun newArray(size: Int): Array<TaskDetail?> = arrayOfNulls(size)
        }
    }
}

data class Sentence(
        var sentence: String?,
        var uploadFlag: Int,
        var sentenceId: Int,
        var id: Int,
        var taskRecordId: Int,
        var taskSubCd: String?,
        var voiceId: String?,
        var status: String?,
        var reason: String?,
        var sex: String?,
        var noise: String?,
        var accent: String?,
        var childVoice: String?,
        var annoText: String?,
        var isSame: String?,
        var voiceDt: String?,
        var annoDt: String?,
        var annoUser: String?,
        var checkDt: String?,
        var checkUser: String?
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readInt(),
            source.readInt(),
            source.readInt(),
            source.readInt(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(sentence)
        writeInt(uploadFlag)
        writeInt(sentenceId)
        writeInt(id)
        writeInt(taskRecordId)
        writeString(taskSubCd)
        writeString(voiceId)
        writeString(status)
        writeString(reason)
        writeString(sex)
        writeString(noise)
        writeString(accent)
        writeString(childVoice)
        writeString(annoText)
        writeString(isSame)
        writeString(voiceDt)
        writeString(annoDt)
        writeString(annoUser)
        writeString(checkDt)
        writeString(checkUser)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Sentence> = object : Parcelable.Creator<Sentence> {
            override fun createFromParcel(source: Parcel): Sentence = Sentence(source)
            override fun newArray(size: Int): Array<Sentence?> = arrayOfNulls(size)
        }
    }
}


data class UserInfoItem(
        val item: String = "",
        val value: String = "",
        val type: Int = 0
)

data class PicFileBean(
        var path: String?
)


data class EducationBean(
        var id: Int,
        var dictCd: String?,
        var dictValue: String?,
        var parentDictCd: String?,
        var seq: Int,
        var status: String?,
        var creDt: String?,
        var creUser: String?,
        var modDt: String?,
        var modUser: String?
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readInt(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readInt(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id)
        writeString(dictCd)
        writeString(dictValue)
        writeString(parentDictCd)
        writeInt(seq)
        writeString(status)
        writeString(creDt)
        writeString(creUser)
        writeString(modDt)
        writeString(modUser)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<EducationBean> = object : Parcelable.Creator<EducationBean> {
            override fun createFromParcel(source: Parcel): EducationBean = EducationBean(source)
            override fun newArray(size: Int): Array<EducationBean?> = arrayOfNulls(size)
        }
    }
}


data class ProvinceBean(//省份Bean
        var province: String?,
        var iscities: String?,
        var id: Int,
        var provinceId: String?
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readInt(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(province)
        writeString(iscities)
        writeInt(id)
        writeString(provinceId)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ProvinceBean> = object : Parcelable.Creator<ProvinceBean> {
            override fun createFromParcel(source: Parcel): ProvinceBean = ProvinceBean(source)
            override fun newArray(size: Int): Array<ProvinceBean?> = arrayOfNulls(size)
        }
    }
}


data class CityBean(//城市Bean
        var city: String?,
        var id: Int,
        var cityId: String?
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readInt(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(city)
        writeInt(id)
        writeString(cityId)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<CityBean> = object : Parcelable.Creator<CityBean> {
            override fun createFromParcel(source: Parcel): CityBean = CityBean(source)
            override fun newArray(size: Int): Array<CityBean?> = arrayOfNulls(size)
        }
    }
}

data class LanguageBean(
        var id: Int,
        var dictCd: String?,
        var dictValue: String?,
        var parentDictCd: String?,
        var seq: Int?,
        var status: String?,
        var creDt: String?,
        var creUser: String?,
        var modDt: String?,
        var modUser: String?
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readInt(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id)
        writeString(dictCd)
        writeString(dictValue)
        writeString(parentDictCd)
        writeValue(seq)
        writeString(status)
        writeString(creDt)
        writeString(creUser)
        writeString(modDt)
        writeString(modUser)
    }

    override fun toString(): String {
        return "LanguageBean(id=$id, dictCd=$dictCd, dictValue=$dictValue, parentDictCd=$parentDictCd, seq=$seq, status=$status, creDt=$creDt, creUser=$creUser, modDt=$modDt, modUser=$modUser)"
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<LanguageBean> = object : Parcelable.Creator<LanguageBean> {
            override fun createFromParcel(source: Parcel): LanguageBean = LanguageBean(source)
            override fun newArray(size: Int): Array<LanguageBean?> = arrayOfNulls(size)
        }
    }
}


data class LibCheckInfoBean(
        var sentence: String?,
        var checkStatus: String?,
        var voiceFileId: String?,
        var libSentence: String?,
        var libCd: String?,
        var libRemark: String?,
        var title: String?,
        var checkRemark: String?
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(sentence)
        writeString(checkStatus)
        writeString(voiceFileId)
        writeString(libSentence)
        writeString(libCd)
        writeString(libRemark)
        writeString(title)
        writeString(checkRemark)
    }

    override fun toString(): String {
        return "LibCheckInfoBean(sentence=$sentence, checkStatus=$checkStatus, voiceFileId=$voiceFileId, libSentence=$libSentence, libCd=$libCd, libRemark=$libRemark, title=$title, checkRemark=$checkRemark)"
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<LibCheckInfoBean> = object : Parcelable.Creator<LibCheckInfoBean> {
            override fun createFromParcel(source: Parcel): LibCheckInfoBean = LibCheckInfoBean(source)
            override fun newArray(size: Int): Array<LibCheckInfoBean?> = arrayOfNulls(size)
        }
    }
}

data class LibDetailsBean(
        var sentence: String = "",
        var libNm: String = "",
        var lan1: String = "",
        var remark: String = "",
        var title: String = ""
) {
    override fun toString(): String {
        return "LibDetailsBean(sentence='$sentence', libNm='$libNm', lan1='$lan1', remark='$remark', title='$title')"
    }
}


data class VersionData(
        var id: Int,
        var appType: String,
        var appVersionNo: String,
        var appVersionName: String,
        var downNumber: Any,
        var state: String,
        var versionDes: String,
        var url: String,
        var creDt: Any,
        var modDt: Any,
        var creUser: Any,
        var modUser: Any
)


data class SentenceDetailsBean(
		var id: Int,
		var taskRecordId: Int,
		var taskSubCd: String?,
		var sentenceId: Int,
		var voiceId: String?,
		var status: String?,
		var reason: String?,
		var sex: String?,
		var noise: String?,
		var accent: String?,
		var childVoice: String?,
		var annoText: String?,
		var isSame: String?,
		var voiceDt: Long,
		var sentence: String?
) {
    override fun toString(): String {
        return "SentenceDetailsBean(id=$id, taskRecordId=$taskRecordId, taskSubCd=$taskSubCd, sentenceId=$sentenceId, voiceId=$voiceId, status=$status, reason=$reason, sex=$sex, noise=$noise, accent=$accent, childVoice=$childVoice, annoText=$annoText, isSame=$isSame, voiceDt=$voiceDt, sentence=$sentence)"
    }
}