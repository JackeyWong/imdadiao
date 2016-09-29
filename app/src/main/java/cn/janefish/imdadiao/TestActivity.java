package cn.janefish.imdadiao;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import cn.janefish.imdadiao.common.IntervalHandlerManager;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }
    private void interval() {
        IntervalHandlerManager.getInstance().postInterval(new Runnable() {
            @Override
            public void run() {
                Log.e("wwww", "interval invoking.....");
                interval();
            }
        }, 2000);
    }
    @Override
    protected void onDestroy() {
        Log.e("wwww", "test destroy------");
        super.onDestroy();
    }
    public void onTest(View v) {
        interval();
        finish();
    }

}
