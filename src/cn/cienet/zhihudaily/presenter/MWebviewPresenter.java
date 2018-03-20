package cn.cienet.zhihudaily.presenter;

import android.content.Context;
import cn.cienet.zhihudaily.view.MWebView;

public class MWebviewPresenter extends BasePresenter<MWebView>{
	
	public MWebviewPresenter(Context context, MWebView view) {
		// TODO Auto-generated constructor stub
		attachView(context, view);
	}
	
	public String getNewsTitle(){
	    return dataSource.readNewsTitle();
	}
	
	public String getNewsContent () {
		return dataSource.readNewsContent();
	}

}
