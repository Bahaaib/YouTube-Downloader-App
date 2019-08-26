package bahaa.apps.ytd.root.components;

import bahaa.apps.ytd.contracts.Download;
import bahaa.apps.ytd.root.modules.AppModule;
import bahaa.apps.ytd.root.modules.DownloadModule;
import bahaa.apps.ytd.root.scopes.DownloadActivityScope;
import bahaa.apps.ytd.view.DownloadActivity;
import dagger.Component;

@DownloadActivityScope
@Component(dependencies = {AppComponent.class}, modules = {DownloadModule.class, AppModule.class})
public interface ActivityComponent {

    void inject(DownloadActivity target);

    Download.Presenter provideDownloadPresenter();
}
