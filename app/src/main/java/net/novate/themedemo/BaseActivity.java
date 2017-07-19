package net.novate.themedemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by gavin on 2017/7/19.
 */

public class BaseActivity extends AppCompatActivity {

    public static final String SHARED_NAME = "theme";
    public static final String SHARED_KEY_THEME = "theme";

    public static final int THEME_BLUE = 0;
    public static final int THEME_RED = 1;
    public static final int THEME_GREEN = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch (readTheme()) {
            case THEME_BLUE:
                setTheme(R.style.AppTheme_Blue);
                break;
            case THEME_RED:
                setTheme(R.style.AppTheme_Red);
                break;
            case THEME_GREEN:
                setTheme(R.style.AppTheme_Green);
                break;
        }
    }

    /**
     * 保存主题到 SharedPreferences
     *
     * @param theme 主题编号
     * @return 操作成果
     */
    public boolean saveTheme(int theme) {
        return getSharedPreferences(SHARED_NAME, MODE_PRIVATE)
                .edit()
                .putInt(SHARED_KEY_THEME, theme)
                .commit();
    }

    /**
     * 从 SharedPreferences 读取主题
     *
     * @return 主题编号
     */
    public int readTheme() {
        return getSharedPreferences(SHARED_NAME, MODE_PRIVATE)
                .getInt(SHARED_KEY_THEME, THEME_BLUE);
    }
}
