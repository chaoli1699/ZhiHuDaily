package cn.cienet.zhihudaily.network;

import cn.cienet.zhihudaily.bean.HomeNewsResponse;
import cn.cienet.zhihudaily.bean.NewsDetail;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface ApiService {

	//知乎日报
    @GET("news/latest")
    Observable<HomeNewsResponse> getHomeNews();
    
    @GET("news/before/{time}")
    Observable<HomeNewsResponse> getBeforetNews(@Path("time") String time);
    
    @GET("news/{id}")
    Observable<NewsDetail> getNewsDetail(@Path("id") int id);
}
