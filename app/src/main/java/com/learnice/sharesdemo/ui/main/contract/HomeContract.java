package com.learnice.sharesdemo.ui.main.contract;

import com.learnice.base_library.base.BaseModel;
import com.learnice.base_library.base.BasePresenter;
import com.learnice.base_library.base.BaseView;
import com.learnice.sharesdemo.bean.NewsListResponse;
import com.learnice.sharesdemo.bean.NewsBean;

import java.util.List;

import rx.Observable;

/**
 * Created by Xuebin He on 2017/5/16.
 * e-mail:learnice.he@gmail.com.
 */

public interface HomeContract {
    interface Model extends BaseModel{

        Observable<NewsListResponse<NewsBean>>  loadNews();
    }
    interface View extends BaseView{

        void setNewsData(List<NewsBean> data);
    }
    interface Presenter extends BasePresenter{

        void loadNews();
    }
}
