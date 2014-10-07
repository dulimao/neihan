package com.dlm.neihan.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 文本段子实体
 * 
 * @author dlm
 * 
 */
public class TextEntity {

	private int type;
	private long createTime;
	private long onlineTime;// 上线时间
	private long displayTime;// 显示时间
	private int coummentCount;// 评论的总数
	private int diggCount;// digg总数
	private int status;// 状态，其中可选值3 需要分析具体含义
	// TODO ?
	private int userDigg;
	private long groupId;// 段子ID，访问详情和评论是，作为接口 的参数
	private int categoryId;// 内容类型，1 文本，2 图片
	private int buryCount;// 踩的总数
	private String content;// 文本段子内容
	private int userRepin;// 用户是否repine
	/**
	 * 代表当前用户是否踩了，0代表没有，1代表踩了
	 */
	private int userBury;
	private int level;// TODO ???? 两处出现：1.获取列表接口里面有 level = 6 2.文本段子实体中level = 4
	private int hasComments;// 当前用户是否评论
	// TODO 需要分析具体含义
	private int goDetailCount;
	/*
	 * 状态描述信息
	 */
	private String statusDesc;
	private int favoriteCount;// 赞的总数

	/**
	 * 代表当前用户是否赞了，0代表没有，1代表踩了
	 */
	private int userFavorite;

	private String shareUrl;// 用于第三方分享，提交的网址

	// TODO 分析这个字段的含义
	private int label;

	private int repinCount;// ??

	private int hasHotComment;// 是否含有热门评论

	private UserEntity user;

	// TODO 需要去分析comments 这个json数组中的内容是什么
	public void parseJson(JSONObject json) throws JSONException {
		this.onlineTime = json.getLong("online_time");
		this.displayTime = json.getLong("display_time");
		JSONObject group = json.getJSONObject("group");

		this.createTime = group.getLong("create_time");
		this.favoriteCount = group.getInt("favorite_count");
		this.userBury = group.getInt("user_bury");
		this.userFavorite = group.getInt("user_favorite");
		this.buryCount = group.getInt("bury_count");
		this.shareUrl = group.getString("share_url");
		this.label = group.optInt("label", 0);
		this.content = group.getString("content");
		this.coummentCount = group.getInt("comment_count");
		this.status = group.getInt("status");
		this.statusDesc = group.getString("status_desc");
		this.hasComments = group.getInt("has_comments");
		this.goDetailCount = group.getInt("go_detail_count");

		JSONObject uobj = group.getJSONObject("user");
		user = new UserEntity();
		user.parseJson(uobj);

		this.userDigg = group.getInt("user_digg");
		this.groupId = group.getLong("group_id");
		this.level = group.getInt("level");
		this.repinCount = group.getInt("repin_count");
		this.diggCount = group.getInt("digg_count");
		this.hasHotComment = group.optInt("has_hot_comments", 0);
		this.userRepin = group.getInt("user_repin");
		this.categoryId = group.getInt("category_id");
	}

	public long getOnlineTime() {
		return onlineTime;
	}

	public long getDisplayTime() {
		return displayTime;
	}

	public int getType() {
		return type;
	}

	public long getCreateTime() {
		return createTime;
	}

	public int getFavoriteCount() {
		return favoriteCount;
	}

	public int getUserBury() {
		return userBury;
	}

	public int getUserFavorite() {
		return userFavorite;
	}

	public int getBuryCount() {
		return buryCount;
	}

	public String getShareUrl() {
		return shareUrl;
	}

	public int getLabel() {
		return label;
	}

	public String getContent() {
		return content;
	}

	public int getCoummentCount() {
		return coummentCount;
	}

	public int getStatus() {
		return status;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public int getHasComments() {
		return hasComments;
	}

	public int getGoDetailCount() {
		return goDetailCount;
	}

	public int getUserDigg() {
		return userDigg;
	}

	public int getDiggCount() {
		return diggCount;
	}

	public long getGroupId() {
		return groupId;
	}

	public int getLevel() {
		return level;
	}

	public int getRepinCount() {
		return repinCount;
	}

	public int getUserRepin() {
		return userRepin;
	}

	public int getHasHotComment() {
		return hasHotComment;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public UserEntity getUser() {
		return user;
	}

}
// {
// "online_time": 1411878957,
// "display_time": 1411878957,
// "group": {
// "create_time": 1411718218.0,
// "favorite_count": 1209,
// "user_bury": 0,
// "user_favorite": 0,
// "bury_count": 1516,
// "share_url":
// "http://toutiao.com/group/3560859160/?iid=2337593504&app=joke_essay",
// "label": 1,
// "content":
// "甲:昨天碰到抢劫的，被打了两顿。乙:为啥啊？甲:他问我有钱吗我说没有，他从我身上搜出一包软中华然后就被打了一顿。等他走了，不一会儿又回来打了我一顿，因为他发现里面塞的是白红梅，劫匪走时还留下一句‘没钱还装B’",
// "comment_count": 177,
// "status": 3,
// "has_comments": 0,
// "go_detail_count": 4370,
// "status_desc": "已发表到热门列表",
// "user": {
// "avatar_url": "http://p1.pstatp.com/thumb/1367/2213311454",
// "user_id": 3080520868,
// "name": "请叫我梓安哥",
// "user_verified": false
// },
// "user_digg": 0,
// "group_id": 3560859160,
// "level": 4,
// "repin_count": 1209,
// "digg_count": 18424,
// "has_hot_comments": 1,
// "user_repin": 0,
// "category_id": 1
// },

