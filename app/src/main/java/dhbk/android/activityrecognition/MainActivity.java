package dhbk.android.activityrecognition;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.ActivityRecognition;

/**
 * todo 3 - implement the ConnectionCallbacks and OnConnectionFailedListener interfaces.
 */
public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: 8/20/16 3b you can initialize the client and connect to Google Play Services in onCreate() by requesting the ActivityRecognition.API and associating your listeners with the GoogleApiClient instance.
        mApiClient = new GoogleApiClient.Builder(this)
                .addApi(ActivityRecognition.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mApiClient.connect();
    }

    // TODO: 8/20/16 3c -  Once the GoogleApiClient instance has connected, onConnected() is called.
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        // create a PendingIntent that goes to the IntentService you created earlier, and pass it to the ActivityRecognitionApi.
        Intent intent = new Intent(this, ActivityRecognizedService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // your application should attempt to recognize the user's activity every three seconds and send that data to ActivityRecognizedService.
        ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(mApiClient, 3000, pendingIntent);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
