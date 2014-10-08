package com.dlm.neihan.bean;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

//"avatar_url": "http://p1.pstatp.com/thumb/1367/2213311454",
//"user_id": 3080520868,
//"name": "请叫我梓安哥",
//"user_verified": false
//},
public class UserEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8352282423507975604L;
	private String avatarUrl;//头像网址
	private long uerId;//用户ID；
	private String name;//昵称
	private boolean userVerified;//是否加V
	public void parseJson(JSONObject json) throws JSONException{
		if(json!=null){
			this.avatarUrl = json.getString("avatar_url");
			this.uerId = json.getLong("user_id");
			name = json.getString("name");
			userVerified = json.getBoolean("user_verified");
		}
	}
	public String getAvatarUrl() {
		return avatarUrl;
	}
	public long getUerId() {
		return uerId;
	}
	public String getName() {
		return name;
	}
	public boolean isUserVerified() {
		return userVerified;
	}
	
}
