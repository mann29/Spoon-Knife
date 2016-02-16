package com.mitchell.services.core.partialloss.apddelivery.events;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XStreamAlias("UploadCompletedEventPayload")
public class UploadCompletedEventPayload  extends AbstractEventPayload {

    @XStreamOmitField
    private final String EVENT_TYPE = "UploadCompletedEvent";

    @XStreamAlias("ClaimNumber")
    private String claimNumber;

    @XStreamAlias("CarrierCode")
    private String carrierCode;

    @XStreamAlias("ShopCoCd")
    private String shopCoCd;

    @XStreamAlias("ShopOrgCd")
    private String shopOrgCd;

    @XStreamAlias("FolderId")
    private long folderId;

    @XStreamAlias("StatusCode")
    private long statusCode;

    public String getEventType() {
        return this.EVENT_TYPE;
    }

    public String getClaimNumber() {
        return claimNumber;
    }

    public void setClaimNumber(String claimNumber) {
        this.claimNumber = claimNumber;
    }

    public String getShopCoCd() {
        return shopCoCd;
    }

    public void setShopCoCd(String shopCoCd) {
        this.shopCoCd = shopCoCd;
    }

    public String getShopOrgCd() {
        return shopOrgCd;
    }

    public void setShopOrgCd(String shopOrgCd) {
        this.shopOrgCd = shopOrgCd;
    }

    public long getFolderId() {
        return folderId;
    }

    public void setFolderId(long folderId) {
        this.folderId = folderId;
    }

    public long getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(long statusCode) {
        this.statusCode = statusCode;
    }

    public String getCarrierCode() {
        return carrierCode;
    }

    public void setCarrierCode(String carrierCode) {
        this.carrierCode = carrierCode;
    }
}


