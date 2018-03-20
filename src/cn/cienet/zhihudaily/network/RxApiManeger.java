package cn.cienet.zhihudaily.network;

import java.util.Set;
import android.annotation.TargetApi;
import android.os.Build;
import android.util.ArrayMap;
import rx.Subscription;

@TargetApi(Build.VERSION_CODES.KITKAT)
public class RxApiManeger implements RxActionManager<Object>{
	
	private static RxApiManeger sInstance=null;
	private ArrayMap<Object, Subscription> maps;
	
	
	public static RxApiManeger get() {
		
		if (sInstance==null) {
			synchronized (RxApiManeger.class) {
				if (sInstance==null) {
					sInstance=new RxApiManeger();
				}
			}
		}
		return sInstance;
	}
	
	private RxApiManeger() {
		// TODO Auto-generated constructor stub
		maps=new ArrayMap<Object, Subscription>();
	}

	@Override
	public void add(Object tag, Subscription subscription) {
		// TODO Auto-generated method stub
		maps.put(tag, subscription);
	}

	@Override
	public void remove(Object tag) {
		// TODO Auto-generated method stub
		if (!maps.isEmpty()) {
			maps.remove(tag);
		}
	}
	
	public void removeAll(){
		if (!maps.isEmpty()) {
			maps.clear();
		}
	}

	@Override
	public void cancel(Object tag) {
		// TODO Auto-generated method stub
		if (maps.isEmpty()) {
			return ; 
		}
		if (maps.get(tag)==null) {
			return ;
		}
		if (maps.get(tag).isUnsubscribed()) {
			maps.get(tag).unsubscribe();
			maps.remove(tag);
		}
	}

	@Override
	public void cancelAll() {
		// TODO Auto-generated method stub
		if (maps.isEmpty()) {
			return ;
		}
		Set<Object> keys=maps.keySet();
		for(Object apiKey: keys){
			cancel(apiKey);
		}
	}

}
