package cn.cienet.zhihudaily;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class MyApp extends Application {

	protected List<Activity> activities; 
	private long firstTime=0;
	
	private static MyApp mApp;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	    
		mApp=this;
		activities=new ArrayList<Activity>();
	}
	
	protected void exit(){
		
		if (System.currentTimeMillis()-firstTime>2000){
            Toast.makeText(this,getResources().getString(R.string.click_twice_to_exit),Toast.LENGTH_SHORT).show();
            firstTime=System.currentTimeMillis();
        }else{
        	for(Activity activity: activities){
    			activity.finish();
    		}
            System.exit(0);
        }
		
		
	}
	
    public boolean isNetworkConnected(){
		
		if (getApplicationContext() != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }

        return false;
	}
    
    public static MyApp getInstance(){
    	return mApp;
    }
}
