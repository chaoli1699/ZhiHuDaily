package cn.cienet.zhihudaily.view;

import cn.cienet.zhihudaily.bean.HomeNewsResponse;

public interface ZhiHuView {

	void getHomeNewsSuccess(HomeNewsResponse homeNewsResponse);
	void getNewsDetailSuccess();
}
