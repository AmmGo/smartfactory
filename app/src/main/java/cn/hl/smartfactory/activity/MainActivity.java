package cn.hl.smartfactory.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.xuexiang.xui.adapter.FragmentAdapter;
import com.xuexiang.xui.utils.ResUtils;
import com.xuexiang.xutil.XUtil;
import com.xuexiang.xutil.common.ClickUtils;
import com.xuexiang.xutil.common.CollectionUtils;

import butterknife.BindView;
import cn.hl.smartfactory.R;
import cn.hl.smartfactory.core.BaseActivity;
import cn.hl.smartfactory.core.BaseFragment;
import cn.hl.smartfactory.fragment.home.HomeFragment;
import cn.hl.smartfactory.fragment.message.MessageFragment;
import cn.hl.smartfactory.fragment.profile.ProfileFragment;
import cn.hl.smartfactory.fragment.work.WorkFragment;
import cn.hl.smartfactory.utils.XToastUtils;
import cn.hl.smartfactory.widget.GuideTipsDialog;

/**
 * 程序主页面,只是一个简单的Tab例子
 *
 * @author xuexiang
 * @since 2019-07-07 23:53
 */
public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener, ClickUtils.OnClick2ExitListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    /**
     * 底部导航栏
     */
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigation;
    private String[] mTitles;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViews();
        initListeners();
    }

    @Override
    protected boolean isSupportSlideBack() {
        return false;
    }

    private void initViews() {
        mTitles = ResUtils.getStringArray(R.array.home_titles);
        toolbar.setTitle("");
        toolbar_title.setText(mTitles[0]);
        //主页内容填充
        BaseFragment[] fragments = new BaseFragment[]{
                new HomeFragment(),
                new WorkFragment(),
                new MessageFragment(),
                new ProfileFragment()
        };
        FragmentAdapter<BaseFragment> adapter = new FragmentAdapter<>(getSupportFragmentManager(), fragments);
        viewPager.setOffscreenPageLimit(mTitles.length - 1);
        viewPager.setAdapter(adapter);
        GuideTipsDialog.showTips(this);
    }

    protected void initListeners() {
        //主页事件监听
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                MenuItem item = bottomNavigation.getMenu().getItem(position);
                toolbar.setTitle("");
                toolbar_title.setText(item.getTitle());
                item.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        bottomNavigation.setOnNavigationItemSelectedListener(this);
    }

    //================Navigation================//

    /**
     * 底部导航栏点击事件
     *
     * @param menuItem
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int index = CollectionUtils.arrayIndexOf(mTitles, menuItem.getTitle());
        if (index != -1) {
            toolbar.setTitle(menuItem.getTitle());
            viewPager.setCurrentItem(index, false);
            return true;
        }
        return false;
    }

    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ClickUtils.exitBy2Click(2000, this);
        }
        return true;
    }

    @Override
    public void onRetry() {
        XToastUtils.toast("再按一次退出程序");
    }

    @Override
    public void onExit() {
        XUtil.exitApp();
    }


}
