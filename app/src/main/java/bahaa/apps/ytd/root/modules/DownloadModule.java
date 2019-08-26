package bahaa.apps.ytd.root.modules;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Named;

import bahaa.apps.ytd.contracts.Download;
import bahaa.apps.ytd.model.DownloadModel;
import bahaa.apps.ytd.presenter.DownloadPresenter;
import dagger.Module;
import dagger.Provides;

@Module
public class DownloadModule {

    private Download.View downloadView;

    public DownloadModule(Download.View downloadView) {
        this.downloadView = downloadView;
    }

    @Provides
    public Download.View provideView() {
        return downloadView;
    }

    @Provides
    public DownloadModel provideModel(Context context) {
        return new DownloadModel(context);
    }

    @Provides
    public Download.Presenter providePresenter(Download.View view, DownloadModel model) {
        return new DownloadPresenter(view, model);
    }
}
