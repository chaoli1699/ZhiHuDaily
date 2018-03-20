package cn.cienet.zhihudaily.presenter;

import android.content.Context;
import cn.cienet.zhihudaily.model.DataSource;

public class BasePresenter<V> {

	public V view;
	protected Context context;
	protected DataSource dataSource;
	
	public void attachView(Context context, V view){
		this.context=context;
		this.view=view;
		dataSource=new DataSource(context);
	};
	
	public void detachView(V view){
		this.view=view;
	}
}
