package com.dlm.neihan.bean;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ImageUrlList {
	private List<String> large_imageUrls;
	private String uri;
	private int width;
	private int height;
	public List<String> getLarge_imageUrls() {
		return large_imageUrls;
	}
	public String getUri() {
		return uri;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public void parseJson(JSONObject json){
		try {
			large_imageUrls = parseImageUrlList(json);
			uri = json.getString("uri");
			width = json.getInt("width");
			height = json.getInt("height");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	private List<String> parseImageUrlList(JSONObject large_image)
			throws JSONException {
		JSONArray urls = large_image.getJSONArray("url_list");

		List<String> large_imageUrls = new LinkedList<String>();

		int ulen = urls.length();

		for (int j = 0; j < ulen; j++) {
			JSONObject uobj = urls.getJSONObject(j);
			String url = uobj.getString("url");
			large_imageUrls.add(url);
		}
		return large_imageUrls;
	}
	
}
