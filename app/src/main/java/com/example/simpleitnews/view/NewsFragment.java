package com.example.simpleitnews.view;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.simpleitnews.BaseApplication;
import com.example.simpleitnews.R;
import com.example.simpleitnews.adapter.NewsRvAdapter;
import com.example.simpleitnews.dagger.NewsFragmentComponent;
import com.example.simpleitnews.databinding.NewsFragmentBinding;
import com.example.simpleitnews.util.ArticleNavigator;
import com.example.simpleitnews.viewModel.NewsViewModel;
import com.example.simpleitnews.viewModel.ViewModelFactory;

import javax.inject.Inject;

public class NewsFragment extends Fragment {

    private NewsFragmentBinding mBinding;
    @Inject
    ViewModelFactory mVmFactory;
    NewsViewModel mVm;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.news_fragment, container, false);
        mBinding.setLifecycleOwner(this);

        NewsFragmentComponent component = ((BaseApplication) getActivity().getApplication())
                .getAppComponent()
                .getNewsFragmentComponent();
        component.inject(this);

        mVm = new ViewModelProvider(this, mVmFactory).get(NewsViewModel.class);
        mBinding.setVm(mVm);

        NewsRvAdapter rvAdapter = new NewsRvAdapter(mVm, (ArticleNavigator) getActivity());
        mBinding.mainRv.setAdapter(rvAdapter);
        mBinding.mainRv.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (!mBinding.mainRv.canScrollVertically(1)) {
                    mVm.loadMoreNews();
                }
            }
        });

        mVm.getBookmarkLiveData().observe(this.getViewLifecycleOwner(), hashSet -> rvAdapter.notifyDataSetChanged());


        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

}
