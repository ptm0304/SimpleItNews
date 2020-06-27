package com.example.simpleitnews.dagger;

import com.example.simpleitnews.dagger.module.ViewModelModule;
import com.example.simpleitnews.dagger.scope.PerFragment;
import com.example.simpleitnews.model.repository.NewsRepository;
import com.example.simpleitnews.view.BookmarkFragment;
import com.example.simpleitnews.view.NewsFragment;

import dagger.Subcomponent;

@PerFragment
@Subcomponent(modules = ViewModelModule.class)
public interface NewsFragmentComponent {
    void inject(NewsFragment fragment);
    void inject(BookmarkFragment fragment);
    void inject(NewsRepository repository);
}
