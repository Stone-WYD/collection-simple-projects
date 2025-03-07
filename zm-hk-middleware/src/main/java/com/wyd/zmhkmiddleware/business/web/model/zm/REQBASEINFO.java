
package com.wyd.zmhkmiddleware.business.web.model.zm;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class REQBASEINFO {

    @SerializedName("REQ_BSN_ID")
    private String rEQBSNID;
    @SerializedName("REQ_SEND_TIME")
    private String rEQSENDTIME;
    @SerializedName("REQ_SERVER_NAME")
    private String rEQSERVERNAME;
    @SerializedName("REQ_SRC_SUB_SYS")
    private String rEQSRCSUBSYS;
    @SerializedName("REQ_SRC_SYS")
    private String rEQSRCSYS;
    @SerializedName("REQ_SYN_FLAG")
    private Long rEQSYNFLAG;
    @SerializedName("REQ_TAR_SUB_SYS")
    private String rEQTARSUBSYS;
    @SerializedName("REQ_TAR_SYS")
    private String rEQTARSYS;
    @SerializedName("REQ_TRACE_ID")
    private String rEQTRACEID;

    public String getREQBSNID() {
        return rEQBSNID;
    }

    public void setREQBSNID(String rEQBSNID) {
        this.rEQBSNID = rEQBSNID;
    }

    public String getREQSENDTIME() {
        return rEQSENDTIME;
    }

    public void setREQSENDTIME(String rEQSENDTIME) {
        this.rEQSENDTIME = rEQSENDTIME;
    }

    public String getREQSERVERNAME() {
        return rEQSERVERNAME;
    }

    public void setREQSERVERNAME(String rEQSERVERNAME) {
        this.rEQSERVERNAME = rEQSERVERNAME;
    }

    public String getREQSRCSUBSYS() {
        return rEQSRCSUBSYS;
    }

    public void setREQSRCSUBSYS(String rEQSRCSUBSYS) {
        this.rEQSRCSUBSYS = rEQSRCSUBSYS;
    }

    public String getREQSRCSYS() {
        return rEQSRCSYS;
    }

    public void setREQSRCSYS(String rEQSRCSYS) {
        this.rEQSRCSYS = rEQSRCSYS;
    }

    public Long getREQSYNFLAG() {
        return rEQSYNFLAG;
    }

    public void setREQSYNFLAG(Long rEQSYNFLAG) {
        this.rEQSYNFLAG = rEQSYNFLAG;
    }

    public String getREQTARSUBSYS() {
        return rEQTARSUBSYS;
    }

    public void setREQTARSUBSYS(String rEQTARSUBSYS) {
        this.rEQTARSUBSYS = rEQTARSUBSYS;
    }

    public String getREQTARSYS() {
        return rEQTARSYS;
    }

    public void setREQTARSYS(String rEQTARSYS) {
        this.rEQTARSYS = rEQTARSYS;
    }

    public String getREQTRACEID() {
        return rEQTRACEID;
    }

    public void setREQTRACEID(String rEQTRACEID) {
        this.rEQTRACEID = rEQTRACEID;
    }

}
