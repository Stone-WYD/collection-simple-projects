
package com.wyd.feignsimpletest.kaiheyun.model.result.point;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class PointContent {

    @SerializedName("BleCard")
    private Object bleCard;
    @SerializedName("CheckItemsCount")
    private Long checkItemsCount;
    @SerializedName("Id")
    private Long id;
    @SerializedName("IsVirtual")
    private Boolean isVirtual;
    @SerializedName("Latitude")
    private Object latitude;
    @SerializedName("LimitBleLocation")
    private Boolean limitBleLocation;
    @SerializedName("LimitNfcLocation")
    private Boolean limitNfcLocation;
    @SerializedName("Longitude")
    private Object longitude;
    @SerializedName("MaxValidDistance")
    private Object maxValidDistance;
    @SerializedName("Name")
    private String name;
    @SerializedName("NfcCard")
    private Object nfcCard;
    @SerializedName("Note")
    private String note;
    @SerializedName("Order")
    private Long order;
    @SerializedName("PhotoId")
    private Object photoId;
    @SerializedName("QRCode")
    private Object qRCode;
    @SerializedName("QrCodeLimitDistance")
    private Object qrCodeLimitDistance;
    @SerializedName("RfidCard")
    private String rfidCard;
    @SerializedName("Station")
    private Station station;
    @SerializedName("VirtualCard")
    private Object virtualCard;
    @SerializedName("VirtualDistance")
    private Long virtualDistance;

    public Object getBleCard() {
        return bleCard;
    }

    public void setBleCard(Object bleCard) {
        this.bleCard = bleCard;
    }

    public Long getCheckItemsCount() {
        return checkItemsCount;
    }

    public void setCheckItemsCount(Long checkItemsCount) {
        this.checkItemsCount = checkItemsCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsVirtual() {
        return isVirtual;
    }

    public void setIsVirtual(Boolean isVirtual) {
        this.isVirtual = isVirtual;
    }

    public Object getLatitude() {
        return latitude;
    }

    public void setLatitude(Object latitude) {
        this.latitude = latitude;
    }

    public Boolean getLimitBleLocation() {
        return limitBleLocation;
    }

    public void setLimitBleLocation(Boolean limitBleLocation) {
        this.limitBleLocation = limitBleLocation;
    }

    public Boolean getLimitNfcLocation() {
        return limitNfcLocation;
    }

    public void setLimitNfcLocation(Boolean limitNfcLocation) {
        this.limitNfcLocation = limitNfcLocation;
    }

    public Object getLongitude() {
        return longitude;
    }

    public void setLongitude(Object longitude) {
        this.longitude = longitude;
    }

    public Object getMaxValidDistance() {
        return maxValidDistance;
    }

    public void setMaxValidDistance(Object maxValidDistance) {
        this.maxValidDistance = maxValidDistance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getNfcCard() {
        return nfcCard;
    }

    public void setNfcCard(Object nfcCard) {
        this.nfcCard = nfcCard;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public Object getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Object photoId) {
        this.photoId = photoId;
    }

    public Object getQRCode() {
        return qRCode;
    }

    public void setQRCode(Object qRCode) {
        this.qRCode = qRCode;
    }

    public Object getQrCodeLimitDistance() {
        return qrCodeLimitDistance;
    }

    public void setQrCodeLimitDistance(Object qrCodeLimitDistance) {
        this.qrCodeLimitDistance = qrCodeLimitDistance;
    }

    public String getRfidCard() {
        return rfidCard;
    }

    public void setRfidCard(String rfidCard) {
        this.rfidCard = rfidCard;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public Object getVirtualCard() {
        return virtualCard;
    }

    public void setVirtualCard(Object virtualCard) {
        this.virtualCard = virtualCard;
    }

    public Long getVirtualDistance() {
        return virtualDistance;
    }

    public void setVirtualDistance(Long virtualDistance) {
        this.virtualDistance = virtualDistance;
    }

}
