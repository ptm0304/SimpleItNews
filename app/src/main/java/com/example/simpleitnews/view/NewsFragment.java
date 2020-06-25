package com.example.simpleitnews.view;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.simpleitnews.BaseApplication;
import com.example.simpleitnews.R;
import com.example.simpleitnews.adapter.NewsRvAdapter;
import com.example.simpleitnews.dagger.NewsFragmentComponent;
import com.example.simpleitnews.databinding.NewsFragmentBinding;
import com.example.simpleitnews.model.repository.NewsRepository;
import com.example.simpleitnews.util.ArticleNavigator;
import com.example.simpleitnews.viewModel.NewsViewModel;

import javax.inject.Inject;

public class NewsFragment extends Fragment {

    private NewsFragmentBinding mBinding;
//    @Inject NewsViewModel.NewsViewModelFactory mVmFactory;
    @Inject NewsRepository mRepository;
    NewsViewModel mVm;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.news_fragment, container, false);
        mBinding.setLifecycleOwner(this);

//        NewsFragmentComponent component = ((BaseApplication) getActivity().getApplication())
//                .getAppComponent()
//                .getNewsFragmentComponent();
//        component.inject(this);

        mVm = new ViewModelProvider(this, new NewsViewModel.NewsViewModelFactory(
                mRepository, (ArticleNavigator) getActivity())).get(NewsViewModel.class);

        mBinding.mainRv.setAdapter(new NewsRvAdapter(mVm));
        mBinding.setVm(mVm);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

}
