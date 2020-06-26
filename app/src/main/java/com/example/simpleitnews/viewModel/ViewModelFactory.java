package com.example.simpleitnews.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import javax.inject.Inject;
import javax.inject.Provider;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private final Provider<NewsViewModel> mNewsViewModelProvider;
    private final Provider<BookmarkViewModel> mBookmarkViewModelProvider;

    @Inject
    public ViewModelFactory(Provider<NewsViewModel> newsViewModelProvider, Provider<BookmarkViewModel> bookmarkViewModelProvider) {
        mNewsViewModelProvider= newsViewModelProvider;
        mBookmarkViewModelProvider = bookmarkViewModelProvider;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        ViewModel viewModel;
        if (modelClass == NewsViewModel.class) {
            viewModel = mNewsViewModelProvider.get();
        }
        else if (modelClass == BookmarkViewModel.class) {
            viewModel = mBookmarkViewModelProvider.get();
        }
        else {
            throw new RuntimeException("unsupported view model class: " + modelClass);
        }

        return (T) viewModel;
    }
}
