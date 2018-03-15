package com.annie.annieforchild.view;

import android.widget.ListView;
import android.widget.PopupWindow;

import com.annie.annieforchild.view.info.ViewInfo;

/**
 * Created by WangLei on 2018/1/19 0019
 */

public interface ReadingView extends ViewInfo {
    PopupWindow getPopupWindow();
    ListView getPopupListView();
}
