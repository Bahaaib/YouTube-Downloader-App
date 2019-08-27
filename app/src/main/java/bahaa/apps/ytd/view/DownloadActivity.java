package bahaa.apps.ytd.view;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

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

    @BindView(R.id.link_input_layout)
    TextInputLayout linkInputLayout;

    @BindView(R.id.link_text)
    TextInputEditText linkEditText;

    @BindView(R.id.download_btn)
    AppCompatButton downloadButton;

    @BindView(R.id.main_layout)
    LinearLayout linearLayout;

    @BindDrawable(R.drawable.button_background)
    Drawable buttonBackground;

    private VideoFile tempFile;
    private ProgressDialog progressDialog;
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

    private boolean isStoragePermissionGranted() {

        int result = ContextCompat.checkSelfPermission(DownloadActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(DownloadActivity.this
                , new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
    }

    private void displayToast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();

    }

    @Override
    public void showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching Available Quality..");
        progressDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        progressDialog.dismiss();
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
                if (!isStoragePermissionGranted()) {
                    requestStoragePermission();
                    tempFile = file;
                } else {
                    presenter.beginDownload(
                            file.getFile().getUrl(),
                            file.getMetaTitle(),
                            file.getFileName());
                    displayToast("Download started");
                }


            });


            linearLayout.addView(button);
        }
    }

    @Override
    public void showErrorMessage() {
        linkInputLayout.setError("Invalid YouTube link");
    }

    @Override
    public void showNoVideoToast() {
        displayToast("Video NOT Found");
    }

    @OnClick(R.id.download_btn)
    void pressButton() {
        String link = linkEditText.getText().toString();
        presenter.validateInputLink(link);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
            displayToast("Permission Denied!");
        } else {
            presenter.beginDownload(
                    tempFile.getFile().getUrl(),
                    tempFile.getMetaTitle(),
                    tempFile.getFileName()
            );
        }
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
