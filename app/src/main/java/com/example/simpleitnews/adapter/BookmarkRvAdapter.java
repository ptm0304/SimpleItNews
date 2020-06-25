package com.example.simpleitnews.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpleitnews.R;
import com.example.simpleitnews.databinding.RvNewsItemBinding;
import com.example.simpleitnews.model.dto.NewsDto;
import com.example.simpleitnews.viewModel.BookmarkViewModel;

import java.util.List;

public class BookmarkRvAdapter extends RecyclerView.Adapter<BookmarkRvAdapter.BookmarkViewHolder>{
    private LiveData<List<NewsDto>> mNews;
    private BookmarkViewModel mVm;

    public BookmarkRvAdapter(BookmarkViewModel vm){
        mVm = vm;
    }

    @NonNull
    @Override
    public BookmarkRvAdapter.BookmarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RvNewsItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(
                parent.getContext()), R.layout.rv_news_item, parent, false);
        return new BookmarkViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkRvAdapter.BookmarkViewHolder holder, int position) {
        NewsDto currentNews = mNews.getValue().get(position);
        holder.mBinding.setNews(currentNews);
        holder.setNews(currentNews);
    }

    @Override
    public int getItemCount() {
        if (mNews == null || mNews.getValue() == null) return 0;
        return mNews.getValue().size();
    }

    @BindingAdapter("bookmark")
    public static void bindBookmark(RecyclerView recyclerView, LiveData<List<NewsDto>> news) {
        BookmarkRvAdapter adapter = (BookmarkRvAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.setNews(news);
        }
    }

    private void setNews(LiveData<List<NewsDto>> news) {
        this.mNews = news;
        notifyDataSetChanged();
    }

    class BookmarkViewHolder extends RecyclerView.ViewHolder {
        private RvNewsItemBinding mBinding;
        private NewsDto mNews;
        public BookmarkViewHolder(@NonNull RvNewsItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            View view = binding.getRoot();

            // 뉴스 클릭시 기사보기 웹뷰로 이동
            view.setOnClickListener(v -> {
                mVm.navigateArticle(mNews);
            });

            // 북마크 클릭시 북마크 추가
            mBinding.mainNewsBookmark.setOnClickListener(v -> {
                mNews.setBookmark(!mNews.isBookmark());
                mVm.updateNews(mNews);
            });
        }

        private void setNews(NewsDto news) {
            mNews = news;
            setBookmark();
        }

        private void setBookmark() {
            if (mNews.isBookmark()) {
                mBinding.mainNewsBookmark.setImageResource(R.drawable.ic_bookmark_red_24dp);
            }
            else {
                mBinding.mainNewsBookmark.setImageResource(R.drawable.ic_bookmark_border_red_24dp);
            }
        }
    }
}
