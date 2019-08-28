package bahaa.apps.ytd;

import android.app.Application;
import android.content.Context;

import bahaa.apps.ytd.receiver.ConnectivityReceiver;
import bahaa.apps.ytd.root.components.AppComponent;
import bahaa.apps.ytd.root.components.DaggerAppComponent;
import bahaa.apps.ytd.root.modules.AppModule;
import bahaa.apps.ytd.root.modules.ContextModule;

public class ApplicationInstance extends Application {
    private AppComponent component;


    public static ApplicationInstance get(Context context) {
        return (ApplicationInstance) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .contextModule(new ContextModule(this))
                .build();

    }

    public AppComponent getComponent() {
        return component;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}
