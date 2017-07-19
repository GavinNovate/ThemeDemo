# Android 主题切换的简单方案

这是一个Android主题切换的简单方案，不需要使用第三方框架，切换时不会闪屏，但需要重建界面。

首先建立3套不同颜色的主题

```xml
<resources>

    <!-- 初始主题，启动界面 -->
    <style name="AppTheme.Start" parent="Theme.AppCompat.Light.DarkActionBar">
        <item name="android:windowFullscreen">true</item>
    </style>

    <!-- 蓝色主题 -->
    <style name="AppTheme.Blue" parent="Theme.AppCompat.Light.DarkActionBar">
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>
    </style>

    <!-- 红色主题 -->
    <style name="AppTheme.Red" parent="Theme.AppCompat.Light.DarkActionBar">
        <item name="colorPrimary">#E51C23</item>
        <item name="colorPrimaryDark">#C41411</item>
        <item name="colorAccent">@color/colorAccent</item>
    </style>

    <!-- 绿色主题 -->
    <style name="AppTheme.Green" parent="Theme.AppCompat.Light.DarkActionBar">
        <item name="colorPrimary">#259B24</item>
        <item name="colorPrimaryDark">#056F00</item>
        <item name="colorAccent">@color/colorAccent</item>
    </style>

</resources>
```

建立Base Activity,实现主题切换，保存和读取功能

```java
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
```

建立Theme Activity继承Base Activity.

其中Blue,Red,Green分别是3个按钮,触发点击事件时，会去保存对应主题，然后以无动画的方式启动新的Theme Activity，可以无感切换主题。这里先卖个关子，怎么处理那么多Theme Activity实例？

然后复写返回方法，跳转到Main Activity,并清空栈。

```java
public class ThemeActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);

        findViewById(R.id.blue).setOnClickListener(this);
        findViewById(R.id.red).setOnClickListener(this);
        findViewById(R.id.green).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.blue:
                saveTheme(THEME_BLUE);
                startActivity(
                        new Intent(this, ThemeActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                break;
            case R.id.red:
                saveTheme(THEME_RED);
                startActivity(
                        new Intent(this, ThemeActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                break;
            case R.id.green:
                saveTheme(THEME_GREEN);
                startActivity(
                        new Intent(this, ThemeActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(
                new Intent(this, MainActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }
}
```

如何处理Theme Activity的多个实例？看manifest配置文件

注意，在Theme Activity上配置了no History属性，这样Theme Activity就不会被加入到后退栈中，会在用完后自动被回收。

```xml
<application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme.Start">
        <activity
            android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        <activity
            android:name=".ThemeActivity"
            android:label="@string/theme_activity"
            android:noHistory="true" />
        <activity
            android:name=".OtherActivity"
            android:label="@string/other_activity" />
    </application>
```



#### 总结

优点:

1. 不需要第三方库
2. 可以实现无感切换，不会有闪屏的烦恼

缺点:

1. 会启动新的Activity，不能达到无缝切换的目的