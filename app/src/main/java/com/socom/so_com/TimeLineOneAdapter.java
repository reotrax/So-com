package com.socom.so_com;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by user on 2016/05/22.
 */
public class TimeLineOneAdapter extends BaseAdapter {

	Context context;
	LayoutInflater layoutInflater = null;
	ArrayList<TwData> tweetList;
	int tweetGetLimit;

	public TimeLineOneAdapter(Context context, int tweetGetLimit) {
		this.context = context;
		this.tweetGetLimit = tweetGetLimit;
		this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public void setTweetList(ArrayList<TwData> tweetList) {
		this.tweetList = tweetList;
	}

	public void add(TwData tweet) {
		tweetList.add(tweet);
	}

	@Override
	public int getCount() {
		return tweetList.size();
	}

	@Override
	public Object getItem(int position) {
		return tweetList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = layoutInflater.inflate(R.layout.timeline, parent, false);

		((TextView)convertView.findViewById(R.id.username)).setText(tweetList.get(position).getUser());
		((TextView)convertView.findViewById(R.id.tweet_time)).setText(tweetList.get(position).getCreatedAt().toString());
		((TextView)convertView.findViewById(R.id.tweet)).setText(tweetList.get(position).getText());
		((TextView)convertView.findViewById(R.id.retweet_user)).setText(tweetList.get(position).getRetweetUser());

		return convertView;
	}
}
