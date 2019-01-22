package com.annie.annieforchild.bean.net;

import java.io.Serializable;
import java.util.List;

public class PreheatConsult implements Serializable {
    private int Microclassisshow;
    private List<PreheatConsultList> Microclass;
    private List<PreheatConsultList> Material;

    public int getMicroclassisshow() {
        return Microclassisshow;
    }

    public void setMicroclassisshow(int microclassisshow) {
        Microclassisshow = microclassisshow;
    }

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
