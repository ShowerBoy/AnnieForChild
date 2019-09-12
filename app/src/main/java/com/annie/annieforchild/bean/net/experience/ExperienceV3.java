package com.annie.annieforchild.bean.net.experience;

import java.io.Serializable;
import java.util.List;

/**
 *
 */

public class ExperienceV3 implements Serializable {
    private String background_img;

    private String bottom_img;
    private TyProcess tyProcess;
    private JoinTest joinTest;

    private int  location_bee;
    private List<ExpItemBeanV3> plate;
    public int getLocation_bee() {
        return location_bee;
    }

    public void setLocation_bee(int location_bee) {
        this.location_bee = location_bee;
    }
    public String getBackground_img() {
        return background_img;
    }

    public void setBackground_img(String background_img) {
        this.background_img = background_img;
    }

    public JoinTest getJoinTest() {
        return joinTest;
    }

    public void setJoinTest(JoinTest joinTest) {
        this.joinTest = joinTest;
    }



    public TyProcess getTyProcess() {
        return tyProcess;
    }

    public void setTyProcess(TyProcess tyProcess) {
        this.tyProcess = tyProcess;
    }


    public List<ExpItemBeanV3> getPlate() {
        return plate;
    }

    public void setPlate(List<ExpItemBeanV3> plate) {
        this.plate = plate;
    }
    public String getBottom_img() {
        return bottom_img;
    }

    public void setBottom_img(String bottom_img) {
        this.bottom_img = bottom_img;
    }
}
