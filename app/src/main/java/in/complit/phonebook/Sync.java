package in.complit.phonebook;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class Sync extends AppCompatActivity {
    ListView list;
    MydbHelper db;
    SimpleCursorAdapter adapter;
    EditText search;
    MydbHelper item = new MydbHelper(this);
    Cursor data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync);
    }

}
