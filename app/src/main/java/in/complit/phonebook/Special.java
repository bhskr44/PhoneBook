package in.complit.phonebook;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.Toast;

public class Special extends AppCompatActivity {

    ListView list;
    MydbHelper db;
    NewAdapter adapter;
    EditText search;
    MydbHelper item = new MydbHelper(this);
    Cursor data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special);
        populate();
        list.setFastScrollEnabled(true);
        list.setTextFilterEnabled(true);
        search = findViewById(R.id.search);
        list.setTextFilterEnabled(true);
        final CharSequence acharSequence;
        adapter.setFilterQueryProvider(new FilterQueryProvider() {
            @Override
            public Cursor runQuery(CharSequence charSequence) {
                String value = "'%" + charSequence.toString() + "%'";
                Toast.makeText(getApplicationContext(),"Searching results for : "+value,Toast.LENGTH_SHORT).show();
                return  item.getsuggestedvalues(charSequence,"specialist_details","doctor_name");
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
                NewAdapter filterAdapter = (NewAdapter) list.getAdapter();
                filterAdapter.getFilter().filter(editable.toString());
            }
        });

    }
    public void populate(){
        db= new MydbHelper(getApplicationContext());
        list = findViewById(R.id.lv1);
        String[] arrayCols = new String[]{"_id","doctor_name","CHSS","specialization","attached hospital","doctor address","doctor contact","place"};
        int[] arrayIDs = new int[]{R.id.id,R.id.tv1,R.id.tv2,R.id.tv3,R.id.tv4,R.id.tv5,R.id.phone,R.id.tv7};
        data = db.getdata("specialist_details");

        adapter = new NewAdapter(getApplicationContext(),R.layout.mylist,data,0);
        list.setAdapter(adapter);


    }
}