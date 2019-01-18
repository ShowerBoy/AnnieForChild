package com.annie.annieforchild.bean.net;

import java.io.Serializable;
import java.util.List;

public class PreheatConsult implements Serializable {

    private List<PreheatConsultList> Microclass;
    private List<PreheatConsultList> Material;
    public void setMicroclass(List<PreheatConsultList> Microclass) {
        this.Microclass = Microclass;
    }
    public List<PreheatConsultList> getMicroclass() {
        return Microclass;
    }

    public void setMaterial(List<PreheatConsultList> Material) {
        this.Material = Material;
    }
    public List<PreheatConsultList> getMaterial() {
        return Material;
    }

}
