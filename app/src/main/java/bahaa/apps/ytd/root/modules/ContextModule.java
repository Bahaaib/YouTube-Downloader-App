package bahaa.apps.ytd.root.modules;

import android.app.Application;
import android.content.Context;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {

    private Context context;


    public ContextModule(Context context) {
        this.context = context;
    }


    @Provides
    public Context provideContext() {
        return context;
    }

    @Provides
    @Named("Application Context")
    public Context provideAppContext() {
        return context;
    }


}
