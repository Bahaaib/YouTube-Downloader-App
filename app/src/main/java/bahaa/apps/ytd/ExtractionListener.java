package bahaa.apps.ytd;

import java.util.ArrayList;
import java.util.HashMap;

import at.huber.youtubeExtractor.YtFile;

public interface ExtractionListener {

    void onExtractionComplete(ArrayList<VideoFile> videoFiles);

    void onExtractionFailed();

}
