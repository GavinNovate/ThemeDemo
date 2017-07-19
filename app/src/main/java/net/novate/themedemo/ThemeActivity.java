package net.novate.themedemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
