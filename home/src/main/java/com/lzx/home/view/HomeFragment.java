package com.lzx.home.view;


import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableField;

import com.bumptech.glide.Glide;
import com.lzx.common.app.BaseAppcation;
import com.lzx.core.view.BaseFragment;
import com.lzx.home.R;
import com.lzx.home.databinding.LayoutHomeBinding;
import com.lzx.home.viewmodel.HomeViewModel;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoaderInterface;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends BaseFragment<LayoutHomeBinding, HomeViewModel> implements OnBannerListener {
    private Banner bannerHomeMain;
    private ViewFlipper vfMain;
    private ImageView ivAdv;

    public ObservableField<String> imgPath=new ObservableField<>();

    public HomeFragment(){
        imgPath.set("http://hbimg.b0.upaiyun.com/0cdfedffcedb13445e4def3f2d6891bb32cb03de828b-m2zK4U_fw658");
    }

    @Override
    protected void initContentView(View view) {

        initView(view);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        initData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_home;
    }

    @Override
    protected HomeViewModel createVM() {
        return new HomeViewModel();
    }

    @Override
    protected void initBinding() {
        binding.setMine(this);
    }


    private void initView(View view) {
        bannerHomeMain = (Banner) view.findViewById(R.id.banner_home_main);
        vfMain = (ViewFlipper) view.findViewById(R.id.vf_main);
        ivAdv = (ImageView) view.findViewById(R.id.iv_adv);
    }

    private void initData() {
        List<String> imgPath=new ArrayList<>();
        imgPath.add("http://hbimg.b0.upaiyun.com/0cdfedffcedb13445e4def3f2d6891bb32cb03de828b-m2zK4U_fw658");
        imgPath.add("http://hbimg.b0.upaiyun.com/8a75ab36c175489634b6c8621eea02fd8c83bb82c3869-Waz6eO_fw658");
        imgPath.add("http://hbimg.b0.upaiyun.com/a2a321fb4e128e2327674fee6c3be76bb7d6f70929ca3-IVhr33_fw658");
        imgPath.add("http://hbimg.b0.upaiyun.com/861f92e7514b297b0cd5833b3ffb52f8df37b4ec218f8-BmVyhw_fw658");

        List<String> strings=new ArrayList<>();
        strings.add("金融产品1");
        strings.add("金融产品2");
        strings.add("金融产品3");
        strings.add("金融产品4");

        bannerHomeMain.setImages(imgPath);
        bannerHomeMain.setBannerTitles(strings);
        bannerHomeMain.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        bannerHomeMain.setImageLoader(new MyBannerLoader());
        bannerHomeMain.setBannerAnimation(Transformer.Default);
        bannerHomeMain.setDelayTime(3000);
        bannerHomeMain.isAutoPlay(true);

        bannerHomeMain.setIndicatorGravity(Gravity.CENTER)
                .setOnBannerListener(this)
                .start();

        for (int i=0;i<10;i++){
            View view = getLayoutInflater().inflate(R.layout.item_viewffipper, null);
            TextView textView = view.findViewById(R.id.tv_item_ffipper);
            textView.setText("我是系统通知，内容:"+i);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView tv= (TextView) v;
                    Toast.makeText(getActivity(),""+tv.getText(),Toast.LENGTH_SHORT).show();
                }
            });
            vfMain.addView(view);
        }
        vfMain.setFlipInterval(2000);
        vfMain.startFlipping();


//        Glide.with(getActivity()).load("http://hbimg.b0.upaiyun.com/0cdfedffcedb13445e4def3f2d6891bb32cb03de828b-m2zK4U_fw658").into(ivAdv);
    }

    @Override
    public void OnBannerClick(int position) {
        Toast.makeText(getActivity(),"position="+position,Toast.LENGTH_SHORT).show();;
    }

    @BindingAdapter({"imgSrc"})
    public static void bindImgPath(ImageView view, String path){
        Glide.with(BaseAppcation.getAppContext()).load(path).into(view);
    }


    private class MyBannerLoader implements ImageLoaderInterface {

        @Override
        public void displayImage(Context context, Object path, View imageView) {
            Glide.with(context).load(path).into((ImageView) imageView);
        }

        @Override
        public View createImageView(Context context) {
            return null;
        }
    }
}