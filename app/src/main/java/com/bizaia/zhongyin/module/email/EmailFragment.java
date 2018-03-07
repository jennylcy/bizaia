package com.bizaia.zhongyin.module.email;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.base.ResumeFragment;

/**
 * Created by x on 18-3-5.
 */

public class EmailFragment extends ResumeFragment {

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_email, container, false);
        return null;
    }

    public static EmailFragment newInstance(){
        return new EmailFragment();
    }
}
