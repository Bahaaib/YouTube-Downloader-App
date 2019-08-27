package bahaa.apps.ytd.model;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.SparseArray;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;
import javax.inject.Named;

import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YtFile;
import bahaa.apps.ytd.ExtractionListener;
import bahaa.apps.ytd.VideoFile;
import bahaa.apps.ytd.contracts.Download;

public class DownloadModel implements Download.Model {

    private WeakReference<Context> weakReference;
    private ArrayList<VideoFile> videoFiles = new ArrayList<>();

    @Inject
    public DownloadModel(@Named("Application Context") Context context) {
        weakReference = new WeakReference<>(context);
    }


    @SuppressLint("StaticFieldLeak")
    @Override
    public void beginLinkExtraction(String link, final ExtractionListener listener) {
        new YouTubeExtractor(weakReference.get()) {
            @Override
            protected void onExtractionComplete(SparseArray<YtFile> ytFiles, VideoMeta videoMeta) {
                videoFiles.clear();
                if (ytFiles == null) {
                    listener.onExtractionFailed();
                } else {
                    for (int i = 0, itag; i < ytFiles.size(); i++) {
                        itag = ytFiles.keyAt(i);
                        YtFile ytFile = ytFiles.get(itag);

                        if (ytFile.getFormat().getHeight() == -1 || ytFile.getFormat().getHeight() >= 360) {
                            VideoFile file = new VideoFile();
                            file.setFile(ytFile);
                            file.setMetaTitle(videoMeta.getTitle());
                            videoFiles.add(file);
                        }
                    }
                    listener.onExtractionComplete(videoFiles);
                }

            }
        }.extract(link, true, false);
    }

    @Override
    public void downloadFromUrl(String url, String metadata, String filename) {
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setTitle(metadata);
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);

        DownloadManager manager = (DownloadManager) weakReference.get().getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }
}
