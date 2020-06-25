package com.example.simpleitnews.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.simpleitnews.BaseApplication;
import com.example.simpleitnews.R;
import com.example.simpleitnews.adapter.BookmarkRvAdapter;
import com.example.simpleitnews.dagger.NewsFragmentComponent;
import com.example.simpleitnews.databinding.BookmarkFragmentBinding;
import com.example.simpleitnews.model.repository.NewsRepository;
import com.example.simpleitnews.util.ArticleNavigator;
import com.example.simpleitnews.viewModel.BookmarkViewModel;
import com.example.simpleitnews.viewModel.NewsViewModel;

import javax.inject.Inject;

public class BookmarkFragment extends Fragment {

    BookmarkViewModel mVm;
    private BookmarkFragmentBinding mBinding;

    @Inject
    NewsRepository mRepository;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.bookmark_fragment, container, false);
        mBinding.setLifecycleOwner(this);

//        NewsFragmentComponent component = ((BaseApplication) getActivity().getApplication())
//                .getAppComponent()
//                .getNewsFragmentComponent();
//        component.inject(this);

        mVm = new ViewModelProvider(this, new BookmarkViewModel.BookmarkViewModelFactory(
                mRepository, (ArticleNavigator) getActivity())).get(BookmarkViewModel.class);

        mBinding.bookmarkRv.setAdapter(new BookmarkRvAdapter(mVm));
        mBinding.setVm(mVm);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
