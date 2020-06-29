package com.lzx.usercenter.view;


import android.text.TextUtils;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.lzx.common.Utils.MsgUtils;
import com.lzx.core.view.BaseActivity;
import com.lzx.net.protocol.BaseRespEntity;
import com.lzx.usercenter.R;
import com.lzx.usercenter.databinding.ActivityLoginBinding;
import com.lzx.usercenter.entity.UserEntity;
import com.lzx.usercenter.viewmodel.UserViewModel;
import com.lzx.wiget.TitleBar;

public class LoginActivity extends BaseActivity<ActivityLoginBinding, UserViewModel> {
    private final String TAG=LoginActivity.class.getSimpleName();
    private TitleBar titleBar;
    @Override
    protected void initBinding() {
        binding.setVm(vm);
        binding.setCommand(this);
        titleBar=findViewById(R.id.tb_bar);
        titleBar.setTitleBarClickListener(new TitleBar.TitleBarClickListener() {
            @Override
            public void leftClick(View view) {
                MsgUtils.INSTANCE.show(LoginActivity.this,"left");
            }

            @Override
            public void rightClick(View view) {
                MsgUtils.INSTANCE.show(LoginActivity.this,"right");
            }
        });
    }

    @Override
    protected UserViewModel createVM() {
        return new UserViewModel();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    /**
     * 登录按钮的点击事件
     * @param view
     */
    public void loginClick(View view){
        String username = vm.userEntity.getUsername();
        String pwd = vm.userEntity.getPwd();
        if (TextUtils.isEmpty(username)){
            showMsg(getResourceString(R.string.user_hint_input_username));
            return;
        }
        if (TextUtils.isEmpty(pwd)){
            showMsg(getResourceString(R.string.user_hint_input_pwd));
            return;
        }

        LiveData<BaseRespEntity<UserEntity>> result = vm.login();

//        if(result.getValue()){
//            showMsg(getResourceString(R.string.user_login_success));
//            return;
//        }
//        else{
//            showMsg(getResourceString(R.string.user_login_failed));
//        }

        result.observe(this, new Observer<BaseRespEntity<UserEntity>>() {
            @Override
            public void onChanged(BaseRespEntity<UserEntity> userEntityBaseRespEntity) {
                if (userEntityBaseRespEntity!=null&&userEntityBaseRespEntity.getData()!=null){
                    showMsg(getResourceString(R.string.user_login_success));
                }else{
                    showMsg(getResourceString(R.string.user_login_failed));
                }
            }
        });

    }

    public void changeUrlClick(View view){
//        UserApi userApi = RetrofitFactory.getInstance().create(UserApi.class);
//        userApi.getTest().enqueue(new Callback<TokenRespEntity>() {
//            @Override
//            public void onResponse(Call<TokenRespEntity> call, Response<TokenRespEntity> response) {
//                LogUtils.INSTANCE.i(TAG,"Request SUCCESS");
//            }
//
//            @Override
//            public void onFailure(Call<TokenRespEntity> call, Throwable t) {
//                LogUtils.INSTANCE.e(TAG,t);
//            }
//        });
    }
}