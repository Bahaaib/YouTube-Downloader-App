package bahaa.apps.ytd.contracts;

public interface Download {

    interface View {

        void initViews();

        void showProgressBar();

        void addQualityButtons();
    }

    interface Presenter {

        void onButtonClick();
    }

    interface Model {
        interface onCompleteListener {
            void onLinkExtracted(String ytDownloadLink);
        }
    }
}
