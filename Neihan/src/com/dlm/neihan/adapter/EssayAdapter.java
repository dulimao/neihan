package com.dlm.neihan.adapter;

import java.util.List;
import java.util.zip.Inflater;

import pl.droidsonroids.gif.GifImageView;

import android.content.Context;
import android.media.Image;
import android.provider.Telephony.Sms.Conversations;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dlm.neihan.R;
import com.dlm.neihan.bean.TextEntity;
import com.dlm.neihan.bean.UserEntity;

public class EssayAdapter extends BaseAdapter {
	private Context context;
	private List<TextEntity> entities;
	private LayoutInflater inflater;

	public EssayAdapter(Context context, List<TextEntity> entities) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.entities = entities;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return entities.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return entities.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		View ret = arg1;
		if (arg1 == null) {
			ret = inflater.inflate(R.layout.item_essay_, arg2, false);

		}
		if (ret != null) {
			ViewHolder holder = (ViewHolder) ret.getTag();
			if (holder == null) {
				holder = new ViewHolder();
				holder.btnGifPlay = (TextView) ret
						.findViewById(R.id.btnGifPlay);
				holder.btnShare = (ImageButton) ret
						.findViewById(R.id.item_share);
				holder.chbBuryCount = (CheckBox) ret
						.findViewById(R.id.item_bury_count);
				holder.chbDiggCount = (CheckBox) ret
						.findViewById(R.id.item_digg_count);
				holder.gifImageView = (GifImageView) ret
						.findViewById(R.id.gifview);
				holder.imgprofileImage = (ImageView) ret
						.findViewById(R.id.item_profile_image);
				holder.pbDownProgress = (ProgressBar) ret
						.findViewById(R.id.item_image_download_progress);
				holder.txtCommentCount = (TextView) ret
						.findViewById(R.id.item_comment_count);
				holder.txtContext = (TextView) ret
						.findViewById(R.id.item_content);
				holder.txtprofileNick = (TextView) ret
						.findViewById(R.id.item_profile_nick);
				ret.setTag(holder);
			}
			TextEntity entity = entities.get(arg0);
			//1.先设置文本内容的数据
			
			UserEntity user = entity.getUser();
			String nick = "";//清空，防止错位
			if(user!=null){
				nick = user.getName();
				
			}
			holder.txtprofileNick.setText(nick);
			
			String content = entity.getContent();
			holder.txtContext.setText(content);
			int diggcount = entity.getDiggCount();
			holder.chbDiggCount.setText(""+diggcount);
			
			int userDigg = entity.getUserDigg();//当前用户是否赞过，
			//如果usrDigg 为1 已经赞过，那么chbDiggCount必须禁用，所以用！=1;
			holder.chbDiggCount.setEnabled(userDigg!=1);
			
			int buryCount = entity.getBuryCount();
			holder.chbBuryCount.setText(Integer.toString(buryCount));
			
			int userBury = entity.getUserBury();//是否彩果
			//如果usrBury 为1 已经踩过，那么chbBuryCount必须禁用，所以用！=1;
			holder.chbBuryCount.setEnabled(userBury!=1);
			
			//评论个数
			int commentcount = entity.getCoummentCount();
			holder.txtCommentCount.setText(Integer.toString(commentcount));
			
			
			//2.再设置图片内容的数据
			
			//TODO 需要加载各种图片数据
		}
		return ret;
	}

	private static class ViewHolder {
		/**
		 * 头像
		 */
		public ImageView imgprofileImage;
		/**
		 * 昵称
		 */
		public TextView txtprofileNick;
		/**
		 * 文本
		 */
		public TextView txtContext;
		/**
		 * 下载进度
		 */
		public ProgressBar pbDownProgress;
		/**
		 * 图片显示
		 */
		public GifImageView gifImageView;

		public TextView btnGifPlay;
		/**
		 * 赞，包含个数，如果赞过，就禁用这个控件
		 */
		public CheckBox chbDiggCount;
		/**
		 * 踩
		 */
		public CheckBox chbBuryCount;
		/**
		 * 评论
		 */
		public TextView txtCommentCount;
		public ImageButton btnShare;
	}
}
