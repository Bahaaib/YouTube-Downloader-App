package bahaa.apps.ytd.presenter;

import bahaa.apps.ytd.contracts.Download;
import bahaa.apps.ytd.model.DownloadModel;

public class DownloadPresenter implements Download.Presenter {

    private Download.View downloadView;
    private DownloadModel downloadModel;

    public DownloadPresenter(Download.View downloadView, DownloadModel downloadModel) {
        this.downloadView = downloadView;
        this.downloadModel = downloadModel;
    }

    @Override
    public void onButtonClick() {

    }
}
