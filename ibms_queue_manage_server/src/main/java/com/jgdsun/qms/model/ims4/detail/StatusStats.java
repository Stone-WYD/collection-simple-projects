package com.jgdsun.qms.model.ims4.detail;

import org.noear.snack.annotation.ONodeAttr;

public class StatusStats {

    @ONodeAttr(name="BACKGROUND")
    private Long bACKGROUND;
    @ONodeAttr(name="DORMANT")
    private Long dORMANT;
    @ONodeAttr(name="EXCEPTION")
    private Long eXCEPTION;
    @ONodeAttr(name="OFFLINE")
    private Long oFFLINE;
    @ONodeAttr(name="RUNNING")
    private Long rUNNING;
    @ONodeAttr(name="TOTAL")
    private Long tOTAL;

    public Long getBACKGROUND() {
        return bACKGROUND;
    }

    public void setBACKGROUND(Long bACKGROUND) {
        this.bACKGROUND = bACKGROUND;
    }

    public Long getDORMANT() {
        return dORMANT;
    }

    public void setDORMANT(Long dORMANT) {
        this.dORMANT = dORMANT;
    }

    public Long getEXCEPTION() {
        return eXCEPTION;
    }

    public void setEXCEPTION(Long eXCEPTION) {
        this.eXCEPTION = eXCEPTION;
    }

    public Long getOFFLINE() {
        return oFFLINE;
    }

    public void setOFFLINE(Long oFFLINE) {
        this.oFFLINE = oFFLINE;
    }

    public Long getRUNNING() {
        return rUNNING;
    }

    public void setRUNNING(Long rUNNING) {
        this.rUNNING = rUNNING;
    }

    public Long getTOTAL() {
        return tOTAL;
    }

    public void setTOTAL(Long tOTAL) {
        this.tOTAL = tOTAL;
    }

}
