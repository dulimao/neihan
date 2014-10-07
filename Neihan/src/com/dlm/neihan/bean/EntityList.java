package com.dlm.neihan.bean;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class EntityList {
	private long maxTime;
	private boolean hasMore;
	private long minTime;
	private String tip;

	private List<TextEntity> entities;

	public long getMaxTime() {
		return maxTime;
	}

	public boolean isHasMore() {
		return hasMore;
	}

	public long getMinTime() {
		return minTime;
	}

	public String getTip() {
		return tip;
	}

	public List<TextEntity> getEntities() {
		return entities;
	}

	public void parseJson(JSONObject json) throws JSONException {
		if (json != null) {
			hasMore = json.getBoolean("has_more");// 是否还有更多
			if(hasMore==true){
				minTime = json.optLong("min_time");
			}
			
			tip = json.getString("tip");
			maxTime = json.optLong("max_time");

			// 从data对象中获取名称为data的数组
			JSONArray array = json.getJSONArray("data");

			int len = array.length();

			if (len > 0) {

				entities = new LinkedList<TextEntity>();
				// 便利数组中的每一条图片段子信息
				for (int i = 0; i < len; i++) {
					JSONObject item = array.getJSONObject(i);

					int type = item.getInt("type");// 1 段子 5 广告
					if (type == 5) {
						// TODO 处理广告
						AdEntity entity = new AdEntity();
						entity.parseJson(item);
						String downurl = entity.getDownloadUrl();
						Log.i("TestActivity:", downurl + "downurl");
					} else if (type == 1) {
						JSONObject group = item.getJSONObject("group");
						int cid = group.getInt("category_id");
						TextEntity entity = null;
						if (cid == 1) {
							// TODO 解析文本段子
							entity = new TextEntity();

						} else if (cid == 2) {
							// TODO 解析图片段子
							entity = new ImageEntity();
						}
						entity.parseJson(item);

						entities.add(entity);

						long groupId = entity.getGroupId();

						//Log.i("TestActivity:", groupId + "---groupId");
					}
					
				}
				//Log.i("TestActivity:",this.tip + "---tip");
			}
		}
	}
}