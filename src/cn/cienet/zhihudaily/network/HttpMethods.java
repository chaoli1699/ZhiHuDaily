package cn.cienet.zhihudaily.network;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import cn.cienet.zhihudaily.MyApp;
import cn.cienet.zhihudaily.bean.HomeNewsResponse;
import cn.cienet.zhihudaily.bean.NewsDetail;
import cn.cienet.zhihudaily.utils.Constant;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HttpMethods {

	public static final String BASE_URL = Constant.BASE_URL1;

    private static final int DEFAULT_TIMEOUT = 15;

    private Retrofit retrofit;
    private ApiService apiService;
    //构造方法私有
    private HttpMethods() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.addInterceptor(new HttpCacheInterceptor());

        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder{
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    //获取单例
    public static HttpMethods getInstance(){
        return SingletonHolder.INSTANCE;
    }
    
    // 网络请求缓存策略插入器
	public class HttpCacheInterceptor implements Interceptor {
	    @Override
	    public Response intercept(Chain chain) throws IOException {
	        Request request = chain.request();
	        // 无网络时，始终使用本地Cache
	        if (!MyApp.getInstance().isNetworkConnected()) {
	            request = request.newBuilder()
	                    .cacheControl(CacheControl.FORCE_CACHE)
	                    .build();
	        }

	        Response response = chain.proceed(request);
	        if (MyApp.getInstance().isNetworkConnected()) {
	            // 有网络时，设置缓存过期时间0个小时
	            int maxAge = 0;
	            response.newBuilder()
	                    .header("Cache-Control", "public, max-age=" + maxAge)
	                    .removeHeader("Pragma") // 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
	                    .build();
	        } else {
	            // 无网络时，设置缓存过期超时时间为4周
	            int maxStale = 60 * 60 * 24 * 28;
	            response.newBuilder()
	                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
	                    .removeHeader("Pragma")
	                    .build();
	        }
	        return response;
	    }
	}


    public void getLatesNews(Subscriber<HomeNewsResponse> subscriber){
        Subscription subscription=apiService.getHomeNews()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        RxApiManeger.get().add("latest", subscription);
    }
    
    public void getBoforeNews(Subscriber<HomeNewsResponse> subscriber, String time){
        Subscription subscription=apiService.getBeforetNews(time)
    	         .subscribeOn(Schedulers.io())
    	         .unsubscribeOn(Schedulers.io())
    	         .observeOn(AndroidSchedulers.mainThread())
    	         .subscribe(subscriber);
        RxApiManeger.get().add(time, subscription);
    }

    public void getNewsDetail(Subscriber<NewsDetail> subscriber, int id){
        Subscription subscription=apiService.getNewsDetail(id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        RxApiManeger.get().add("detailId:"+id, subscription);
    }
}
