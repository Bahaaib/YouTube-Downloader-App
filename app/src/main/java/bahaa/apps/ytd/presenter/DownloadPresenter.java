package bahaa.apps.ytd.presenter;

import android.util.Log;

import java.util.HashMap;

import at.huber.youtubeExtractor.YtFile;
import bahaa.apps.ytd.ExtractionListener;
import bahaa.apps.ytd.contracts.Download;
import bahaa.apps.ytd.model.DownloadModel;

public class DownloadPresenter implements Download.Presenter, ExtractionListener {

    private Download.View downloadView;
    private DownloadModel downloadModel;

    public DownloadPresenter(Download.View downloadView, DownloadModel downloadModel) {
        this.downloadView = downloadView;
        this.downloadModel = downloadModel;
    }

    @Override
    public void validateInputLink(String link) {
        if (link.isEmpty()) {
            downloadView.showErrorDialog();
        } else {
            validateYouTubeLinkFormat(link);
        }
    }

    @Override
    public void validateYouTubeLinkFormat(String link) {
        if (link.contains("://youtu.be/") || link.contains("youtube.com/watch?v=")) {
            downloadModel.beginLinkExtraction(link, this);
        } else {
            downloadView.showErrorDialog();
        }
    }

    @Override
    public void onExtractionComplete(HashMap<String, YtFile> videosList) {
        Log.i("statuss", "I got " + videosList.size() + " Files");
    }

    @Override
    public void onExtractionFailed() {
        Log.i("statuss", "Videos NOT Found");
    }
}
