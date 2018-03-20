package cn.cienet.zhihudaily.adapter;

import java.util.ArrayList;
import com.squareup.picasso.Picasso;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.cienet.zhihudaily.R;
import cn.cienet.zhihudaily.bean.Stories;

public class ZhihuNewsAdapter extends BaseAdapter{
	
	private ArrayList<Stories> newsList;
	private Context context;
	
	public ZhihuNewsAdapter(Context context, ArrayList<Stories> newsInfos) {
		// TODO Auto-generated constructor stub
		this.context=context;
		this.newsList=newsInfos;
	}
	
	@SuppressWarnings("unchecked")
	public void setNewsSources(ArrayList<Stories> newsList){
		if (newsList!=null) {
			this.newsList = (ArrayList<Stories>) newsList.clone();
		}
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return newsList==null? 0:newsList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return newsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;  
        if (convertView == null) {  
            holder = new ViewHolder();  
            convertView = LayoutInflater.from(context).inflate(  
                    R.layout.item_zhihu_news, null);  
            holder.img = (ImageView) convertView.findViewById(R.id.item_zhihu_news_img);  
            holder.tit = (TextView) convertView.findViewById(R.id.item_zhihu_news_tit);  
            holder.date = (TextView) convertView.findViewById(R.id.item_zhihu_news_date);  
  
            // Bind holder to convertView  
            convertView.setTag(holder);  
        } else {  
            holder = (ViewHolder) convertView.getTag();  
        } 
        
        //Bind data...
        String url=newsList.get(position).getImages().get(0);
        Picasso.with(context).load(url).into(holder.img);
        holder.tit.setText(newsList.get(position).getTitle());
               
		return convertView;
	}
	
	final class ViewHolder {  
        ImageView img;  
        TextView tit;  
        TextView date; 
    } 

}
