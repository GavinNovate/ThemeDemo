package net.novate.themedemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.theme).setOnClickListener(this);
        findViewById(R.id.other).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.theme:
                startActivity(new Intent(this, ThemeActivity.class));
                break;
            case R.id.other:
                startActivity(new Intent(this, OtherActivity.class));
                break;
        }
    }
}
