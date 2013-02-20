/*
 * Copyright (C) 2012-2013 AChep@xda <artemchep@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.achep.FifteenPuzzle.updater;

import com.achep.FifteenPuzzle.R;
import com.achep.FifteenPuzzle.Toast;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class AutoUpdater extends Activity implements OnClickListener {

	private ImageView mDownloadButton;
	private RelativeLayout mBackButton;
	private TextView mChangelog;
	private ProgressBar mProgressBar;
	private ScrollView mChangelogPanel;

	private String mVersionName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mVersionName = getIntent().getAction();
		if (mVersionName != null) {
			setContentView(R.layout.activity_auto_updater);

			final TextView newVersion = (TextView) findViewById(R.id.version);
			newVersion.setText(mVersionName);

			mDownloadButton = (ImageView) findViewById(R.id.download);
			mDownloadButton.setOnLongClickListener(new OnLongClickListener() {

				@Override
				public boolean onLongClick(View v) {
					Toast.show(AutoUpdater.this,
							R.string.action_bar_download_new_version);
					return true;
				}
			});
			mBackButton = (RelativeLayout) findViewById(R.id.back);
			mChangelog = (TextView) findViewById(R.id.content);
			mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
			mChangelogPanel = (ScrollView) findViewById(R.id.content_panel);

			// OnClickListeners
			mDownloadButton.setOnClickListener(this);
			mBackButton.setOnClickListener(this);

			// Time cap
			mChangelog.setText("Function isn't availabel yet.");
			mChangelogPanel.setVisibility(View.VISIBLE);
			mChangelogPanel.startAnimation(AnimationUtils.loadAnimation(this,
					R.anim.widget_list_view_panel_in));

			mProgressBar.setVisibility(View.GONE);
			mDownloadButton.setVisibility(View.VISIBLE);
		} else {
			finish();
		}
	}

	@Override
	public void onClick(View v) {
		if (v == mDownloadButton) {
			startService(new Intent(this, DownloadService.class)
					.setAction(mVersionName));
		} else if (v == mBackButton) {
			finish();
		}
	}

}