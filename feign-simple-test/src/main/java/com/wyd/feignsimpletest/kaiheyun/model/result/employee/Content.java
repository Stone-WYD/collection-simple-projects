
package com.wyd.feignsimpletest.kaiheyun.model.result.employee;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.List;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Content {

    @SerializedName("Address")
    private Object address;
    @SerializedName("Card")
    private Object card;
    @SerializedName("City")
    private Object city;
    @SerializedName("Company")
    private Object company;
    @SerializedName("Departments")
    private List<Object> departments;
    @SerializedName("EditPermission")
    private Boolean editPermission;
    @SerializedName("Email")
    private Object email;
    @SerializedName("Gender")
    private Object gender;
    @SerializedName("Id")
    private Long id;
    @SerializedName("IsChief")
    private Boolean isChief;
    @SerializedName("LastLoginedTime")
    private String lastLoginedTime;
    @SerializedName("Name")
    private String name;
    @SerializedName("Note")
    private Object note;
    @SerializedName("Password")
    private String password;
    @SerializedName("Province")
    private Object province;
    @SerializedName("Station")
    private Station station;
    @SerializedName("Telephone")
    private Object telephone;
    @SerializedName("UserPhoto")
    private Object userPhoto;
    @SerializedName("UserType")
    private Long userType;
    @SerializedName("UserVerificationInfo")
    private Object userVerificationInfo;
    @SerializedName("Username")
    private String username;

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public Object getCard() {
        return card;
    }

    public void setCard(Object card) {
        this.card = card;
    }

    public Object getCity() {
        return city;
    }

    public void setCity(Object city) {
        this.city = city;
    }

    public Object getCompany() {
        return company;
    }

    public void setCompany(Object company) {
        this.company = company;
    }

    public List<Object> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Object> departments) {
        this.departments = departments;
    }

    public Boolean getEditPermission() {
        return editPermission;
    }

    public void setEditPermission(Boolean editPermission) {
        this.editPermission = editPermission;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public Object getGender() {
        return gender;
    }

    public void setGender(Object gender) {
        this.gender = gender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsChief() {
        return isChief;
    }

    public void setIsChief(Boolean isChief) {
        this.isChief = isChief;
    }

    public String getLastLoginedTime() {
        return lastLoginedTime;
    }

    public void setLastLoginedTime(String lastLoginedTime) {
        this.lastLoginedTime = lastLoginedTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getNote() {
        return note;
    }

    public void setNote(Object note) {
        this.note = note;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Object getProvince() {
        return province;
    }

    public void setProvince(Object province) {
        this.province = province;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public Object getTelephone() {
        return telephone;
    }

    public void setTelephone(Object telephone) {
        this.telephone = telephone;
    }

    public Object getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(Object userPhoto) {
        this.userPhoto = userPhoto;
    }

    public Long getUserType() {
        return userType;
    }

    public void setUserType(Long userType) {
        this.userType = userType;
    }

    public Object getUserVerificationInfo() {
        return userVerificationInfo;
    }

    public void setUserVerificationInfo(Object userVerificationInfo) {
        this.userVerificationInfo = userVerificationInfo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
