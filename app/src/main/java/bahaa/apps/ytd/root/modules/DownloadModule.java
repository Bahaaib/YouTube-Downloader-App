package bahaa.apps.ytd.root.modules;

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
    public DownloadModel provideModel() {
        return new DownloadModel();
    }

    @Provides
    public Download.Presenter providePresenter(Download.View view, DownloadModel model) {
        return new DownloadPresenter(view, model);
    }
}
