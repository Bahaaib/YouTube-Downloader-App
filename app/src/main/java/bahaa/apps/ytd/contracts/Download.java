package bahaa.apps.ytd.contracts;

import java.util.HashMap;

import at.huber.youtubeExtractor.YtFile;
import bahaa.apps.ytd.ExtractionListener;

public interface Download {

    interface View {

        void showProgressBar();

        void showErrorDialog();

        void showNoVideoToast();

        void addQualityButtons();
    }

    interface Presenter {

        void validateInputLink(String link);

        void validateYouTubeLinkFormat(String link);
    }

    interface Model {

        void beginLinkExtraction(String link, final ExtractionListener listener);

    }
}
