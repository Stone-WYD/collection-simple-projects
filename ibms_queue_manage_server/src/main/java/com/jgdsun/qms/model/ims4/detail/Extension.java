
package com.jgdsun.qms.model.ims4.detail;

import java.util.List;
import org.noear.snack.annotation.ONodeAttr;

@SuppressWarnings("unused")
public class Extension {

    @ONodeAttr(name="brightness_segment")
    private List<Object> brightnessSegment;

    private List<Object> materialList;
    @ONodeAttr(name="volume_segment")
    private List<Object> volumeSegment;

    public List<Object> getBrightnessSegment() {
        return brightnessSegment;
    }

    public void setBrightnessSegment(List<Object> brightnessSegment) {
        this.brightnessSegment = brightnessSegment;
    }

    public List<Object> getMaterialList() {
        return materialList;
    }

    public void setMaterialList(List<Object> materialList) {
        this.materialList = materialList;
    }

    public List<Object> getVolumeSegment() {
        return volumeSegment;
    }

    public void setVolumeSegment(List<Object> volumeSegment) {
        this.volumeSegment = volumeSegment;
    }

}
