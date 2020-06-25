package com.example.simpleitnews.view;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.simpleitnews.R;
import com.example.simpleitnews.adapter.NewsRvAdapter;
import com.example.simpleitnews.dagger.DaggerDbComponent;
import com.example.simpleitnews.dagger.DbComponent;
import com.example.simpleitnews.dagger.DbModule;
import com.example.simpleitnews.databinding.NewsFragmentBinding;
import com.example.simpleitnews.model.repository.NewsRepository;
import com.example.simpleitnews.viewModel.NewsViewModel;

public class NewsFragment extends Fragment {

    private NewsFragmentBinding mBinding;
    private NewsViewModel mVm;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.news_fragment, container, false);
        mBinding.setLifecycleOwner(this);

        NewsRepository newsRepository = new NewsRepository();
        DbComponent comp = DaggerDbComponent.builder().DbModule(new DbModule(getContext())).build();
        comp.inject(newsRepository);

        mVm = new ViewModelProvider(this, new NewsViewModel.NewsViewModelFactory(
                newsRepository, (MainActivityKt) getActivity())).get(NewsViewModel.class);

        mBinding.mainRv.setAdapter(new NewsRvAdapter(mVm));
        mBinding.setVm(mVm);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

}
