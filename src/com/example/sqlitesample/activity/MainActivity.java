package com.example.sqlitesample.activity;

import java.util.List;
import java.util.Random;

import com.example.sqlitesample.R;
import com.example.sqlitesample.R.layout;
import com.example.sqlitesample.R.menu;
import com.example.sqlitesample.model.Comment;
import com.example.sqlitesample.model.CommentsDataSource;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;

public class MainActivity extends ListActivity  {

	private CommentsDataSource datasource;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		datasource = new CommentsDataSource(this);
		datasource.open();

		List<Comment> values = datasource.getAllComments();

		// Use the SimpleCursorAdapter to show the
		// elements in a ListView
		ArrayAdapter<Comment> adapter = new ArrayAdapter<Comment>(this,
				android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
	}

	// Will be called via the onClick attribute
	// of the buttons in main.xml
	public void onClick(View view) {
		@SuppressWarnings("unchecked")
		ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>) getListAdapter();
		Comment comment = null;
		switch (view.getId()) {
		case R.id.add:
			String[] comments = new String[] { "Cool", "Very nice", "Hate it" };
			int nextInt = new Random().nextInt(3);
			// Save the new comment to the database
			comment = datasource.createComment(comments[nextInt]);
			adapter.add(comment);
			break;
		case R.id.delete:
			if (getListAdapter().getCount() > 0) {
				comment = (Comment) getListAdapter().getItem(0);
				datasource.deleteComment(comment);
				adapter.remove(comment);
			}
			break;
		}
		adapter.notifyDataSetChanged();
	}

	@Override
	protected void onResume() {
		datasource.open();
		super.onResume();
	}

	@Override
	protected void onPause() {
		datasource.close();
		super.onPause();
	}


}
