/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bizaia.zhongyin.module.live.ui.search;


import com.bizaia.zhongyin.base.BasePresenter;
import com.bizaia.zhongyin.base.BaseView;
import com.bizaia.zhongyin.module.common.data.SearchNavData;
import com.bizaia.zhongyin.module.live.data.LiveNewBean;

import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface SearchContract {

    interface View extends BaseView<Presenter> {
        void setSearchNav(SearchNavData searchNav);

        void setData(LiveNewBean data);
        void setData(List<LiveNewBean.DataEntity.LiveListEntity.DatasEntity> data);

        void netError();
        void dataError(String error);

        void reLogin();
    }

    interface Presenter extends BasePresenter {
        void getSearchNav();

        void getSearchResult(int pageNum, int pageSize, String queryStr,int type,String label);

        void getLiving(int pageNum, int pageSize, long cateId);
    }
}
