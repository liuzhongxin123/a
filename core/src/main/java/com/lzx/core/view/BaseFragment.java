package com.lzx.core.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.lzx.core.viewmodel.BaseViewModel;

public abstract class BaseFragment<Binding extends ViewDataBinding,VM extends BaseViewModel> extends Fragment {

    protected Binding binding;
    protected VM vm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        View view = binding.getRoot();
        vm=createVM();
        initContentView(view);
        initBinding();
        return view;
    }

    /**
     * 初始化视图
     * @param view
     */
    protected abstract void initContentView(View view);


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData(savedInstanceState);
    }

    /**
     * 初始化数据
     * @param savedInstanceState
     */
    protected abstract void initData(Bundle savedInstanceState);

    /**
     * 获取布局id
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 创建VM
     * @return
     */
    protected abstract VM createVM();

    /**
     * 初始化绑定
     */
    protected abstract void initBinding();
}