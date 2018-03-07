package com.bizaia.zhongyin.module.launch;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.base.BaseActivity;
import com.bizaia.zhongyin.module.mine.ui.setting.area.AreaActivity;
import com.bizaia.zhongyin.util.PreferencesUtils;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LauncherActivity extends BaseActivity {

    @BindView(R.id.ivLauncher)
    ImageView ivLauncher;

    private int pos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        ButterKnife.bind(this);
        final int[] res = {R.drawable.launcher_1, R.drawable.launcher_2,
                R.drawable.launcher_4, R.drawable.launcher_5,R.drawable.launcher_6};
        ivLauncher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pos < res.length - 1) {
                    pos++;
                    ivLauncher.setImageResource(res[pos]);
                } else {
                    PreferencesUtils.setIsGuided(getBaseContext(), "FlushActivity");
//                    startActivity(new Intent(LauncherActivity.this, MainActivity.class));
                    AreaActivity.show(LauncherActivity.this,true);
                    finish();
                }
            }
        });
    }


}
