package com.annie.annieforchild.ui.fragment.square;

import android.app.Dialog;
import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.bean.DurationStatis;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.view.SongView;
import com.annie.baselibrary.base.BaseFragment;

import org.greenrobot.eventbus.Subscribe;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.view.ColumnChartView;

/**
 * 统计
 * Created by wanglei on 2018/5/11.
 */

public class CensusFragment extends BaseFragment implements SongView {
    private Spinner location, time;
    private TextView grindearDuration, grindearAverage, readingDuration, readingAverage, spokenDuration, spokenAverage;
    private List<String> locationList, timeList;
    private ColumnChartView chartView;
    private ColumnChartData data;
    private GrindEarPresenter presenter;
    private AlertHelper helper;
    private Dialog dialog;
    private int timeType, locationType;
    private int grindmin, grindhour, grindavemin, grindavehour, readingmin, readinghour, readingavemin, readingavehour, spokenmin, spokenhour, spokenavemin, spokenavehour;
    private float grindFloat, grindAveFloat, readingFloat, readingAveFloat, spokenFloat, spokenAveFloat;
    private DecimalFormat format;
    private boolean hasAxes = true;
    private boolean hasAxesNames = true;
    private boolean hasLabels = false;
    private boolean hasLabelForSelected = false;

    {
        setRegister(true);
    }

    public static CensusFragment instance() {
        CensusFragment fragment = new CensusFragment();
        return fragment;
    }

    @Override
    protected void initData() {
        format = new DecimalFormat("0.00");
        helper = new AlertHelper(getActivity());
        dialog = helper.LoadingDialog();
        locationList = new ArrayList<>();
        timeList = new ArrayList<>();
        timeType = 1;
        locationType = 4;
        timeList.add("本周");
        timeList.add("本月");
//        timeList.add("本季");
        locationList.add("全国");
        locationList.add("全班");
        locationList.add("全校");
        locationList.add("全市");
        location.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, locationList));
        time.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, timeList));
        location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    if (locationType != 4) {
                        locationType = 4;
                        presenter.getDurationStatistics(timeType, locationType);
                    }
                } else {
                    if (locationType != position) {
                        locationType = position;
                        presenter.getDurationStatistics(timeType, locationType);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (timeType != position + 1) {
                    timeType = position + 1;
                    presenter.getDurationStatistics(timeType, locationType);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        chartView.setOnValueTouchListener(new ValueTouchListener());
        presenter = new GrindEarPresenterImp(getContext(), this);
        presenter.initViewAndData();
        presenter.getDurationStatistics(timeType, locationType);
    }

    private void generateDefaultData() {
        int numSubcolumns = 2;
        int numColumns = 5;
        // Column can have many subcolumns, here by default I use 1 subcolumn in each of 8 columns.
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        for (int i = 0; i < numColumns; ++i) {

            values = new ArrayList<SubcolumnValue>();
            for (int j = 0; j < numSubcolumns; ++j) {
                if (i == 0) {
                    if (j == 0) {
                        values.add(new SubcolumnValue(grindFloat, Color.parseColor("#FF8A0D")));
                    } else {
                        values.add(new SubcolumnValue(grindAveFloat, Color.parseColor("#A1A1A1")));
                    }
                } else if (i == 1 || i == 3) {
                    values.add(new SubcolumnValue());
                } else if (i == 2) {
                    if (j == 0) {
                        values.add(new SubcolumnValue(readingFloat, Color.parseColor("#A1CA0F")));
                    } else {
                        values.add(new SubcolumnValue(readingAveFloat, Color.parseColor("#A1A1A1")));
                    }
                } else if (i == 4) {
                    if (j == 0) {
                        values.add(new SubcolumnValue(spokenFloat, Color.parseColor("#66CBFF")));
                    } else {
                        values.add(new SubcolumnValue(spokenAveFloat, Color.parseColor("#A1A1A1")));
                    }
                }
            }
            Column column = new Column(values);
            column.setHasLabels(hasLabels);
            column.setHasLabelsOnlyForSelected(hasLabelForSelected);
            columns.add(column);
        }

        data = new ColumnChartData(columns);

        if (hasAxes) {
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);
            if (hasAxesNames) {
                if (locationType == 1) {
                    axisX.setName("班级平均");
                } else if (locationType == 2) {
                    axisX.setName("全校平均");
                } else if (locationType == 3) {
                    axisX.setName("全市平均");
                } else if (locationType == 4) {
                    axisX.setName("全国平均");
                }
                axisY.setName("单位时间：分钟");
            }
            axisX.setTextColor(R.color.black);
            axisY.setTextColor(R.color.black);

            //
            List<AxisValue> listsAxis = new ArrayList<>();
            listsAxis.add(new AxisValue(0f, new char[]{'磨', '耳', '朵'}));
            listsAxis.add(new AxisValue(2.0f, new char[]{'阅', '读'}));
            listsAxis.add(new AxisValue(4.0f, new char[]{'口', '语'}));

            axisX.setValues(listsAxis);
            data.setAxisXBottom(axisX);
            data.setAxisYLeft(axisY);
        } else {
            data.setAxisXBottom(null);
            data.setAxisYLeft(null);
        }

        chartView.setColumnChartData(data);

    }

    @Override
    protected void initView(View view) {
        location = view.findViewById(R.id.census_range_spinner);
        time = view.findViewById(R.id.census_time_spinner);
        chartView = view.findViewById(R.id.chart);
        grindearDuration = view.findViewById(R.id.grindear_duration);
        grindearAverage = view.findViewById(R.id.grindear_average);
        readingDuration = view.findViewById(R.id.reading_duration);
        readingAverage = view.findViewById(R.id.reading_average);
        spokenDuration = view.findViewById(R.id.spoken_duration);
        spokenAverage = view.findViewById(R.id.spoken_average);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_census_fragment;
    }

    /**
     * {@link GrindEarPresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_GETDURATIONSTATISTICS) {
            DurationStatis durationStatis = (DurationStatis) message.obj;
            refresh(durationStatis);
//            grindFloat = grindhour + Float.parseFloat(format.format((float) (grindmin / 60)));
//            grindAveFloat = grindavehour + Float.parseFloat(format.format((float) (grindavemin / 60)));
//            readingFloat = readinghour + Float.parseFloat(format.format((float) (readingmin / 60)));
//            readingAveFloat = readingavehour + Float.parseFloat(format.format((float) (readingavemin / 60)));
//            spokenFloat = spokenhour + Float.parseFloat(format.format((float) (spokenmin / 60)));
//            spokenAveFloat = spokenavehour + Float.parseFloat(format.format((float) (spokenavemin / 60)));
            grindFloat = grindhour * 60 + grindmin;
            grindAveFloat = grindavehour * 60 + grindavemin;
            readingFloat = readinghour * 60 + readingmin;
            readingAveFloat = readingavehour * 60 + readingavemin;
            spokenFloat = spokenhour * 60 + spokenmin;
            spokenAveFloat = spokenavehour * 60 + spokenavemin;
            generateDefaultData();
        }
    }

    private void refresh(DurationStatis durationStatis) {
        grindmin = Integer.parseInt(durationStatis.getListeningDuration().equals("") ? "0" : durationStatis.getListeningDuration()) / 60;
        grindhour = 0;
        if (grindmin > 60) {
            grindhour = grindmin / 60;
            grindmin = grindmin % 60;
        }
        grindearDuration.setText("磨耳朵：" + grindhour + "小时" + grindmin + "分钟");
        grindavemin = durationStatis.getListeningAverage() / 60;
        grindavehour = 0;
        if (grindavemin > 60) {
            grindavehour = grindavemin / 60;
            grindavemin = grindavemin % 60;
        }
        if (locationType == 1) {
            grindearAverage.setText("班级平均：" + grindavehour + "小时" + grindavemin + "分钟");
        } else if (locationType == 2) {
            grindearAverage.setText("全校平均：" + grindavehour + "小时" + grindavemin + "分钟");
        } else if (locationType == 3) {
            grindearAverage.setText("全市平均：" + grindavehour + "小时" + grindavemin + "分钟");
        } else if (locationType == 4) {
            grindearAverage.setText("全国平均：" + grindavehour + "小时" + grindavemin + "分钟");
        }

        readingmin = Integer.parseInt(durationStatis.getReadingDuration().equals("") ? "0" : durationStatis.getReadingDuration()) / 60;
        readinghour = 0;
        if (readingmin > 60) {
            readinghour = readingmin / 60;
            readingmin = readingmin % 60;
        }
        readingDuration.setText("阅读：" + readinghour + "小时" + readingmin + "分钟");
        readingavemin = durationStatis.getReadingAverage() / 60;
        readingavehour = 0;
        if (readingavemin > 60) {
            readingavehour = readingavemin / 60;
            readingavemin = readingavemin % 60;
        }
        if (locationType == 1) {
            readingAverage.setText("班级平均：" + readingavehour + "小时" + readingavemin + "分钟");
        } else if (locationType == 2) {
            readingAverage.setText("全校平均：" + readingavehour + "小时" + readingavemin + "分钟");
        } else if (locationType == 3) {
            readingAverage.setText("全市平均：" + readingavehour + "小时" + readingavemin + "分钟");
        } else if (locationType == 4) {
            readingAverage.setText("全国平均：" + readingavehour + "小时" + readingavemin + "分钟");
        }

        spokenmin = Integer.parseInt(durationStatis.getSpeakingDuration().equals("") ? "0" : durationStatis.getSpeakingDuration()) / 60;
        spokenhour = 0;
        if (spokenmin > 60) {
            spokenhour = spokenmin / 60;
            spokenmin = spokenmin % 60;
        }
        spokenDuration.setText("口语：" + spokenhour + "小时" + spokenmin + "分钟");
        spokenavemin = durationStatis.getSpeakingAverage() / 60;
        spokenavehour = 0;
        if (spokenavemin > 60) {
            spokenavehour = spokenavemin / 60;
            spokenavemin = spokenavemin % 60;
        }
        if (locationType == 1) {
            spokenAverage.setText("班级平均：" + spokenavehour + "小时" + spokenavemin + "分钟");
        } else if (locationType == 2) {
            spokenAverage.setText("全校平均：" + spokenavehour + "小时" + spokenavemin + "分钟");
        } else if (locationType == 3) {
            spokenAverage.setText("全市平均：" + spokenavehour + "小时" + spokenavemin + "分钟");
        } else if (locationType == 4) {
            spokenAverage.setText("全国平均：" + spokenavehour + "小时" + spokenavemin + "分钟");
        }
    }

    @Override
    public void showInfo(String info) {
        Toast.makeText(getContext(), info, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoad() {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    @Override
    public void dismissLoad() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    private class ValueTouchListener implements ColumnChartOnValueSelectListener {

        @Override
        public void onValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value) {
//            Toast.makeText(getActivity(), value.getValue() + "", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onValueDeselected() {
            // TODO Auto-generated method stub

        }

    }
}
