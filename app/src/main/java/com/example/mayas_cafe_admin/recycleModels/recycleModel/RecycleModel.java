package com.example.mayas_cafe_admin.recycleModels.recycleModel;

public class RecycleModel {

    String orderId, pickUpTime, orderAmt, orderStatus, orderItems, orderImg;
    String orderName, orderSize, orderQty;
    String notifyTitle, notifyBody, notifyDate, notifyTime;
    String menuName, menuImg;
    String userName, userImage;

    public RecycleModel(String orderId, String pickUpTime, String orderAmt, String orderStatus, String orderItems, String orderImg) {
        this.orderId = orderId;
        this.pickUpTime = pickUpTime;
        this.orderAmt = orderAmt;
        this.orderStatus = orderStatus;
        this.orderItems = orderItems;
        this.orderImg = orderImg;
    }

    public RecycleModel(String orderAmt, String orderImg, String orderName, String orderSize, String orderQty) {
        this.orderAmt = orderAmt;
        this.orderImg = orderImg;
        this.orderName = orderName;
        this.orderSize = orderSize;
        this.orderQty = orderQty;
    }

    public RecycleModel(String notifyTitle, String notifyBody, String notifyDate, String notifyTime) {
        this.notifyTitle = notifyTitle;
        this.notifyBody = notifyBody;
        this.notifyDate = notifyDate;
        this.notifyTime = notifyTime;
    }

    public RecycleModel(String menuName, String menuImg) {
        this.menuName = menuName;
        this.menuImg = menuImg;
    }


    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuImg() {
        return menuImg;
    }

    public void setMenuImg(String menuImg) {
        this.menuImg = menuImg;
    }

    public String getNotifyTitle() {
        return notifyTitle;
    }

    public void setNotifyTitle(String notifyTitle) {
        this.notifyTitle = notifyTitle;
    }

    public String getNotifyBody() {
        return notifyBody;
    }

    public void setNotifyBody(String notifyBody) {
        this.notifyBody = notifyBody;
    }

    public String getNotifyDate() {
        return notifyDate;
    }

    public void setNotifyDate(String notifyDate) {
        this.notifyDate = notifyDate;
    }

    public String getNotifyTime() {
        return notifyTime;
    }

    public void setNotifyTime(String notifyTime) {
        this.notifyTime = notifyTime;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderSize() {
        return orderSize;
    }

    public void setOrderSize(String orderSize) {
        this.orderSize = orderSize;
    }

    public String getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(String orderQty) {
        this.orderQty = orderQty;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPickUpTime() {
        return pickUpTime;
    }

    public void setPickUpTime(String pickUpTime) {
        this.pickUpTime = pickUpTime;
    }

    public String getOrderAmt() {
        return orderAmt;
    }

    public void setOrderAmt(String orderAmt) {
        this.orderAmt = orderAmt;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(String orderItems) {
        this.orderItems = orderItems;
    }

    public String getOrderImg() {
        return orderImg;
    }

    public void setOrderImg(String orderImg) {
        this.orderImg = orderImg;
    }
}
