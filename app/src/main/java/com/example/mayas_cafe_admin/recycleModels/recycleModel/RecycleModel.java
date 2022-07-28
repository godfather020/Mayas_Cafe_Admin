package com.example.mayas_cafe_admin.recycleModels.recycleModel;

public class RecycleModel {

    String orderId, pickUpTime, orderAmt, orderStatus, orderItems, orderImg;
    String orderName, orderSize, orderQty, custImg, payStatus;
    String notifyTitle, notifyBody, notifyDate, notifyTime, notifyId;
    String menuName, menuImg, menuId;
    String userName, userImage;
    String itemName, itemImg, itemId;
    String offersId, offersName, offersTitle, offersCode, offersDes, offersCal, offersUpTo, offersMin, offersStartAt, offersStopAt, offersImg;

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

    public RecycleModel(String menuName, String menuImg, String menuId) {
        this.menuName = menuName;
        this.menuImg = menuImg;
        this.menuId = menuId;
    }

    public RecycleModel(String itemName, String itemImg) {
        this.itemName = itemName;
        this.itemImg = itemImg;
    }

    public RecycleModel(String offersId, String offersName, String offersTitle, String offersCode, String offersDes, String offersCal, String offersUpTo, String offersMin, String offersStartAt, String offersStopAt, String offersImg) {
        this.offersId = offersId;
        this.offersName = offersName;
        this.offersTitle = offersTitle;
        this.offersCode = offersCode;
        this.offersDes = offersDes;
        this.offersCal = offersCal;
        this.offersUpTo = offersUpTo;
        this.offersMin = offersMin;
        this.offersStartAt = offersStartAt;
        this.offersStopAt = offersStopAt;
        this.offersImg = offersImg;
    }

    public RecycleModel(String custImg, String orderId, String pickUpTime, String orderAmt, String orderStatus, String orderItems, String orderImg, String payStatus) {
        this.orderId = orderId;
        this.pickUpTime = pickUpTime;
        this.orderAmt = orderAmt;
        this.orderStatus = orderStatus;
        this.orderItems = orderItems;
        this.orderImg = orderImg;
        this.custImg = custImg;
        this.payStatus = payStatus;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getCustImg() {
        return custImg;
    }

    public void setCustImg(String custImg) {
        this.custImg = custImg;
    }

    public String getOffersId() {
        return offersId;
    }

    public void setOffersId(String offersId) {
        this.offersId = offersId;
    }

    public String getOffersName() {
        return offersName;
    }

    public void setOffersName(String offersName) {
        this.offersName = offersName;
    }

    public String getOffersTitle() {
        return offersTitle;
    }

    public void setOffersTitle(String offersTitle) {
        this.offersTitle = offersTitle;
    }

    public String getOffersCode() {
        return offersCode;
    }

    public void setOffersCode(String offersCode) {
        this.offersCode = offersCode;
    }

    public String getOffersDes() {
        return offersDes;
    }

    public void setOffersDes(String offersDes) {
        this.offersDes = offersDes;
    }

    public String getOffersCal() {
        return offersCal;
    }

    public void setOffersCal(String offersCal) {
        this.offersCal = offersCal;
    }

    public String getOffersUpTo() {
        return offersUpTo;
    }

    public void setOffersUpTo(String offersUpTo) {
        this.offersUpTo = offersUpTo;
    }

    public String getOffersMin() {
        return offersMin;
    }

    public void setOffersMin(String offersMin) {
        this.offersMin = offersMin;
    }

    public String getOffersStartAt() {
        return offersStartAt;
    }

    public void setOffersStartAt(String offersStartAt) {
        this.offersStartAt = offersStartAt;
    }

    public String getOffersStopAt() {
        return offersStopAt;
    }

    public void setOffersStopAt(String offersStopAt) {
        this.offersStopAt = offersStopAt;
    }

    public String getOffersImg() {
        return offersImg;
    }

    public void setOffersImg(String offersImg) {
        this.offersImg = offersImg;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemImg() {
        return itemImg;
    }

    public void setItemImg(String itemImg) {
        this.itemImg = itemImg;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
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
