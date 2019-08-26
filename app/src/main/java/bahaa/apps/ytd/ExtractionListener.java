package bahaa.apps.ytd;

import java.util.HashMap;

import at.huber.youtubeExtractor.YtFile;

public interface ExtractionListener {

    void onExtractionComplete(HashMap<String, YtFile> videosList);

    void onExtractionFailed();

}
