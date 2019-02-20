package com.annie.annieforchild.presenter.imp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.annie.annieforchild.Utils.MethodCode;
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
import com.annie.annieforchild.bean.net.WechatBean;
import com.annie.annieforchild.bean.net.netexpclass.NetExpClass;
import com.annie.annieforchild.interactor.NetWorkInteractor;
import com.annie.annieforchild.interactor.imp.NetWorkInteractorImp;
import com.annie.annieforchild.presenter.NetWorkPresenter;
import com.annie.annieforchild.ui.activity.my.WebActivity;
import com.annie.annieforchild.ui.activity.net.NetPreheatClassActivity;
import com.annie.annieforchild.ui.activity.net.NetWorkActivity;
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
    private int payment;

    public NetWorkPresenterImp(Context context, ViewInfo viewInfo) {
        this.context = context;
        this.viewInfo = viewInfo;
    }

    public NetWorkPresenterImp(Context context, GrindEarView grindEarView) {
        this.context = context;
        this.grindEarView = grindEarView;
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
    public void addAddress(String name, String phone, String address) {
        viewInfo.showLoad();
        interactor.addAddress(name, phone, address);
    }

    @Override
    public void editAddress(int addressid, String name, String phone, String address) {
        viewInfo.showLoad();
        interactor.editAddress(addressid, name, phone, address);
    }

    @Override
    public void deleteAddress(int addressid) {
        viewInfo.showLoad();
        interactor.deleteAddress(addressid);
    }

    @Override
    public void buyNetWork(int netid, int addressid, int ismaterial, int payment, String giftid) {
        this.payment = payment;
        viewInfo.showLoad();
        interactor.buyNetWork(netid, addressid, ismaterial, payment, giftid);
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
    public void getLesson(String lessonid) {
        viewInfo.showLoad();
        interactor.getLesson(lessonid);
    }

    @Override
    public void buySuccess() {
        viewInfo.showLoad();
        interactor.buySuccess();
    }
    @Override
    public void OrderQuery(String tradeno,String outtradeno,int type) {
        viewInfo.showLoad();
        interactor.OrderQuery(tradeno,outtradeno,type);
    }

    @Override
    public void getNetPreheatConsult(String lessonid) {
        viewInfo.showLoad();
        interactor.getNetPreheatConsult(lessonid);
    }

    @Override
    public void getListeningAndReading(String week, String classid) {
        viewInfo.showLoad();
        interactor.getListeningAndReading(week, classid);
    }

    @Override
    public void buynum(int netid,int type) {
        if(grindEarView!=null){
            grindEarView.showLoad();
        }else if(viewInfo!=null){
            viewInfo.showLoad();
        }
        interactor.buynum(netid,type);
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
        if (!bannerList.get(slider.getBundle().getInt("extra")).getUrl().equals("")) {
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra("url", bannerList.get(slider.getBundle().getInt("extra")).getUrl());
                intent.putExtra("title", "");
                context.startActivity(intent);
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
            } else if (what == MethodCode.EVENT_GETPREHEATCONSULT) {
                PreheatConsult lists = (PreheatConsult) result;
                /**
                 * {@link NetPreheatClassActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = lists;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETLISTENANDREAD) {
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
            }else if (what == MethodCode.EVENT_BUYNUM1) {
                /**
                 * {@link com.annie.annieforchild.ui.activity.net.ConfirmOrderActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            }else if (what == MethodCode.EVENT_ORDERQUERY) {
                /**
                 * {@link com.annie.annieforchild.ui.activity.net.ConfirmOrderActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            }
        }
    }

    @Override
    public void Error(int what, String error) {
        super.Error(what, error);
        if (grindEarView != null) {
            grindEarView.dismissLoad();
        }
        if (viewInfo != null) {
            viewInfo.dismissLoad();
            viewInfo.showInfo(error);
        }
    }
}
