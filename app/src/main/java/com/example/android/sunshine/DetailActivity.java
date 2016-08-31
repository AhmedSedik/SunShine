package com.example.android.sunshine;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DetailFragment())
                    .commit();
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this,SettingsActivity.class));
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    //A placeholder fragment containing a simple view.
    public static class DetailFragment extends Fragment{
        private static final String LOG_TAG = DetailActivity.class.getSimpleName();
        private static final String HASHTAG = " #Shunshine";
        private String mForecastStr;
        private ShareActionProvider mShareActionProvider;

        public DetailFragment(){

            setHasOptionsMenu(true);
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_detail,container,false);

            Intent intent = getActivity().getIntent();
            if(intent!=null && intent.hasExtra(Intent.EXTRA_TEXT)) {
                mForecastStr = intent.getStringExtra(Intent.EXTRA_TEXT);
                TextView textView = (TextView) rootView.findViewById(R.id.detail_text);
                textView.setText(mForecastStr);
            }
            return rootView;
        }
        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            // Inflate the menu; this adds items to the action bar if it is present.
           inflater.inflate(R.menu.detailfragment, menu);
            MenuItem item = menu.findItem(R.id.menu_item_share);
            mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

            if (mShareActionProvider!=null){
                mShareActionProvider.setShareIntent(createShareForecastIntent());
                Log.d(LOG_TAG,"Share Action Provider Is null!");
            }else{
                Log.d(LOG_TAG,"Share Action Provider Is null!");
            }
        }
        private  Intent createShareForecastIntent(){
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, mForecastStr + HASHTAG);
            return shareIntent;

        }
    }

}
