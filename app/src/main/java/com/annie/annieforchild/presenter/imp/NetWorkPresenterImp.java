package com.annie.annieforchild.presenter.imp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.annie.annieforchild.Utils.ActivityCollector;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.Banner;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.net.Address;
import com.annie.annieforchild.bean.net.Game;
import com.annie.annieforchild.bean.net.ListenAndRead;
import com.annie.annieforchild.bean.net.MyNetClass;
import com.annie.annieforchild.bean.net.NetClass;
import com.annie.annieforchild.bean.net.NetDetails;
import com.annie.annieforchild.bean.net.NetExpDetails;
import com.annie.annieforchild.bean.net.NetSuggest;
import com.annie.annieforchild.bean.net.NetWork;
import com.annie.annieforchild.bean.net.PreheatConsult;
import com.annie.annieforchild.bean.net.SpecialPreHeat;
import com.annie.annieforchild.bean.net.WechatBean;
import com.annie.annieforchild.bean.net.experience.EveryDetail;
import com.annie.annieforchild.bean.net.experience.EveryTaskList;
import com.annie.annieforchild.bean.net.experience.ExperienceV2;
import com.annie.annieforchild.bean.net.experience.ExperienceV3;
import com.annie.annieforchild.bean.net.experience.VideoFinishBean;
import com.annie.annieforchild.bean.net.netexpclass.NetExpClass;
import com.annie.annieforchild.bean.net.netexpclass.NetExp_new;
import com.annie.annieforchild.bean.order.MyOrder;
import com.annie.annieforchild.bean.order.OrderDetail;
import com.annie.annieforchild.interactor.NetWorkInteractor;
import com.annie.annieforchild.interactor.imp.NetWorkInteractorImp;
import com.annie.annieforchild.presenter.NetWorkPresenter;
import com.annie.annieforchild.ui.activity.login.LoginActivity;
import com.annie.annieforchild.ui.activity.my.WebActivity;
import com.annie.annieforchild.ui.activity.net.NetPreheatClassActivity;
import com.annie.annieforchild.ui.activity.net.NetWorkActivity;
import com.annie.annieforchild.ui.application.MyApplication;
import com.annie.annieforchild.view.GrindEarView;
import com.annie.annieforchild.view.info.ViewInfo;
import com.annie.baselibrary.base.BasePresenterImp;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.MODE_MULTI_PROCESS;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by wanglei on 2018/9/22.
 */

public class NetWorkPresenterImp extends BasePresenterImp implements NetWorkPresenter, BaseSliderView.OnSliderClickListener {
    private Context context;
    private GrindEarView grindEarView;
    private ViewInfo viewInfo;
    private NetWorkInteractor interactor;
    private List<Banner> bannerList;
    private HashMap<Integer, String> file_maps;
    private int payment, tag, position;
    private MyApplication application;

    public NetWorkPresenterImp(Context context, ViewInfo viewInfo) {
        this.context = context;
        this.viewInfo = viewInfo;
        application = (MyApplication) context.getApplicationContext();
    }

    public NetWorkPresenterImp(Context context, GrindEarView grindEarView) {
        this.context = context;
        this.grindEarView = grindEarView;
        application = (MyApplication) context.getApplicationContext();
    }

    @Override
    public void initViewAndData() {
        interactor = new NetWorkInteractorImp(context, this);
        if (grindEarView != null) {
            file_maps = grindEarView.getFile_maps();
        }
    }

    @Override
    public void getNetHomeData() {
        grindEarView.showLoad();
        interactor.getNetHomeData();
    }

    @Override
    public void getNetSuggest(int netid) {
        grindEarView.showLoad();
        interactor.getNetSuggest(netid);
    }

    @Override
    public void getMyNetClass() {
        viewInfo.showLoad();
        interactor.getMyNetClass();
    }

    @Override
    public void confirmOrder(int netid) {
        viewInfo.showLoad();
        interactor.confirmOrder(netid);
    }

    @Override
    public void getMyAddress() {
        viewInfo.showLoad();
        interactor.getMyAddress();
    }


    @Override
    public void addOrUpdateAddress(int addressid, String name, String phone, String address, String provinces) {
        viewInfo.showLoad();
        interactor.addOrUpdateAddress(addressid, name, phone, address, provinces);
    }

    @Override
    public void deleteAddress(int addressid) {
        viewInfo.showLoad();
        interactor.deleteAddress(addressid);
    }

    @Override
    public void buyNetWork(int netid, int addressid, int ismaterial, int payment, String wxnumber, String giftid, String couponid) {
        this.payment = payment;
        viewInfo.showLoad();
        interactor.buyNetWork(netid, addressid, ismaterial, payment, wxnumber, giftid, couponid);
    }

    @Override
    public void getNetDetails(int netid) {
        viewInfo.showLoad();
        interactor.getNetDetails(netid);
    }

    @Override
    public void getNetExpDetails(int netid) {
        viewInfo.showLoad();
        interactor.getNetExpDetails(netid);
    }

    @Override
    public void getNetExpDetails_new(int netid) {
        viewInfo.showLoad();
        interactor.getNetExpDetails_new(netid);
    }

    @Override
    public void getNetSpecialDetail(int netid) {
        viewInfo.showLoad();
        interactor.getNetSpecialDetail(netid);
    }

    @Override
    public void getMyOrderList() {
        viewInfo.showLoad();
        interactor.getMyOrderList();
    }

    @Override
    public void getMyOrderDetail(int orderIncrId) {
        viewInfo.showLoad();
        interactor.getMyOrderDetail(orderIncrId);
    }

    @Override
    public void continuePay(int orderIncrId, int payment, int tag) {
        this.payment = payment;
        this.tag = tag;
        viewInfo.showLoad();
        interactor.continuePay(orderIncrId, payment, tag);
    }

    @Override
    public void cancelOrder(int orderIncrId, int payment, int tag) {
        this.payment = payment;
        this.tag = tag;
        viewInfo.showLoad();
        interactor.cancelOrder(orderIncrId, payment, tag);
    }

    @Override
    public void experienceDetailsV2(int netid) {
        viewInfo.showLoad();
        interactor.experienceDetailsV2(netid);
    }

    @Override
    public void experienceDetailsV3(int netid) {
//        viewInfo.showLoad();
        interactor.experienceDetailsV3(netid);
    }

    @Override
    public void videoPayRecord(String netid, String stageid, String unitid, String chaptercontent_id, int isFinish, String classcode, int position) {
        this.position = position;
        viewInfo.showLoad();
        interactor.videoPayRecord(netid, stageid, unitid, chaptercontent_id, isFinish, classcode);
    }

    @Override
    public void videoList(String fid, String netid, String stageid, String unitid) {
        viewInfo.showLoad();
        interactor.videoList(fid, netid, stageid, unitid);
    }

    @Override
    public void SpecialClassV2(int netid) {
        viewInfo.showLoad();
        interactor.SpecialClassV2(netid);
    }

    @Override
    public void taskList(int netid) {
        viewInfo.showLoad();
        interactor.taskList(netid);
    }

    @Override
    public void taskDetail(int netid, int num) {
        viewInfo.showLoad();
        interactor.taskDetail(netid, num);
    }

    @Override
    public void getDiscountRecordList(int netid) {
        viewInfo.showLoad();
        interactor.getDiscountRecordList(netid);
    }

    @Override
    public void getLesson(String lessonid, int type) {
        viewInfo.showLoad();
        interactor.getLesson(lessonid, type);
    }

    @Override
    public void buySuccess(String tradeno, String outtradeno, int type) {
        viewInfo.showLoad();
        interactor.buySuccess(tradeno, outtradeno, type);
    }

    @Override
    public void OrderQuery(String tradeno, String outtradeno, int type, int tag) {
        this.tag = tag;
        viewInfo.showLoad();
        interactor.OrderQuery(tradeno, outtradeno, type, tag);
    }

    @Override
    public void getNetPreheatConsult(String lessonid, int type) {
        viewInfo.showLoad();
        interactor.getNetPreheatConsult(lessonid, type);
    }

    @Override
    public void getListeningAndReading(String week, String classid, int tag, int classify) {
        this.tag = tag;
        viewInfo.showLoad();
        interactor.getListeningAndReading(week, classid, tag, classify);
    }

    @Override
    public void buynum(int netid, int type) {
        if (grindEarView != null) {
            grindEarView.showLoad();
        } else if (viewInfo != null) {
            viewInfo.showLoad();
        }
        interactor.buynum(netid, type);
    }

    @Override
    public void getWeiClass(String fid, int type) {
        viewInfo.showLoad();
        interactor.getWeiClass(fid, type);
    }

    private void initImageSlide() {
        grindEarView.getImageSlide().removeAllSliders();
        for (int name : file_maps.keySet()) {
            DefaultSliderView defaultSliderView = new DefaultSliderView(context);
            defaultSliderView.bundle(new Bundle());
            defaultSliderView.getBundle().putInt("extra", name);
            defaultSliderView.setOnSliderClickListener(this);
            defaultSliderView.image(file_maps.get(name));
            grindEarView.getImageSlide().addSlider(defaultSliderView);
        }
        grindEarView.getImageSlide().movePrevPosition(false);
        grindEarView.getImageSlide().setPresetTransformer(SliderLayout.Transformer.DepthPage);
        grindEarView.getImageSlide().setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        grindEarView.getImageSlide().setCustomAnimation(new DescriptionAnimation());
        grindEarView.getImageSlide().setDuration(4000);
        if (file_maps.size() == 1) {
            grindEarView.getImageSlide().stopAutoCycle();
        }
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        if (slider.getBundle() != null) {
            if (bannerList != null) {
                if (bannerList.get(slider.getBundle().getInt("extra")) != null) {
                    if (bannerList.get(slider.getBundle().getInt("extra")).getUrl() != null) {
                        if (!bannerList.get(slider.getBundle().getInt("extra")).getUrl().equals("")) {
                            Intent intent = new Intent(context, WebActivity.class);
                            intent.putExtra("url", bannerList.get(slider.getBundle().getInt("extra")).getUrl());
                            intent.putExtra("title", "");
                            context.startActivity(intent);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void Success(int what, Object result) {
        if (grindEarView != null) {
            grindEarView.dismissLoad();
        }
        if (viewInfo != null) {
            viewInfo.dismissLoad();
        }
        if (result != null) {
            if (what == MethodCode.EVENT_GETNETHOMEDATA) {
                NetWork netWork = (NetWork) result;
                if (netWork.getBannerList() != null) {
                    bannerList = netWork.getBannerList();
                    file_maps.clear();
                    for (int i = 0; i < bannerList.size(); i++) {
                        file_maps.put(i, bannerList.get(i).getImageUrl());
                    }
                    initImageSlide();
                }
                /**
                 * {@link com.annie.annieforchild.ui.activity.net.NetWorkActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = netWork;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETNETSUGGEST) {
                NetSuggest netSuggest = (NetSuggest) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.net.NetSuggestActivity#onMainEventThread(JTMessage)}
                 */
                if (netSuggest.getTitleImageUrl() != null) {
                    List<String> img_list = new ArrayList<>();
                    img_list = netSuggest.getTitleImageUrl();
                    file_maps.clear();
                    for (int i = 0; i < img_list.size(); i++) {
                        file_maps.put(i, img_list.get(i));
                    }
                    initImageSlide();
                }
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = netSuggest;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETMYNETCLASS) {
                MyNetClass myNetClass = (MyNetClass) result;
//                List<NetClass> lists = (List<NetClass>) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.my.MyCourseActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = myNetClass;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_CONFIRMORDER) {
                NetSuggest netSuggest = (NetSuggest) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.net.ConfirmOrderActivity#onMainEventThread(JTMessage)}
                 */

                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = netSuggest;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETMYADDRESS) {
                List<Address> lists = (List<Address>) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.net.MyAddressActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = lists;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_ADDADDRESS) {
                /**
                 * {@link com.annie.annieforchild.ui.activity.net.AddAddressActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_EDITADDRESS) {
                /**
                 * {@link com.annie.annieforchild.ui.activity.net.AddAddressActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_DELETEADDRESS) {
                /**
                 * {@link com.annie.annieforchild.ui.activity.net.AddAddressActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_BUYNETWORK) {
                if (payment == 0) {
                    /**
                     * {@link com.annie.annieforchild.ui.activity.net.ConfirmOrderActivity#onMainEventThread(JTMessage)}
                     */
                    JTMessage message = new JTMessage();
                    message.what = what;
                    message.obj = result;
                    EventBus.getDefault().post(message);
                } else {
                    WechatBean wechatBean = (WechatBean) result;
                    /**
                     * {@link com.annie.annieforchild.ui.activity.net.ConfirmOrderActivity#onMainEventThread(JTMessage)}
                     */
                    JTMessage message = new JTMessage();
                    message.what = what;
                    message.obj = wechatBean;
                    EventBus.getDefault().post(message);
                }
            } else if (what == MethodCode.EVENT_GETNETDETAILS) {
                List<NetDetails> lists = (List<NetDetails>) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.net.NetDetailsActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = lists;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETLESSON) {
                List<Game> lists = (List<Game>) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.net.LessonActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = lists;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_BUYSUCCESS) {
                List<NetClass> lists = (List<NetClass>) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.net.PaySuccessActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = lists;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETNETEXPDETAILS) {
                NetExpClass netExpClass = (NetExpClass) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.net.NetExperienceDetailActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = netExpClass;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETNETEXPDETAILS_NEW) {
                NetExp_new netExpClass = (NetExp_new) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.net.NetExperienceDetail_newActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = netExpClass;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETPREHEATCONSULT) {
                PreheatConsult lists = (PreheatConsult) result;
                /**
                 * {@link NetPreheatClassActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = lists;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETLISTENANDREAD + 80000 + tag) {
                ListenAndRead listenAndRead = (ListenAndRead) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.net.NetListenAndReadActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = listenAndRead;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_BUYNUM) {
                /**
                 * {@link com.annie.annieforchild.ui.activity.net.NetSuggestActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_BUYNUM1) {
                /**
                 * {@link com.annie.annieforchild.ui.activity.net.ConfirmOrderActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_ORDERQUERY + 11000 + tag) {
                /**
                 * {@link com.annie.annieforchild.ui.activity.net.ConfirmOrderActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETWEICLASS) {
                /**
                 * {@link com.annie.annieforchild.ui.activity.net.NetExpFirstVideoActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_SPECIALCLASS) {
                /**
                 * {@link com.annie.annieforchild.ui.activity.net.NetSpecialDetailActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_SPECIALPREHEATING) {
                List<SpecialPreHeat> lists = (List<SpecialPreHeat>) result;
                /**
                 * {@link NetPreheatClassActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = lists;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETMYORDERLIST) {
                List<MyOrder> lists = (List<MyOrder>) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.my.MyOrderActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = lists;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETMYORDERDETAIL) {
                OrderDetail orderDetail = (OrderDetail) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.net.ConfirmOrderActivity2#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = orderDetail;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_CONTINUEPAY + 90000 + tag) {
                if (payment == 0) {
                    /**
                     * {@link com.annie.annieforchild.ui.activity.net.ConfirmOrderActivity2#onMainEventThread(JTMessage)}
                     */
                    JTMessage message = new JTMessage();
                    message.what = what;
                    message.obj = result;
                    EventBus.getDefault().post(message);
                } else {
                    WechatBean wechatBean = (WechatBean) result;
                    /**
                     * {@link com.annie.annieforchild.ui.activity.net.ConfirmOrderActivity2#onMainEventThread(JTMessage)}
                     */
                    JTMessage message = new JTMessage();
                    message.what = what;
                    message.obj = wechatBean;
                    EventBus.getDefault().post(message);
                }
            } else if (what == MethodCode.EVENT_CANCELORDER) {
                /**
                 * {@link com.annie.annieforchild.ui.activity.net.ConfirmOrderActivity2#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_EXPERIENCEDETAILSV2) {
                ExperienceV2 experienceV2 = (ExperienceV2) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.net.NetExperienceDetail_newActivity2#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = experienceV2;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_EXPERIENCEDETAILSV3) {
                ExperienceV3 experienceV3 = (ExperienceV3) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.net.NetExperienceDetail_newActivity3#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = experienceV3;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_VIDEOPAYRECORD) {
                VideoFinishBean videoFinishBean = (VideoFinishBean) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.net.NetExpFirstVideoActivity#onMainEventThread(JTMessage)}
                 * {@link com.annie.annieforchild.ui.activity.VideoActivity#onMainEventThread(JTMessage)}
                 * {@link com.annie.annieforchild.ui.activity.net.NetExperienceDetail_newActivity2#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = videoFinishBean;
                message.obj2 = position;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_VIDEOLIST) {
                /**
                 * {@link com.annie.annieforchild.ui.activity.net.NetExpFirstVideoActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_SPECIALCLASSV2) {
                ExperienceV2 experienceV2 = (ExperienceV2) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.net.NetSpecialDetailActivity2#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;


                message.obj = experienceV2;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_TASKLIST) {
                EveryTaskList everyTaskList = (EveryTaskList) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.net.EveryDayTaskActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = everyTaskList;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_TASKDETAIL) {
                EveryDetail everyDetail = (EveryDetail) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.net.EveryDayTaskActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = everyDetail;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_DISCOUNTRECORD) {
                /**
                 * {@link com.annie.annieforchild.ui.activity.my.MyCouponActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            }
        }
    }

    @Override
    public void Error(int what, int status, String error) {
        if (grindEarView != null) {
            grindEarView.dismissLoad();
        }
        if (viewInfo != null) {
            viewInfo.dismissLoad();
        }
        if (status == 1) {
            //该账号已在别处登陆
            if (!application.getSystemUtils().isReLogin()) {
                application.getSystemUtils().setReLogin(true);
                if (grindEarView != null) {
                    grindEarView.showInfo(error);
                }
                if (viewInfo != null) {
                    viewInfo.showInfo(error);
                }
                JTMessage message = new JTMessage();
                message.what = MethodCode.EVENT_RELOGIN;
                message.obj = 1;
                EventBus.getDefault().post(message);

                SharedPreferences preferences = context.getSharedPreferences("userInfo", MODE_PRIVATE | MODE_MULTI_PROCESS);
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("phone");
                editor.remove("psd");
                editor.commit();
                application.getSystemUtils().getPhoneSN().setUsername(null);
                application.getSystemUtils().getPhoneSN().setLastlogintime(null);
                application.getSystemUtils().getPhoneSN().setSystem(null);
                application.getSystemUtils().getPhoneSN().setBitcode(null);
                application.getSystemUtils().setDefaultUsername(null);
                application.getSystemUtils().setToken(null);
                application.getSystemUtils().getPhoneSN().save();
                application.getSystemUtils().setOnline(false);
                ActivityCollector.finishAll();
                Intent intent2 = new Intent(context, LoginActivity.class);
                context.startActivity(intent2);
                return;
            } else {
                return;
            }
        } else if (status == 2) {
            //升级

        } else if (status == 3) {
            //参数错误
            Toast.makeText(application.getApplicationContext(), error, Toast.LENGTH_SHORT).show();
            SystemUtils.setDefaltSn(context, application);
        } else if (status == 4) {
            //服务器错误

        } else if (status == 5) {
            //账号或密码错误

        } else if (status == 6) {
            //获取验证码失败

        } else if (status == 7) {
            if (what == MethodCode.EVENT_CANCELORDER + 100000 + tag) {
                Toast.makeText(application.getApplicationContext(), error, Toast.LENGTH_SHORT).show();
            } else if (what == MethodCode.EVENT_BUYNETWORK) {
                Toast.makeText(application.getApplicationContext(), error, Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void Fail(int what, String error) {
        if (grindEarView != null) {
            grindEarView.dismissLoad();
        }
        if (viewInfo != null) {
            viewInfo.dismissLoad();
        }
        Toast.makeText(application.getApplicationContext(), error, Toast.LENGTH_SHORT).show();
    }
}
