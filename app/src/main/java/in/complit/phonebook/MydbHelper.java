package in.complit.phonebook;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MydbHelper extends SQLiteOpenHelper {

    String ctabl1 = "CREATE TABLE IF NOT EXISTS `specialist_details` (\n" +
            "\t`_id`\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
            "\t`doctor_name`\tTEXT NOT NULL,\n" +
            "\t`CHSS`\tTEXT NOT NULL,\n" +
            "\t`specialization`\tTEXT NOT NULL,\n" +
            "\t`attached hospital`\tTEXT,\n" +
            "\t`doctor_address`\tTEXT NOT NULL,\n" +
            "\t`doctor contact`\tNUMERIC,\n" +
            "\t`place`\tTEXT NOT NULL\n" +
            ");";

    String ctab2 = "CREATE TABLE IF NOT EXISTS `hospital_details` (\n" +
            "\t`_id`\tINTEGER NOT NULL,\n" +
            "\t`medical facility type`\tTEXT NOT NULL,\n" +
            "\t`CHSS `\tTEXT NOT NULL,\n" +
            "\t`medical_facility_name`\tTEXT NOT NULL,\n" +
            "\t`medical_facility_address`\tTEXT NOT NULL,\n" +
            "\t`medical_facility_place`\tTEXT NOT NULL,\n" +
            "\t`medical facility contactno`\tNUMERIC NOT NULL,\n" +
            "\t`medical facility other contactno`\tNUMERIC,\n" +
            "\tPRIMARY KEY(`_id`)\n" +
            ");";

    String ctab3 = "CREATE TABLE IF NOT EXISTS `contact_person_details` (\n" +
            "\t`_id`\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
            "\t`contact_person_name`\tTEXT NOT NULL,\n" +
            "\t`designation`\tTEXT,\n" +
            "\t`contact number`\tNUMERIC NOT NULL,\n" +
            "\t`service type`\tTEXT NOT NULL,\n" +
            "\t`other contact number`\tNUMERIC\n" +
            ");";


    Static a = new Static();
    String i2t1 = a.tab1();
    String i2t2 = a.tab2();
    String i2t3 = a.tab3();

    public MydbHelper(Context context) {
        super(context, "MyDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(ctabl1);
        sqLiteDatabase.execSQL(ctab2);
        sqLiteDatabase.execSQL(ctab3);
        sqLiteDatabase.execSQL(i2t1);
        sqLiteDatabase.execSQL(i2t2);
        sqLiteDatabase.execSQL(i2t3);
    }

    SQLiteDatabase database;

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public Cursor getdata(String table_name) {
        database = getReadableDatabase();

        Cursor data = database.rawQuery("SELECT * FROM " + table_name, null);
        return data;
    }

    public Cursor getsuggestedvalues(CharSequence partialvalue, String table_name, String field) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        if (partialvalue == null || partialvalue.length() == 0) {
            return getdata(table_name);
        } else {
            String value = "'%" + partialvalue.toString() + "%'";

            return sqLiteDatabase.rawQuery("SELECT * FROM " + table_name + " WHERE " + field + " LIKE " + value, null);
           // return sqLiteDatabase.rawQuery("SELECT doctor_name,place FROM specialist_details UNION SELECT medical_facility_name,medical_facility_place FROM hospital_details", null);
        }
        }



    public Cursor globalsearch(CharSequence partialvalue) {
        Cursor contents;
        if (partialvalue == null || partialvalue.length() == 0) {
            return getdata("specialist_details");}
           //else
          //return database.rawQuery("SELECT doctor_name,place FROM specialist_details UNION SELECT medical_facility_name,medical_facility_place FROM hospital_details ", null);

         else {
            contents = getsuggestedvalues(partialvalue, "specialist_details", "doctor_name");
            int index = contents.getColumnIndex("doctor_name");
            String str = contents.getColumnName(index);
          if (str == null || str.isEmpty() || str.equalsIgnoreCase("null")) {
                contents = getsuggestedvalues(partialvalue, "specialist_details", "doctor_address");
                index = contents.getColumnIndex("doctor_address");
                str = contents.getColumnName(index);
                if (str == null || str.isEmpty() || str.equalsIgnoreCase("null")) {
                    contents = getsuggestedvalues(partialvalue, "specialist_details", "place");
                    index = contents.getColumnIndex("place");
                    str = contents.getColumnName(index);
                    if (str == null || str.isEmpty() || str.equalsIgnoreCase("null")) {
                        contents = getsuggestedvalues(partialvalue, "hospital_details", "medical_facility_name");
                        index = contents.getColumnIndex("medical_facility_name");
                        str = contents.getColumnName(index);
                        if (str == null || str.isEmpty() || str.equalsIgnoreCase("null")) {
                            contents = getsuggestedvalues(partialvalue, "hospital_details", "medical_facility_address");
                            index = contents.getColumnIndex("medical_facility_address");
                            str = contents.getColumnName(index);
                            if (str == null || str.isEmpty() || str.equalsIgnoreCase("null")) {
                                contents = getsuggestedvalues(partialvalue, "hospital_details", "medical_facility_place");
                                index = contents.getColumnIndex("medical_facility_place");
                                str = contents.getColumnName(index);
                                if (str == null || str.isEmpty() || str.equalsIgnoreCase("null")) {
                                    contents = getsuggestedvalues(partialvalue, "contact_person_details", "contact_person_name");
                                }
                            }
                        }
                    }
                }
            }
        }
        //else return contents = null;
     //   contents = getsuggestedvalues(partialvalue, "specialist_details", "doctor_name");

        return  contents;
    }

        public Cursor allData () {
            return getdata("specialist_details");
    }
    public Cursor getUnsyncedNames(String table_name,String field) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + table_name + " WHERE " + field + " = 0;";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

    public int deleteContents(String table_name){
        SQLiteDatabase db = this.getReadableDatabase();
        try{
            String sql ="DELETE FROM "+table_name;
            db.rawQuery( sql,null );
            return 0;
            }catch (Exception e){
            return 1;
        }
    }
}