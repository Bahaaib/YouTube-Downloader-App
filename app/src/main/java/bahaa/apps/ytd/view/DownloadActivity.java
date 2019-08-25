package bahaa.apps.ytd.view;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import javax.inject.Inject;

import bahaa.apps.ytd.ApplicationInstance;
import bahaa.apps.ytd.R;
import bahaa.apps.ytd.contracts.Download;
import bahaa.apps.ytd.root.components.DaggerActivityComponent;
import bahaa.apps.ytd.root.modules.DownloadModule;

public class DownloadActivity extends AppCompatActivity implements Download.View {

    @Inject
    Download.Presenter presenter;

    @Inject
    Context context;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        DaggerActivityComponent.builder()
                .appComponent(ApplicationInstance.get(this).getComponent())
                .downloadModule(new DownloadModule(this))
                .build()
                .inject(this);

    }

    @Override
    public void initViews() {

    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void addQualityButtons() {

    }
}
