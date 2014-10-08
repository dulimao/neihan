package com.dlm.neihan.bean;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class ImageEntity extends TextEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1889400103094254811L;
	private ImageUrlList largeList;
	private ImageUrlList middleList;

	public ImageUrlList getLargeList() {
		return largeList;
	}

	public ImageUrlList getMiddleList() {
		return middleList;
	}

	public void parseJson(JSONObject item) {
		try {

			super.parseJson(item);

			JSONObject group = item.getJSONObject("group");
			JSONObject large_image = group.optJSONObject("large_image");
			JSONObject middle_image = group.optJSONObject("middle_image");

			// 大等图片的网址集合

			largeList = new ImageUrlList();
			if (large_image != null) {
				largeList.parseJson(large_image);
			}

			// 中等图片的网址集合
			middleList = new ImageUrlList();
			if (middle_image != null) {
				middleList.parseJson(middle_image);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
