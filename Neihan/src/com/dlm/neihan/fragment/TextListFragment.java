package com.dlm.neihan.fragment;

import java.util.Currency;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.DateSorter;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.dlm.neihan.R;
import com.dlm.neihan.activity.EssayDetailActivity;
import com.dlm.neihan.adapter.EssayAdapter;
import com.dlm.neihan.bean.DataStore;
import com.dlm.neihan.bean.EntityList;
import com.dlm.neihan.bean.TextEntity;
import com.dlm.neihan.client.ClientAPI;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * 列表界面，第一次启动，并且数据为空的时候，自动加载数据 只要列表没有数据 ，进入这个界面的时候，就尝试加载数据 只要有数据，就不进行数据的加载
 * 进入这个界面，并且有数据的话，尝试检查新信息的个数
 * 
 * @author dlm
 * 
 */
public class TextListFragment extends Fragment implements OnClickListener,
		OnScrollListener, OnRefreshListener2<ListView>, OnItemClickListener {

	private Handler hander = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int what = msg.what;
			if (what == 1) {
				// TODO what = 1 有新消息
				textNotifiy.setVisibility(View.INVISIBLE);
			}
		}

	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.textlist_title:
			textNotifiy.setVisibility(View.VISIBLE);
			hander.sendEmptyMessageDelayed(1, 3000);
			break;

		default:
			break;
		}

	}

	private View quickTools;
	private TextView textNotifiy;
	private EssayAdapter adapter;
//	private List<TextEntity> entities;

	// 1 ---text 文本
	public static final int CATEGORY_TEXT = 1;

	// 2 ---- image 图片
	public static final int CATEGORY_IMAGE = 2;

	private RequestQueue queue;
	private long lastTime;

	// 请求的分类类型ID
	private int requestCategory = CATEGORY_TEXT;

	public TextListFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if(queue==null){
		queue = Volley.newRequestQueue(getActivity());
		}
		if(savedInstanceState != null){
			lastTime = savedInstanceState.getLong("lastTime");
			Log.d("TextListFragment", "Reload state: lastTime: " + lastTime);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		
		if(savedInstanceState!=null){
			lastTime = savedInstanceState.getLong("lastTime");
			Log.i("TextListFragment:", lastTime+"");
		}
		View view = inflater.inflate(R.layout.fragment_textlist, container,
				false);
		// 获取标题控件，加点击事件，新消息悬浮框显示的功能
		View titleview = view.findViewById(R.id.textlist_title);
		titleview.setOnClickListener(this);

		// TODO 获取listview并且设置数据
		PullToRefreshListView refreshListView = (PullToRefreshListView) view
				.findViewById(R.id.textlist_listview);

		// 设置上啦与下拉的事件监听
		refreshListView.setOnRefreshListener(this);

		refreshListView.setMode(Mode.BOTH);
		// ListView listView = (ListView)
		// view.findViewById(R.id.textlist_listview);

		ListView listView = refreshListView.getRefreshableView();

		
		// List<String> strings = new LinkedList<String>();
		// for (int i = 0; i < 60; i++) {
		// strings.add("内涵段子来了");
		// }

		header = inflater.inflate(R.layout.textlist_header, listView, false);
		listView.addHeaderView(header);

		View quickPublish = header.findViewById(R.id.quick_tools_publish);
		quickPublish.setOnClickListener(this);
		quickPublish.setFocusable(false);
		View quickReview = header.findViewById(R.id.quick_tools_review);
		quickReview.setOnClickListener(this);
		quickReview.setFocusable(false);
		//此句作用：当滑到其他fagment,在回到本fragment时不用重新加载数据
		List<TextEntity> entities = DataStore.getInstance().getTextEntities();
//		if(entities==null){
//			entities = new LinkedList<TextEntity>();
//		}
		
		adapter = new EssayAdapter(getActivity(), entities);

		adapter.setListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(v instanceof TextView){
					String string = (String)v.getTag();
					
					if(string != null){
						
						int position = Integer.parseInt(string);
						
					
						
						
						Intent intent = new Intent(getActivity(), EssayDetailActivity.class);
						
						intent.putExtra("currentEssayPosition", position);
						
						intent.putExtra("category", requestCategory);
						
						startActivity(intent);
					}
					
				}
			}
		});
		listView.setAdapter(adapter);

		listView.setOnScrollListener(this);
		listView.setOnItemClickListener(this);
		// TODO获取快速的工具条（发布和审核，用于列表滚动的显示和影藏

		quickTools = view.findViewById(R.id.textlist_quick_tools);
		quickTools.setVisibility(View.INVISIBLE);

		quickPublish = quickTools.findViewById(R.id.quick_tools_publish);
		quickPublish.setOnClickListener(this);

		quickReview = quickTools.findViewById(R.id.quick_tools_review);
		quickReview.setOnClickListener(this);

		// TODO获取新条目通知控件,新消息提醒

		textNotifiy = (TextView) view.findViewById(R.id.textlist_new_notifiy);
		textNotifiy.setVisibility(View.INVISIBLE);
		return view;
	}
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		Log.d("TextListFragment", "Save state: lastTime: " + lastTime);
		outState.putLong("lastTime", lastTime);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		this.adapter = null;
		this.header = null;
		this.quickTools = null;
		this.textNotifiy = null;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
	}

	// /////////////////////////////////////////////////////////////////////////////////////
	// 列表滚动显示工具条

	private int lastIndex = 0;
	private View header;

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		int offset = lastIndex - firstVisibleItem;

		if (offset < 0 || firstVisibleItem == 0) {
			// 两者只差小于0，证明向上移动
			if (quickTools != null) {
				quickTools.setVisibility(View.INVISIBLE);
			}

		} else if (offset > 0) {
			if (quickTools != null) {
				quickTools.setVisibility(View.VISIBLE);

			}
		}
		lastIndex = firstVisibleItem;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub

	}

	// /////////////////////////////////////////////////////////////////////////////////////

	// 列表下拉刷新与上啦加载
	/**
	 * 下拉 进行加载新数据
	 * 
	 * @param refreshView
	 */
	@Override
	public void onPullDownToRefresh(final PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		ClientAPI.getList(queue, requestCategory, 30, lastTime,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String arg0) {
						// TOTO加载新数据
						
						listonResponse(arg0);
						refreshView.onRefreshComplete();
					}
				});
	}

	/**
	 * 上拉，考虑是否加载旧的数据
	 * 
	 * @param refreshView
	 */
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
				//Log.i("lasttime", "---" + lastTime);
			} else {
				// 没有更多的数据，提示休息一会儿
				String tip = entityList.getTip();
				//Log.i("TestActivity", "Tip" + tip);
			}

			// 获取段子内容列表

			// List<TextEntity> entities = entityList.getEntities();

			// TODO 把entityList 这个段子的数据集合传递给listview之类的adapter即可显示
			
			List<TextEntity> ets = entityList.getEntities();
			if(ets!=null){
				if(!ets.isEmpty()){
					//把ets中的内容按照迭代器的顺序添加，
					DataStore.getInstance().addEntities(ets);
					
					//entities.addAll(0,ets);
					
					//把object添加到指定的location位置，原有的内容向后移动
					
					//手动添加
//					int len = ets.size();
//					for(int index = len-1;index>=0;index--){
//						entities.add(0, ets.get(index));
//					}
					adapter.notifyDataSetChanged();
				}else{
					//TODO 没有更多的数据，需要提示一下
				}
			}else{
				//TODO 没有获取到网络数据，可能是数据解析错误，网络错误，需要提示一下
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub

	}
	// /////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		arg2--;
//		DataStore store = DataStore.getInstance();
//		List<TextEntity> entities = store.getTextEntities();
//		TextEntity entity = entities.get(arg2);
		Intent intent = new Intent(getActivity(), EssayDetailActivity.class);
		
		intent.putExtra("currentEssayPosition", arg2);
		
		intent.putExtra("category", requestCategory);
		
		startActivity(intent);
		
	}

}
