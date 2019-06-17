package com.annie.annieforchild.bean.net;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.List;

public class ShengBean implements IPickerViewData {
    public String name;
    public List<Shi> city;
    public static class Shi{
        public String name;
        public List<String>area;
    }
    @Override
    public String getPickerViewText() {
        return this.name;
    }
}