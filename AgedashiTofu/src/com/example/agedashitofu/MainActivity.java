package com.example.agedashitofu;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

public class MainActivity extends Activity {

	private static String techcrunchFeed = "http://feeds.feedburner.com/TechCrunch?fmt=xml";
	private RequestQueue queue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		queue = Volley.newRequestQueue(this);
		XmlRequest<List<TCNews>> tcRequest = new XmlRequest<List<TCNews>>(
				techcrunchFeed, new TCFeedParser<List<TCNews>>(),
				new Response.Listener<List<TCNews>>() {
					@Override
					public void onResponse(List<TCNews> response) {
						displayFeed(response);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						throw new RuntimeException(error);
					}
				});
		queue.add(tcRequest);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void displayFeed(List<TCNews> news) {
		ListView listView = (ListView) findViewById(R.id.custom_list);
		listView.setAdapter(new CustomListAdapter(this, queue, news));
	}

}
