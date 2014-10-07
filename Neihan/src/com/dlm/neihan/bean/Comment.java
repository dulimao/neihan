package com.dlm.neihan.bean;

import org.json.JSONException;
import org.json.JSONObject;

public class Comment {
	private long id;
	private String platForm;
	private String text;
	private int diggCount;
	private int userDigg;
	private boolean userVerified;
	private int buryCount;
	private String userProfileUrl;
	private long uid;
	private String userName;
	private int userBury;
	private String userProfileImageUrl;
	private String description;
	private long createTime;
	private long userId;

	public void parseJson(JSONObject json) throws JSONException {
		if (json != null) {
			uid = json.getLong("uid");
			platForm = json.getString("platform");
			text = json.getString("text");
			diggCount = json.getInt("digg_count");
			userDigg = json.getInt("user_digg");
			userVerified = json.getBoolean("user_verified");
			buryCount = json.getInt("bury_count");
			userProfileUrl = json.getString("user_profile_url");
			id = json.getLong("id");
			userName = json.getString("user_name");
			userBury = json.getInt("user_bury");
			userProfileImageUrl = json.getString("user_profile_image_url");
			description = json.optString("description");
			createTime = json.getLong("create_time");
			userId = json.getLong("user_id");

		}
	}

	public long getId() {
		return id;
	}

	public String getPlatForm() {
		return platForm;
	}

	public String getText() {
		return text;
	}

	public int getDiggCount() {
		return diggCount;
	}

	public int getUserDigg() {
		return userDigg;
	}

	public boolean isUserVerified() {
		return userVerified;
	}

	public int getBuryCount() {
		return buryCount;
	}

	public String getUserProfileUrl() {
		return userProfileUrl;
	}

	public long getUid() {
		return uid;
	}

	public String getUserName() {
		return userName;
	}

	public int getUserBury() {
		return userBury;
	}

	public String getUserProfileImageUrl() {
		return userProfileImageUrl;
	}

	public String getDescription() {
		return description;
	}

	public long getCreateTime() {
		return createTime;
	}

	public long getUserId() {
		return userId;
	}

}
/*
 * { "uid": 0, "platform": "feifei", "text": "顶，这才是真爷们。", "digg_count": 0,
 * "user_digg": 0, "user_verified": false, "bury_count": 0, "user_profile_url":
 * "", "id": 3023949163, "user_name": "李群4718553", "user_bury": 0,
 * "user_profile_image_url":
 * "http:\/\/mat1.gtimg.com\/www\/mb\/images\/head_50.jpg", "description":
 * "这个用户很懒，神马都木有写", "create_time": 1389752393, "user_id": 3013939443 },
 */