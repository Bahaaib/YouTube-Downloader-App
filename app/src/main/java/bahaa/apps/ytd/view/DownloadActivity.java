package bahaa.apps.ytd.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import javax.inject.Inject;

import bahaa.apps.ytd.ApplicationInstance;
import bahaa.apps.ytd.R;
import bahaa.apps.ytd.VideoFile;
import bahaa.apps.ytd.contracts.Download;
import bahaa.apps.ytd.root.components.DaggerActivityComponent;
import bahaa.apps.ytd.root.modules.DownloadModule;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DownloadActivity extends AppCompatActivity implements Download.View {

    @Inject
    Download.Presenter presenter;

    @Inject
    Context context;

    @BindView(R.id.link_text)
    TextInputEditText linkEditText;

    @BindView(R.id.download_btn)
    AppCompatButton downloadButton;

    @BindView(R.id.main_layout)
    LinearLayout linearLayout;

    @BindDrawable(R.drawable.button_background)
    Drawable buttonBackground;

    private Unbinder unbinder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        DaggerActivityComponent.builder()
                .appComponent(ApplicationInstance.get(this).getComponent())
                .downloadModule(new DownloadModule(this))
                .build()
                .inject(this);

        initViews();

    }

    void initViews() {
        unbinder = ButterKnife.bind(this);
    }

    int getDP(float pixels) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                pixels,
                context.getResources().getDisplayMetrics());
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void addQualityButtons(ArrayList<VideoFile> fileList) {

        if (linearLayout.getChildCount() > 0) {
            linearLayout.removeAllViews();
        }

        for (VideoFile file : fileList) {
            Button button = new Button(this);
            button.setBackground(buttonBackground);
            button.setText(file.getButtonText());
            button.setTextColor(getResources().getColor(R.color.colorPrimaryLight));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(getDP(10), getDP(10), getDP(10), getDP(10));
            button.setLayoutParams(params);

            button.setOnClickListener(v -> {
                Log.i("statuss", "I'm Going to download");
            });

            linearLayout.addView(button);
        }
    }

    @Override
    public void showErrorDialog() {

    }

    @Override
    public void showNoVideoToast() {

    }

    @OnClick(R.id.download_btn)
    void pressButton() {
        String link = linkEditText.getText().toString();
        presenter.validateInputLink(link);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

}
