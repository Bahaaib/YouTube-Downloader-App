package bahaa.apps.ytd.root.components;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import bahaa.apps.ytd.ApplicationInstance;
import bahaa.apps.ytd.root.modules.AppModule;
import bahaa.apps.ytd.root.modules.ContextModule;
import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, ContextModule.class})
public interface AppComponent {

    void inject(ApplicationInstance applicationInstance);

    Context getContext();

    Application getApplication();

}
