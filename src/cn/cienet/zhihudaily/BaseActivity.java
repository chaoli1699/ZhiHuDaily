package cn.cienet.zhihudaily;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import cn.cienet.zhihudaily.presenter.BasePresenter;

public abstract class BaseActivity<P extends BasePresenter> extends Activity {

	protected P mPresenter;
	protected static final int INSERT_USER=0;
	protected static final int INPUT_TOTAL=1;
	protected static final int SET_USER_SIZE=2;
	protected static final int USER_INFO=3;
	protected static final int BILL_HISTORY=4;
	protected MyApp mApp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mPresenter=createPresenter();
		
		mApp=(MyApp) getApplicationContext();
		registActivity(this);
	}
	
	protected void registActivity(Activity mActivity){
		mApp.activities.add(mActivity);
	}
	
	protected void unregistActiivty(Activity mActivity){
		Activity activity;
		for(int i=0; i<mApp.activities.size(); i++){
			activity=mApp.activities.get(i);
			if (activity==mActivity) {
				mApp.activities.remove(i);
				break;
			}
		}
	}
	
	protected void initActionBar(String title, boolean logoElable, boolean homeEnable){
		ActionBar actionBar=getActionBar();
		
		actionBar.setTitle(title);
		
		actionBar.setDisplayUseLogoEnabled(logoElable);
		actionBar.setDisplayShowHomeEnabled(logoElable);

		actionBar.setDisplayHomeAsUpEnabled(homeEnable);
		actionBar.setHomeButtonEnabled(homeEnable);	
	}
	
	protected void startAct(Class activity){
		Intent intent=new Intent(this, activity);
		startAct(intent);
	}
	
	protected void startAct(Intent intent){
		startActivity(intent);
	}
	
	protected void startAct4Result(Class activity, int requestCode){
		Intent intent=new Intent(new Intent(this, activity));
		startAct4Result(intent, requestCode);
	}
	
	protected void startAct4Result(Intent intent, int requestCode){
		startActivityForResult(intent, requestCode);
	}
	
	protected void showToast(String msg){
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
	
	protected abstract P createPresenter(); 
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unregistActiivty(this);
		super.onDestroy();
	}
}
