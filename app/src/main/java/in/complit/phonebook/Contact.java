package in.complit.phonebook;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.List;

public class Contact extends AppCompatActivity implements FetchDataListener {
    ListView list;
    MydbHelper db;
    SimpleCursorAdapter adapter;
    EditText search;
    private ProgressDialog dialog;
    Button b1;
    MydbHelper item = new MydbHelper(this);
    Cursor data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        db= new MydbHelper(getApplicationContext());
        populate();
        list.setFastScrollEnabled(true);
        list.setTextFilterEnabled(true);
        search = findViewById(R.id.search);
        Button b1 = findViewById( R.id.update );
        list.setTextFilterEnabled(true);
        final CharSequence acharSequence;
        b1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            db.deleteContents( "contact_person_details" );
                ConnectivityManager connec =
                        (ConnectivityManager) getSystemService( getApplicationContext().CONNECTIVITY_SERVICE );

                if (connec.getNetworkInfo( 0 ).getState() == android.net.NetworkInfo.State.CONNECTED ||
                        connec.getNetworkInfo( 0 ).getState() == android.net.NetworkInfo.State.CONNECTING ||
                        connec.getNetworkInfo( 1 ).getState() == android.net.NetworkInfo.State.CONNECTING ||
                        connec.getNetworkInfo( 1 ).getState() == android.net.NetworkInfo.State.CONNECTED) {

                    initView();
                    Toast.makeText( getApplicationContext(), " Connected ", Toast.LENGTH_LONG ).show();

                } else if (
                        connec.getNetworkInfo( 0 ).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                                connec.getNetworkInfo( 1 ).getState() == android.net.NetworkInfo.State.DISCONNECTED) {
                    getdatafromDataBase();
                    Toast.makeText( getApplicationContext(), " Please get connect to the internet", Toast.LENGTH_LONG ).show();
                }
            }

        } );



        adapter.setFilterQueryProvider(new FilterQueryProvider() {
            @Override
            public Cursor runQuery(CharSequence charSequence) {
                String value = "'%" + charSequence.toString() + "%'";
                Toast.makeText(getApplicationContext(),"Searching results for : "+value,Toast.LENGTH_SHORT).show();


                //Toast.makeText(getApplicationContext(),"SELECT * FROM specialist_details WHERE doctor_name LIKE '%"+charSequence.toString() +"'" ,Toast.LENGTH_SHORT);


                return  item.getsuggestedvalues(charSequence,"contact_person_details","contact_person_name");
            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                ListView list = findViewById(R.id.lv1);
                SimpleCursorAdapter filterAdapter = (SimpleCursorAdapter) list.getAdapter();
                filterAdapter.getFilter().filter(editable.toString());
                data.close();
            }
        });

    }
    public void populate(){
        list = findViewById(R.id.lv1);
        String[] arrayCols = new String[]{"_id","contact_person_name","designation","contact number","service type","other contact number"};
        int[] arrayIDs = new int[]{R.id.id,R.id.tv1,R.id.tv2,R.id.phone,R.id.tv3,R.id.tv4};
        data = db.getdata("contact_person_details");
        adapter = new SimpleCursorAdapter(getBaseContext(),R.layout.mylist,data, arrayCols,arrayIDs,0);
        list.setAdapter(adapter);


    }


    private void getdatafromDataBase() {

    }

    private void initView() {
        // show progress dialog
        dialog = ProgressDialog.show(this, "", "Loading...");

        String url = "http://14.139.219.221:8080/monitoring/project-data-all.php";
        FetchDataTask task = new FetchDataTask(this);
        task.execute(url);
    }

    @Override
    public void onFetchComplete(List<Application> data) {
        if(dialog != null)  dialog.dismiss();
        ApplicationAdapter adapter = new ApplicationAdapter(this, data);
        list.setAdapter(adapter);
    }

    @Override
    public void onFetchFailure(String msg) {
        if(dialog != null)  dialog.dismiss();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}

