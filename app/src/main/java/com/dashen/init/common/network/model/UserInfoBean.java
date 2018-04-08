package com.dashen.init.common.network.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by beibei on 2018/2/28.
 */

public class UserInfoBean implements Parcelable {
    /**
     * id : 56
     * mobile : 17610767856
     * vcode : null
     * nm : hbhbbh
     * sex : 女
     * sexVal :
     * email : 1501539671@qq.com
     * birthday : 2018-03-13
     * vocation : 其他
     * vocationVal :
     * lan : 英文
     * lanVal : null
     * lan2 : 英文
     * lan2Val : null
     * lan3 : 英文
     * lan3Val : null
     * edu : 本科
     * eduVal :
     * province : 北京市
     * provinceVal : null
     * city : 太原市
     * cityVal : null
     * utype : 0
     * utypeVal : 初始注册
     * status : 1
     * point : null
     * creDt : 2018-02-26 23:55:13
     * modDt : 2018-03-14 05:36:24
     * lastLoginDt : 2018-03-14 05:37:26
     * picId : group1/M00/01/93/b8pCLlqnMiWAXnrFAADqPvWHBFI519.jpg
     * isHasLanguageAbility : 0
     */

    private int id;
    private String mobile;
    private String vcode;
    private String nm;
    private String sex;
    private String sexVal;
    private String email;
    private String birthday;
    private String vocation;
    private String vocationVal;
    private String lan;
    private String lanVal;
    private String lan2;
    private String lan2Val;
    private String lan3;
    private String lan3Val;
    private String edu;
    private String eduVal;
    private String province;
    private String provinceVal;
    private String city;
    private String cityVal;
    private String utype;
    private String utypeVal;
    private String status;
    private String point;
    private String creDt;
    private String modDt;
    private String lastLoginDt;
    private String picId;
    private int isHasLanguageAbility;

    protected UserInfoBean(Parcel in) {
        id = in.readInt();
        mobile = in.readString();
        vcode = in.readString();
        nm = in.readString();
        sex = in.readString();
        sexVal = in.readString();
        email = in.readString();
        birthday = in.readString();
        vocation = in.readString();
        vocationVal = in.readString();
        lan = in.readString();
        lanVal = in.readString();
        lan2 = in.readString();
        lan2Val = in.readString();
        lan3 = in.readString();
        lan3Val = in.readString();
        edu = in.readString();
        eduVal = in.readString();
        province = in.readString();
        provinceVal = in.readString();
        city = in.readString();
        cityVal = in.readString();
        utype = in.readString();
        utypeVal = in.readString();
        status = in.readString();
        point = in.readString();
        creDt = in.readString();
        modDt = in.readString();
        lastLoginDt = in.readString();
        picId = in.readString();
        isHasLanguageAbility = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(mobile);
        dest.writeString(vcode);
        dest.writeString(nm);
        dest.writeString(sex);
        dest.writeString(sexVal);
        dest.writeString(email);
        dest.writeString(birthday);
        dest.writeString(vocation);
        dest.writeString(vocationVal);
        dest.writeString(lan);
        dest.writeString(lanVal);
        dest.writeString(lan2);
        dest.writeString(lan2Val);
        dest.writeString(lan3);
        dest.writeString(lan3Val);
        dest.writeString(edu);
        dest.writeString(eduVal);
        dest.writeString(province);
        dest.writeString(provinceVal);
        dest.writeString(city);
        dest.writeString(cityVal);
        dest.writeString(utype);
        dest.writeString(utypeVal);
        dest.writeString(status);
        dest.writeString(point);
        dest.writeString(creDt);
        dest.writeString(modDt);
        dest.writeString(lastLoginDt);
        dest.writeString(picId);
        dest.writeInt(isHasLanguageAbility);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserInfoBean> CREATOR = new Creator<UserInfoBean>() {
        @Override
        public UserInfoBean createFromParcel(Parcel in) {
            return new UserInfoBean(in);
        }

        @Override
        public UserInfoBean[] newArray(int size) {
            return new UserInfoBean[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }

    public String getNm() {
        return nm;
    }

    public void setNm(String nm) {
        this.nm = nm;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSexVal() {
        return sexVal;
    }

    public void setSexVal(String sexVal) {
        this.sexVal = sexVal;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getVocation() {
        return vocation;
    }

    public void setVocation(String vocation) {
        this.vocation = vocation;
    }

    public String getVocationVal() {
        return vocationVal;
    }

    public void setVocationVal(String vocationVal) {
        this.vocationVal = vocationVal;
    }

    public String getLan() {
        return lan;
    }

    public void setLan(String lan) {
        this.lan = lan;
    }

    public String getLanVal() {
        return lanVal;
    }

    public void setLanVal(String lanVal) {
        this.lanVal = lanVal;
    }

    public String getLan2() {
        return lan2;
    }

    public void setLan2(String lan2) {
        this.lan2 = lan2;
    }

    public String getLan2Val() {
        return lan2Val;
    }

    public void setLan2Val(String lan2Val) {
        this.lan2Val = lan2Val;
    }

    public String getLan3() {
        return lan3;
    }

    public void setLan3(String lan3) {
        this.lan3 = lan3;
    }

    public String getLan3Val() {
        return lan3Val;
    }

    public void setLan3Val(String lan3Val) {
        this.lan3Val = lan3Val;
    }

    public String getEdu() {
        return edu;
    }

    public void setEdu(String edu) {
        this.edu = edu;
    }

    public String getEduVal() {
        return eduVal;
    }

    public void setEduVal(String eduVal) {
        this.eduVal = eduVal;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvinceVal() {
        return provinceVal;
    }

    public void setProvinceVal(String provinceVal) {
        this.provinceVal = provinceVal;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityVal() {
        return cityVal;
    }

    public void setCityVal(String cityVal) {
        this.cityVal = cityVal;
    }

    public String getUtype() {
        return utype;
    }

    public void setUtype(String utype) {
        this.utype = utype;
    }

    public String getUtypeVal() {
        return utypeVal;
    }

    public void setUtypeVal(String utypeVal) {
        this.utypeVal = utypeVal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getCreDt() {
        return creDt;
    }

    public void setCreDt(String creDt) {
        this.creDt = creDt;
    }

    public String getModDt() {
        return modDt;
    }

    public void setModDt(String modDt) {
        this.modDt = modDt;
    }

    public String getLastLoginDt() {
        return lastLoginDt;
    }

    public void setLastLoginDt(String lastLoginDt) {
        this.lastLoginDt = lastLoginDt;
    }

    public String getPicId() {
        return picId;
    }

    public void setPicId(String picId) {
        this.picId = picId;
    }

    public int getIsHasLanguageAbility() {
        return isHasLanguageAbility;
    }

    public void setIsHasLanguageAbility(int isHasLanguageAbility) {
        this.isHasLanguageAbility = isHasLanguageAbility;
    }
}
