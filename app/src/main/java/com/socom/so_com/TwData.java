package com.socom.so_com;

import java.util.Date;

/**
 * Created by user on 2016/05/22.
 */

public class TwData {
	private String text;
	private Date createdAt;
	private int favoriteCount;
	private int retweetCount;
	private String user;
	private String retweetUser;

	public TwData(String text, Date createdAt, int favCount, int retweetCount, String user, String retweeter) {
		this.text = text;
		this.createdAt = createdAt;
		this.favoriteCount = favCount;
		this.retweetCount = retweetCount;
		this.user = user;
		this.retweetUser = retweeter;
	}

	public String getText() {
		return text;
	}

	public TwData setText(String text) {
		this.text = text;
		return this;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public TwData setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
		return this;
	}

	public int getFavoriteCount() {
		return favoriteCount;
	}

	public TwData setFavoriteCount(int favoriteCount) {
		this.favoriteCount = favoriteCount;
		return this;
	}

	public int getRetweetCount() {
		return retweetCount;
	}

	public TwData setRetweetCount(int retweetCount) {
		this.retweetCount = retweetCount;
		return this;
	}

	public String getUser() {
		return user;
	}

	public TwData setUser(String user) {
		this.user = user;
		return this;
	}

	public String getRetweetUser() {
		return retweetUser;
	}

	public TwData setRetweetUser(String retweetUser) {
		this.retweetUser = retweetUser;
		return this;
	}
}
