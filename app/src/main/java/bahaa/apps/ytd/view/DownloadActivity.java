package bahaa.apps.ytd.view;

import android.content.Context;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.textfield.TextInputEditText;

import javax.inject.Inject;
import javax.inject.Named;

import bahaa.apps.ytd.ApplicationInstance;
import bahaa.apps.ytd.R;
import bahaa.apps.ytd.contracts.Download;
import bahaa.apps.ytd.root.components.DaggerActivityComponent;
import bahaa.apps.ytd.root.modules.DownloadModule;
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

    @Override
    public void showProgressBar() {

    }

    @Override
    public void addQualityButtons() {

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
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

}
