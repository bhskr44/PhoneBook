package in.complit.phonebook;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.ResourceCursorAdapter;
import android.view.View;
import android.widget.Button;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

public class NewAdapter extends ResourceCursorAdapter implements Filterable{
    Context mycontext;
    Cursor mycursor;
    public NewAdapter(Context context, int layout, Cursor cursor, int flags) {
        super(context, layout, cursor, flags);
        this.mycursor=cursor;
        this.mycontext= context;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView tvid= view.findViewById(R.id.id);
        TextView tvdname= view.findViewById(R.id.tv1);
        TextView tvchss= view.findViewById(R.id.tv2);
        TextView tvspel= view.findViewById(R.id.tv3);
        TextView tvah= view.findViewById(R.id.tv4);
        TextView tvda= view.findViewById(R.id.tv5);
        TextView tvphone= view.findViewById(R.id.phone);
        TextView tvplace= view.findViewById(R.id.tv7);
        Button  calling= view.findViewById(R.id.call);

                tvid.setText(cursor.getString(cursor.getColumnIndex("_id")));
                tvdname.setText(cursor.getString(cursor.getColumnIndex("doctor_name")));
                tvchss.setText(cursor.getString(cursor.getColumnIndex("CHSS")));
                tvspel.setText(cursor.getString(cursor.getColumnIndex("specialization")));
                tvah.setText(cursor.getString(cursor.getColumnIndex("attached hospital")));
                tvda.setText(cursor.getString(cursor.getColumnIndex("doctor_address")));
                tvphone.setText(cursor.getString(cursor.getColumnIndex("doctor contact")));
                tvplace.setText(cursor.getString(cursor.getColumnIndex("place")));
        calling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mycursor.getPosition();
                if (mycursor.getString(mycursor.getColumnIndex("doctor contact")).equals("")) {
                    Toast.makeText(mycontext, "Phone number not available", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" +
                            mycursor.getString(mycursor.getColumnIndexOrThrow("doctor contact"))));
                    if (ActivityCompat.checkSelfPermission(mycontext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mycontext.startActivity(intent);
                }
            }
        });

    }
                }
