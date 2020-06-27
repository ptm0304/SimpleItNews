package com.example.simpleitnews.dagger.module;

import androidx.lifecycle.ViewModel;

import com.example.simpleitnews.dagger.scope.PerFragment;
import com.example.simpleitnews.model.repository.NewsRepository;
import com.example.simpleitnews.viewModel.BookmarkViewModel;
import com.example.simpleitnews.viewModel.NewsViewModel;
import com.example.simpleitnews.viewModel.ViewModelFactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

import javax.inject.Provider;

import dagger.MapKey;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;

@Module
public class ViewModelModule {
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @MapKey
    @interface ViewModelKey {
        Class<? extends ViewModel> value();
    }

    @PerFragment
    @Provides
    ViewModelFactory viewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> providerMap) {
        return new ViewModelFactory(providerMap);
    }

    @PerFragment
    @Provides
    @IntoMap
    @ViewModelKey(NewsViewModel.class)
    ViewModel provideNewsViewModel(NewsRepository repository) {
        return new NewsViewModel(repository);
    }

    @PerFragment
    @Provides
    @IntoMap
    @ViewModelKey(BookmarkViewModel.class)
    ViewModel provideBookmarkViewModel(NewsRepository repository) {
        return new BookmarkViewModel(repository);
    }
}
