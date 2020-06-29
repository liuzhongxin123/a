package com.lzx.baweiproject.bawei6;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lzx.account.AccountFragment;
import com.lzx.baweiproject.R;
import com.lzx.baweiproject.adapter.FragmentViewPagerAdapter;
import com.lzx.finalce.FinalceFragment;
import com.lzx.home.view.HomeFragment;
import com.lzx.more.MoreFragment;
import com.lzx.wiget.TitleBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bnvBottombar;
    private ViewPager vpMain;
    private List<Fragment> fragments;
    private TitleBar tbMain;
    private DrawerLayout dlMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
        initEvent();
    }

    private void initData() {
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new FinalceFragment());
        fragments.add(new AccountFragment());
        fragments.add(new MoreFragment());

        FragmentViewPagerAdapter adapter=new FragmentViewPagerAdapter(getSupportFragmentManager(),fragments);
        vpMain.setAdapter(adapter);
    }

    private void initEvent() {
        /**
         * 底部菜单切换事件
         */
        bnvBottombar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.menu_bottombar_home:
                        vpMain.setCurrentItem(0);
                        setLeftRightVisible(true);
                        return true;
                    case R.id.menu_bottombar_finalce:

                        vpMain.setCurrentItem(1);
                        setLeftRightVisible(false);
                        return true;
                    case R.id.menu_bottombar_account:
                        vpMain.setCurrentItem(2);
                        setLeftRightVisible(false);
                        return true;
                    case R.id.menu_bottombar_more:
                        vpMain.setCurrentItem(3);
                        setLeftRightVisible(false);
                        return true;
                }
                return false;
            }
        });

        vpMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                bnvBottombar.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tbMain.setTitleBarClickListener(new TitleBar.TitleBarClickListener() {
            @Override
            public void leftClick(View view) {
                dlMain.openDrawer(Gravity.LEFT);
            }

            @Override
            public void rightClick(View view) {
                Toast.makeText(MainActivity.this,"rifht click",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        bnvBottombar = (BottomNavigationView) findViewById(R.id.bnv_bottombar);
        vpMain = (ViewPager) findViewById(R.id.vp_main);
        tbMain = (TitleBar) findViewById(R.id.tb_main);
        dlMain = (DrawerLayout) findViewById(R.id.dl_main);
        dlMain.setScrimColor(Color.TRANSPARENT);
    }

    private void setLeftRightVisible(boolean isvisible){
        tbMain.setLeftVisible(isvisible);
        tbMain.setRightVisible(isvisible);
    }
}