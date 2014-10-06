package com.dlm.neihan;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.dlm.neihan.fragment.HuodongFragment;
import com.dlm.neihan.fragment.ImageListFragment;
import com.dlm.neihan.fragment.MineFragment;
import com.dlm.neihan.fragment.ReviewFragment;
import com.dlm.neihan.fragment.TextListFragment;

public class MainActivity extends FragmentActivity implements android.widget.RadioGroup.OnCheckedChangeListener{
	private List<Fragment> fragments;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		RadioGroup group = (RadioGroup) this.findViewById(R.id.main_tab_bar);
		group.setOnCheckedChangeListener(this);
		
		fragments = new LinkedList<Fragment>();
		
		fragments.add(new TextListFragment());
		fragments.add(new ImageListFragment());
		fragments.add(new ReviewFragment());
		fragments.add(new HuodongFragment());
		fragments.add(new MineFragment());
		
		Fragment fragment = fragments.get(0);
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction transation = manager.beginTransaction();
		transation.replace(R.id.main_fragment, fragment);
		transation.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}



	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		int childCount = group.getChildCount();
		int checkedIndex = 0;
		RadioButton btn = null;
		for(int i=0;i<childCount;i++){
			btn = (RadioButton) group.getChildAt(i);
			if(btn.isChecked()){
				checkedIndex = i;
				break;
			}
		}
		Fragment fragment = fragments.get(checkedIndex);
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction transation = manager.beginTransaction();
		transation.replace(R.id.main_fragment, fragment);
		transation.commit();
	}

}
