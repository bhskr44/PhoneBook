package in.complit.phonebook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ApplicationAdapter extends ArrayAdapter<Application>{
    private List<Application> items;
    MydbHelper helper = new MydbHelper( getContext() );
    
    public ApplicationAdapter(Context context, List<Application> items) {
        super(context, R.layout.mylist, items);
        this.items = items;
    }
    
    @Override
    public int getCount() {
        return items.size();
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        
        if(v == null) {
            LayoutInflater li = LayoutInflater.from(getContext());
            v = li.inflate(R.layout.mylist, null);
        }
        
        Application app = items.get(position);
        
        if(app != null) {

            TextView tvid= v.findViewById(R.id.id);
            TextView tvdname= v.findViewById(R.id.tv1);
            TextView tvchss= v.findViewById(R.id.tv2);
            TextView tvspel= v.findViewById(R.id.tv3);
            TextView tvah= v.findViewById(R.id.tv4);
            TextView tvda= v.findViewById(R.id.tv5);
            TextView tvphone= v.findViewById(R.id.phone);
            TextView tvplace= v.findViewById(R.id.tv7);
            //Button calling= v.findViewById(R.id.call);

            if (tvid!=null){
                tvid.setText( app.getprojectid() );
            }
            if (tvdname!=null){
                tvdname.setText( app.getuser() );
            }
            if (tvchss!=null){
                tvchss.setText( app.getpicdate() );
            }
            if (tvspel!=null){
                tvspel.setText( app.getRemark1() );
            }
            if (tvah!=null){
                tvah.setText( app.getRemark2() );
            }
            if (tvda!=null){
                tvda.setText( String.valueOf( app.getlat() ));
            }
            if (tvphone!=null){
                tvphone.setText( String.valueOf( app.getlat() ));
            }
            if (tvplace!=null){
                tvplace.setText( app.getremark3());
            }



            /* icon = (ImageView)v.findViewById(R.id.appIcon);
            TextView titleText = (TextView)v.findViewById(R.id.titleTxt);
            LinearLayout ratingCntr = (LinearLayout)v.findViewById(R.id.ratingCntr);
            TextView dlText = (TextView)v.findViewById(R.id.dlTxt);
            
            if(icon != null) {
                Resources res = getContext().getResources();
                String sIcon = "in.complit.gmail:drawable/" + app.getIcon();
                icon.setImageDrawable(res.getDrawable(res.getIdentifier(sIcon, null, null)));
            }
            
            if(titleText != null) titleText.setText(app.getTitle());
            
            if(dlText != null) {
                NumberFormat nf = NumberFormat.getNumberInstance();
                dlText.setText(nf.format(app.getTotalDl())+" dl");            
            }
            
            if(ratingCntr != null && ratingCntr.getChildCount() == 0) {        
                /*
                 * max rating: 5

                for(int i=1; i<=5; i++) {
                    ImageView iv = new ImageView(getContext());
                    
                    if(i <= app.getRating()) {
                        iv.setImageDrawable(getContext().getResources().getDrawable(R.drawable.start_checked));
                    }
                    else {                
                        iv.setImageDrawable(getContext().getResources().getDrawable(R.drawable.start_unchecked));
                    }
                    
                    ratingCntr.addView(iv);
                }
            }
        */}

        return v;
    }
}
