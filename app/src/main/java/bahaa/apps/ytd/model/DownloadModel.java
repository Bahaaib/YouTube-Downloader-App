package bahaa.apps.ytd.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.SparseArray;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import javax.inject.Inject;
import javax.inject.Named;

import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YtFile;
import bahaa.apps.ytd.ExtractionListener;
import bahaa.apps.ytd.contracts.Download;
import bahaa.apps.ytd.root.scopes.DownloadActivityScope;

public class DownloadModel implements Download.Model {

    private WeakReference<Context> weakReference;
    private HashMap<String, YtFile> ytFileHashMap = new HashMap<>();


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
                if (ytFiles == null) {
                    listener.onExtractionFailed();
                } else {
                    for (int i = 0, itag; i < ytFiles.size(); i++) {
                        itag = ytFiles.keyAt(i);
                        YtFile ytFile = ytFiles.get(itag);

                        if (ytFile.getFormat().getHeight() == -1 || ytFile.getFormat().getHeight() >= 360) {
                            ytFileHashMap.put(videoMeta.getTitle(), ytFile);
                        }
                    }
                    listener.onExtractionComplete(ytFileHashMap);
                }

            }
        }.extract(link, true, false);
    }
}
