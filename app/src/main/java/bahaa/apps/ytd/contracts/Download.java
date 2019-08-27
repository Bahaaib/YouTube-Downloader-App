package bahaa.apps.ytd.contracts;

import java.util.ArrayList;
import java.util.HashMap;

import at.huber.youtubeExtractor.YtFile;
import bahaa.apps.ytd.ExtractionListener;
import bahaa.apps.ytd.VideoFile;

public interface Download {

    interface View {

        void showProgressBar();

        void showErrorDialog();

        void showNoVideoToast();

        void addQualityButtons(ArrayList<VideoFile> titles);
    }

    interface Presenter {

        void validateInputLink(String link);

        void validateYouTubeLinkFormat(String link);

        void buildButtonsText(ArrayList<VideoFile> videoFiles);

        void buildFileNames(ArrayList<VideoFile> videoFiles);
    }

    interface Model {

        void beginLinkExtraction(String link, final ExtractionListener listener);

    }
}
