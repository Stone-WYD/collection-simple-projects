
package com.wyd.feignsimpletest.kaiheyun.model.result.record;

import com.google.gson.annotations.SerializedName;

 

  
@SuppressWarnings("unused")
public class Record {

    @SerializedName("AttachmentCount")
    private Long attachmentCount;
    @SerializedName("CardType")
    private Long cardType;
    @SerializedName("CheckResultCount")
    private Long checkResultCount;
    @SerializedName("Device")
    private String device;
    @SerializedName("Id")
    private Long id;
    @SerializedName("PatrollerId")
    private Long patrollerId;
    @SerializedName("PatrollerName")
    private String patrollerName;
    @SerializedName("PlaceCard")
    private String placeCard;
    @SerializedName("PlaceId")
    private Object placeId;
    @SerializedName("PlaceName")
    private String placeName;
    @SerializedName("RepairState")
    private Long repairState;
    @SerializedName("RouteId")
    private Object routeId;
    @SerializedName("RouteName")
    private Object routeName;
    @SerializedName("Station")
    private Station station;
    @SerializedName("Time")
    private String time;

    public Long getAttachmentCount() {
        return attachmentCount;
    }

    public void setAttachmentCount(Long attachmentCount) {
        this.attachmentCount = attachmentCount;
    }

    public Long getCardType() {
        return cardType;
    }

    public void setCardType(Long cardType) {
        this.cardType = cardType;
    }

    public Long getCheckResultCount() {
        return checkResultCount;
    }

    public void setCheckResultCount(Long checkResultCount) {
        this.checkResultCount = checkResultCount;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPatrollerId() {
        return patrollerId;
    }

    public void setPatrollerId(Long patrollerId) {
        this.patrollerId = patrollerId;
    }

    public String getPatrollerName() {
        return patrollerName;
    }

    public void setPatrollerName(String patrollerName) {
        this.patrollerName = patrollerName;
    }

    public String getPlaceCard() {
        return placeCard;
    }

    public void setPlaceCard(String placeCard) {
        this.placeCard = placeCard;
    }

    public Object getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Object placeId) {
        this.placeId = placeId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public Long getRepairState() {
        return repairState;
    }

    public void setRepairState(Long repairState) {
        this.repairState = repairState;
    }

    public Object getRouteId() {
        return routeId;
    }

    public void setRouteId(Object routeId) {
        this.routeId = routeId;
    }

    public Object getRouteName() {
        return routeName;
    }

    public void setRouteName(Object routeName) {
        this.routeName = routeName;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
