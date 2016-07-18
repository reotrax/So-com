package com.socom.so_com;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 * Created by user on 2016/05/22.
 */
public class AsyncTimeLine extends AsyncTaskLoader<ResponseList> {

	private Twitter twitter;

	public AsyncTimeLine(Context context, Twitter twitter) {
		super(context);
		this.twitter = twitter;
	}

	/**
	 * TimeLineなどのステータス取得
	 * @return
	 */
	@Override
	public ResponseList loadInBackground() {
		Log.v("Async", "loadInBackground");

		// タイムライン取得
		ResponseList<Status> statuses = null;
		try {
			statuses = twitter.getHomeTimeline();
		} catch (TwitterException e) {
			e.printStackTrace();
		}

		Log.v("satuses size = ", Integer.toString(statuses.size()));

		return statuses;
	}

	@Override
	protected void onStartLoading() {
		super.onStartLoading();
		forceLoad();
	}
}
