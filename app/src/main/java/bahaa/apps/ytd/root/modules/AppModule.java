package bahaa.apps.ytd.root.modules;

import android.app.Application;

import javax.inject.Singleton;

import bahaa.apps.ytd.ApplicationInstance;
import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private ApplicationInstance applicationInstance;

    public AppModule(ApplicationInstance applicationInstance) {
        this.applicationInstance = applicationInstance;
    }

    @Provides
    @Singleton
    public Application provideApplication(){
        return applicationInstance;
    }

}
