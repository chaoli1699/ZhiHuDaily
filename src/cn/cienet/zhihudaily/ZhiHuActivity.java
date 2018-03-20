package cn.cienet.zhihudaily;

import java.util.ArrayList;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import cn.cienet.zhihudaily.adapter.ZhihuNewsAdapter;
import cn.cienet.zhihudaily.bean.HomeNewsResponse;
import cn.cienet.zhihudaily.bean.Stories;
import cn.cienet.zhihudaily.presenter.ZhiHuPresenter;
import cn.cienet.zhihudaily.view.ZhiHuView;
import cn.cienet.zhihudaily.weight.LoadMoreListView;
import cn.cienet.zhihudaily.weight.LoadMoreListView.OnLoadMoreListener;

public class ZhiHuActivity extends BaseActivity<ZhiHuPresenter> implements ZhiHuView, OnLoadMoreListener, OnRefreshListener {
	
	private LoadMoreListView news;
	private ZhihuNewsAdapter zhihuNewsAdapter;
//	private BannerAdapter bannerAdapter;
	private ArrayList<Stories> newsInfoSource=new ArrayList<Stories>();
//	private List<TopStories> bannerInfoSource=new ArrayList<TopStories>();
	private HomeNewsResponse homeNewsResponse;
	
//	private BannerViewPager bannerViewPager;
//	private List<IBannerItem> bannerItems;
	
//	private SwipeRefreshLayout swipeRefreshLayout;
	
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initActionBar(getResources().getString(R.string.app_name), true, false);
		setContentView(R.layout.activity_zhihu);
		
 		initView();
 		initData();
	}
	
	private void initView() {
		// TODO Auto-generated method stub		
        news=(LoadMoreListView) findViewById(R.id.zhihu_news);
        zhihuNewsAdapter=new ZhihuNewsAdapter(this, newsInfoSource);
        news.setLoadMoreEnable(true); 
        news.setOnLoadMoreListener(this);
        news.setAdapter(zhihuNewsAdapter);   
        
        news.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
//				stopRefreshingData();
				if (position<newsInfoSource.size()) {
					mPresenter.getNewsDetailResponse(newsInfoSource.get(position).getId());
				}else {
					news.onFinishLoading();
				}
			}
		});
    
//        bannerViewPager=(BannerViewPager) findViewById(R.id.zhihu_top_news);        
//        bannerAdapter = new BannerAdapter(new PicassoImageLoader());
//        bannerItems = new ArrayList<IBannerItem>();
//        
//        bannerViewPager.setBannerItemClick(new BannerViewPager.OnBannerItemClick<IBannerItem>() {
//            @Override
//            public void onClick(IBannerItem data) {
//            	stopRefreshingData();
//            	mPresenter.getNewsDetailResponse(data.getTopStories().getId());
//            }
//        });
        
//        swipeRefreshLayout=(SwipeRefreshLayout) findViewById(R.id.zhihu_swipe_refresh);
//        swipeRefreshLayout.setOnRefreshListener(this);
//        // set style  
//        swipeRefreshLayout.setColorScheme(android.R.color.holo_red_light, android.R.color.holo_green_light,  
//                android.R.color.holo_blue_bright, android.R.color.holo_orange_light);
	}
	
	private void initData(){
//		if (bannerInfoSource!=null) {
//			bannerInfoSource.clear();
//		}
		if (newsInfoSource!=null) {
			newsInfoSource.clear();
		}
//		if (bannerItems!=null) {
//			bannerItems.clear();
//		}
		mPresenter.getHomeNewsResponse();
	}

//	private void stopRefreshingData(){
//		if (swipeRefreshLayout.isRefreshing()) {
//			swipeRefreshLayout.setRefreshing(false);
//			mPresenter.cancelHttpTask("latest");
//		}
//	}
	
	@Override
	protected ZhiHuPresenter createPresenter() {
		// TODO Auto-generated method stub
		return new ZhiHuPresenter(ZhiHuActivity.this, this);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.zhihu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
//		stopRefreshingData();
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void getHomeNewsSuccess(HomeNewsResponse homeNewsResponse) {
		// TODO Auto-generated method stub
		this.homeNewsResponse=homeNewsResponse;
//		if (homeNewsResponse.getTop_stories()!=null) {
//			bannerInfoSource=homeNewsResponse.getTop_stories();
//			for (int i = 0; i < bannerInfoSource.size(); i++) {	
//        		bannerItems.add(new BannerItem(bannerInfoSource.get(i)));
//        		bannerAdapter.setData(this, bannerItems);
//                bannerViewPager.setBannerAdapter(bannerAdapter);
//            }
//		}
		newsInfoSource.addAll(homeNewsResponse.getStories());

	    zhihuNewsAdapter.setNewsSources(newsInfoSource);
	    news.onFinishLoading();
//	    stopRefreshingData();
	};
	
	@Override
	public void onLoadingMore() {
		// TODO Auto-generated method stub
		if (homeNewsResponse!=null) {
			mPresenter.getBeforeNews(homeNewsResponse.getDate());
		}
	}
	
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		initData();
	}

	@Override
	public void getNewsDetailSuccess() {
		// TODO Auto-generated method stub
		Intent intent=new Intent(ZhiHuActivity.this, WebviewActivity.class);
		intent.putExtra("page_source", "news");
		startAct(intent);
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
//		super.onBackPressed();
//		stopRefreshingData();
		mPresenter.cancelAllHttpTask();
	    mApp.exit();
	}
	
//	class PicassoImageLoader implements ImageLoader {
//        @Override
//        public void onDisplayImage(Context context, ImageView imageView, String url) {
//            Picasso.with(context)
//                   .load(url)
//                   .transform(new RoundTransform(ZhiHuActivity.this, 16))  
//                   .into(imageView);
//        }
//    }

}
