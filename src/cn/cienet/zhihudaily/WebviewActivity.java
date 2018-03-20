package cn.cienet.zhihudaily;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import cn.cienet.zhihudaily.presenter.MWebviewPresenter;
import cn.cienet.zhihudaily.view.MWebView;

public class WebviewActivity extends BaseActivity<MWebviewPresenter> implements MWebView{
	
	private WebView webPage;
	private ProgressBar processBar;
	private String pageSource;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		pageSource=getIntent().getExtras().getString("page_source");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);
		
		initView();
		if (pageSource.equals("news")) {
			initActionBar(mPresenter.getNewsTitle(), false, true);
			webPage.loadDataWithBaseURL("file:///android_asset/", mPresenter.getNewsContent(),"text/html", "UTF-8", null);
		}
	}
	
	@SuppressLint("SetJavaScriptEnabled")
	private void initView(){
		webPage=(WebView) findViewById(R.id.webview_webpage);
		processBar=(ProgressBar) findViewById(R.id.webview_progressBar);
		
		WebSettings webSettings=webPage.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webPage.setVerticalScrollBarEnabled(false);
        webPage.setHorizontalScrollBarEnabled(false);
		
		webPage.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
//				view.loadUrl(url);
				return true;
//				return super.shouldOverrideUrlLoading(view, url);
			}
		});
		
		webPage.setWebChromeClient(new WebChromeClient(){
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// TODO Auto-generated method stub
				super.onProgressChanged(view, newProgress);
				if (newProgress != 100) {
	                processBar.setProgress(newProgress);
	            } else {
	                processBar.setVisibility(View.GONE);
	            }
			}
			
			@Override
			public void onReceivedTitle(WebView view, String title) {
				// TODO Auto-generated method stub
				super.onReceivedTitle(view, title);
				if (pageSource.equals("news")) {
					initActionBar(mPresenter.getNewsTitle(), false, true);
				}else {
					initActionBar(title, false, true);
				}
			}
		});
	}

	@Override
	protected MWebviewPresenter createPresenter() {
		// TODO Auto-generated method stub
		return new MWebviewPresenter(WebviewActivity.this, this);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.mwebview, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
