package cn.cienet.zhihudaily.presenter;

import android.content.Context;
import android.util.Log;
import cn.cienet.zhihudaily.bean.HomeNewsResponse;
import cn.cienet.zhihudaily.bean.NewsDetail;
import cn.cienet.zhihudaily.network.HttpMethods;
import cn.cienet.zhihudaily.network.RxApiManeger;
import cn.cienet.zhihudaily.view.ZhiHuView;
import rx.Subscriber;

public class ZhiHuPresenter extends BasePresenter<ZhiHuView> {
	
	private static final String TAG="ZhiHuPresenter";

	public ZhiHuPresenter(Context context, ZhiHuView view) {
		// TODO Auto-generated constructor stub
		attachView(context, view);
	}
	
	public void getHomeNewsResponse(){
		
		HttpMethods.getInstance().getLatesNews(new Subscriber<HomeNewsResponse>() {
			
			@Override
			public void onNext(HomeNewsResponse homeNewsResponse) {
				// TODO Auto-generated method stub
				view.getHomeNewsSuccess(homeNewsResponse);
			}
			
			@Override
			public void onError(Throwable t) {
				// TODO Auto-generated method stub
				String str=t.getMessage();
				Log.i(TAG, str);
			}
			
			@Override
			public void onCompleted() {
				// TODO Auto-generated method stub
				Log.i(TAG, "finish");
				RxApiManeger.get().remove("latest");
			}
		});
	}
	
	public void getBeforeNews(final String time){	
		HttpMethods.getInstance().getBoforeNews(new Subscriber<HomeNewsResponse>() {

			@Override
			public void onNext(HomeNewsResponse homeNewsResponse) {
				// TODO Auto-generated method stub
				view.getHomeNewsSuccess(homeNewsResponse);
			}

			@Override
			public void onError(Throwable t) {
				// TODO Auto-generated method stub
				Log.i(TAG, t.getMessage());
			}

			@Override
			public void onCompleted() {
				// TODO Auto-generated method stub
				Log.i(TAG, "finish");
				RxApiManeger.get().remove(time);
			}
		}, time);
	}
	
	public void cancelAllHttpTask(){
		RxApiManeger.get().cancelAll();
	}
	
	public void cancelHttpTask(String tag) {
		RxApiManeger.get().cancel(tag);
	}
	
	public void getNewsDetailResponse(final int newsId){
		HttpMethods.getInstance().getNewsDetail(new Subscriber<NewsDetail>() {

			@Override
			public void onNext(NewsDetail newsDetail) {
				// TODO Auto-generated method stub
				
				String headerImage;
		        if (newsDetail.getImage() == null || newsDetail.getImage() == "") {
		            headerImage = "file:///android_asset/news_detail_header_image.jpg";

		        } else {
		            headerImage = newsDetail.getImage();
		        }
		        StringBuilder sb = new StringBuilder();
		        sb.append("<div class=\"img-wrap\">")
		                .append("<h1 class=\"headline-title\">")
		                .append(newsDetail.getTitle()).append("</h1>")
		                .append("<span class=\"img-source\">")
		                .append(newsDetail.getImage_source()).append("</span>")
		                .append("<img src=\"").append(headerImage)
		                .append("\" alt=\"\">")
		                .append("<div class=\"img-mask\"></div>");
		        String mNewsContent = "<link rel=\"stylesheet\" type=\"text/css\" href=\"news_content_style.css\"/>"
		                + "<link rel=\"stylesheet\" type=\"text/css\" href=\"news_header_style.css\"/>"
		                + newsDetail.getBody().replace("<div class=\"img-place-holder\">", sb.toString());
		        
				dataSource.writeNews2File(newsDetail.getTitle(), mNewsContent);
				view.getNewsDetailSuccess();
			}

			@Override
			public void onError(Throwable t) {
				// TODO Auto-generated method stub
				Log.i(TAG, t.getMessage());
			}
			
			@Override
			public void onCompleted() {
				// TODO Auto-generated method stub
				Log.i(TAG, "finish");
				RxApiManeger.get().remove("detailId:"+newsId);

			}
			
		}, newsId);
	}
}
