package com.example.deneme.ui;

import androidx.fragment.app.ListFragment;

public class HeadlinesFragment extends ListFragment {
    OnHeadlineSelectedListener callback;

    public void setOnHeadlineSelectedListener(OnHeadlineSelectedListener callback) {
        this.callback = callback;
    }
    public interface OnHeadlineSelectedListener {
        public void onArticleSelected(int position);
    }

}