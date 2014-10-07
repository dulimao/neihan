package com.dlm.neihan.bean;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 评论接口返回的data{} 数据部分的实体定义
 * 包含了top_comments 和 recent_comment 两个数组json格式如下
 * <br/>
 * <pre>
 * "data": {
         "top_comments": [],
         "recent_comments": [
             {
                 "uid": 0,
                 "platform": "feifei",
                 "text": "顶，这才是真爷们。",
                 "digg_count": 0,
                 "user_digg": 0,
                 "user_verified": false,
                 "bury_count": 0,
                 "user_profile_url": "",
                 "id": 3023949163,
                 "user_name": "李群4718553",
                 "user_bury": 0,
                 "user_profile_image_url": "http:\/\/mat1.gtimg.com\/www\/mb\/images\/head_50.jpg",
                 "description": "这个用户很懒，神马都木有写",
                 "create_time": 1389752393,
                 "user_id": 3013939443
             },...
             ]
         }
         </pre>
 * @author dlm
 *
 */
public class CommentList {
		private List<Comment> topComments;
		private List<Comment> recentComments;
		private long groupId;
		private int totalNumber;
		private boolean hasMore;
		public void parseJson(JSONObject json) throws JSONException {
			if(json!=null){
				groupId = json.getLong("group_id");
				
				totalNumber = json.getInt("total_number");
				
				hasMore =  json.getBoolean("has_more");
				JSONObject data = json.getJSONObject("data");
				//热门评论
				JSONArray topArray = data.getJSONArray("top_comments");
				
				if(topArray!=null){
					topComments = new LinkedList<Comment>();
					
					int len = topArray.length();
					
					if(len>0){
						for(int i=0;i<len;i++){
							JSONObject obj = topArray.getJSONObject(i);
							
							Comment comment = new Comment();
							
							comment.parseJson(obj);
							
							topComments.add(comment);
							
						}
					}
				}
				
				//最近评论
				JSONArray recentArray = data.getJSONArray("recent_comments");
				

				if(recentArray!=null){
					recentComments = new LinkedList<Comment>();
					
					int len = recentArray.length();
					
					if(len>0){
						for(int i=0;i<len;i++){
							JSONObject obj = recentArray.getJSONObject(i);
							
							Comment comment = new Comment();
							
							comment.parseJson(obj);
							
							recentComments.add(comment);
							
						}
					}
				}
			}
		}
		public List<Comment> getTopComments() {
			return topComments;
		}
		public List<Comment> getRecentComments() {
			return recentComments;
		}
		public long getGroupId() {
			return groupId;
		}
		public int getTotalNumber() {
			return totalNumber;
		}
		public boolean isHasMore() {
			return hasMore;
		}
		
}
