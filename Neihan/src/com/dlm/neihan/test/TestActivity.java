package com.dlm.neihan.test;

import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.bool;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.dlm.neihan.R;
import com.dlm.neihan.bean.Comment;
import com.dlm.neihan.bean.CommentList;
import com.dlm.neihan.bean.EntityList;
import com.dlm.neihan.bean.TextEntity;
import com.dlm.neihan.client.ClientAPI;

public class TestActivity extends Activity implements Response.Listener<String> {

	// 1 ---text 文本
	public static final int CATEGORY_TEXT = 1;

	// 2 ---- image 图片
	public static final int CATEGORY_IMAGE = 2;

	private RequestQueue queue;
	private long lastTime;
	private int offset;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);

		queue = Volley.newRequestQueue(this);
		Log.i("lasttime", "---" + lastTime);

		// this.findViewById(R.id.next).setOnClickListener(new OnClickListener()
		// {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// int itemCount = 30;
		// ClientAPI.getList(queue, CATEGORY_IMAGE, itemCount, lastTime,
		// TestActivity.this);
		// }
		// });

		offset = 0;
		this.findViewById(R.id.next).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				long groupId = 3551461874l;// 对应文本段子的id
				ClientAPI
						.getComments(queue, groupId, offset, TestActivity.this);

			}
		});

	}

	public void next(View v) {
		Toast.makeText(this, "next", 1).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test, menu);
		return true;
	}

	/**
	 * 列表网络获取回调部分
	 * 
	 * @param arg0
	 *            json数据字符串
	 */
	public void listonResponse(String arg0) {
		// TODO Auto-generated method stub
		// Log.d("list", arg0);

		try {

			JSONObject json = new JSONObject(arg0);
			arg0 = json.toString(4);
			// Log.d("TestActity", "list:" + arg0);

			// 解析数据

			JSONObject obj = json.getJSONObject("data");
			// 解析段子列表的完整数据
			EntityList entityList = new EntityList();
			// 这个方法是解析json的方法，其中包含支持图片，文本，广告的解析
			entityList.parseJson(obj);
			if (entityList.isHasMore()) {
				lastTime = entityList.getMinTime();// 获取更新时间标志
				Log.i("lasttime", "---" + lastTime);
			} else {
				// 没有更多的数据，提示休息一会儿
				String tip = entityList.getTip();
				Log.i("TestActivity", "Tip" + tip);
			}

			// 获取段子内容列表

			// List<TextEntity> entities = entityList.getEntities();

			// TODO 把entityList 这个段子的数据集合传递给listview之类的adapter即可显示

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onResponse(String arg0) {
		// TODO Auto-generated method stub
		try {
			JSONObject json = new JSONObject(arg0);
			String data = json.toString(4);

			// Iterator<String> keys = json.keys();
			// while(keys.hasNext()){
			// String key = keys.next();
			// Log.d("TestActivity", key+"-----");
			// }

			// 解析获取到的评论列表
			CommentList commentList = new CommentList();
			// 评论包含热门评论，和新鲜评论，也有可能是空的
			commentList.parseJson(json);

			long g = commentList.getGroupId();
			// 是否还有数据
			boolean b = commentList.isHasMore();
			// 评论总数
			int t = commentList.getTotalNumber();
			Log.i("TestActivity", "groupid:" + g);
			Log.i("TestActivity", "hasmore:" + b);
			Log.i("TestActivity", "getTotalNumber:" + t);
			// 热门评论（可能为空，第一次offset为0时可能有数据）
			List<Comment> tc = commentList.getTopComments();
			if (tc != null) {
				for (Comment comment : tc) {
					Log.i("", "TOP" + comment.getText());
				}
			}
			// 新鲜评论同上

			// TODO 直接把CommentList提交给listview的adapter，这样可以进行是否还有内容的判断
			// 利用adapter跟新数据
			// 分页标志，要求服务器每次返回20条评论，通过hasmore来是否还要继续分页
			offset = offset + 20;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
