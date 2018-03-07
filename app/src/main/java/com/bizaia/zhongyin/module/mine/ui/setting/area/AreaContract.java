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

package com.bizaia.zhongyin.module.mine.ui.setting.area;

import com.bizaia.zhongyin.base.BasePresenter;
import com.bizaia.zhongyin.module.common.ui.DataView;
import com.bizaia.zhongyin.module.mine.data.AreaData;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface AreaContract {
    interface View<T> extends DataView<T, AreaContract.Presenter> {
    }


    interface Presenter extends BasePresenter {
        void getArea();
    }
}
