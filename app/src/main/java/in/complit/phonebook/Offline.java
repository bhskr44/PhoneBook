package in.complit.phonebook;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

public class Offline extends ListActivity implements FetchDataListener{
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline);
        isInternetOn();
    }

    public void isInternetOn() {

        ConnectivityManager connec =
                (ConnectivityManager) getSystemService( getApplicationContext().CONNECTIVITY_SERVICE );

        if (connec.getNetworkInfo( 0 ).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo( 0 ).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo( 1 ).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo( 1 ).getState() == android.net.NetworkInfo.State.CONNECTED) {

            initView();
            Toast.makeText( this, " Connected ", Toast.LENGTH_LONG ).show();

        } else if (
                connec.getNetworkInfo( 0 ).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo( 1 ).getState() == android.net.NetworkInfo.State.DISCONNECTED) {
            getdatafromDataBase();
            Toast.makeText( this, " Please get connect to the internet", Toast.LENGTH_LONG ).show();
        }

    }

    private void getdatafromDataBase() {

    }

    private void initView() {
        dialog = ProgressDialog.show(this, "", "Loading...");

        String url = "http://14.139.219.221:8080/monitoring/project-data-all.php";
        FetchDataTask task = new FetchDataTask(this);
        task.execute(url);
    }

    @Override
    public void onFetchComplete(List<Application> data) {
        if(dialog != null)  dialog.dismiss();
        ApplicationAdapter adapter = new ApplicationAdapter(this, data);
        setListAdapter(adapter);
    }

    @Override
    public void onFetchFailure(String msg) {
        if(dialog != null)  dialog.dismiss();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
