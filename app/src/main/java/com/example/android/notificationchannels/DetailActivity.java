/*
 * Copyright 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.notificationchannels;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/** Detailed screen for sample to show the extra values from intent. */
public class DetailActivity extends Activity {
  public static final String EXTRA_DETAILED_NAME = "EXTRA_DETAILED_NAME";

  public static Intent constructDefaultIntent(MainActivity activity) {
    return constructIntent(activity, "TonyTangAndroid");
  }

  public static Intent constructIntent(MainActivity activity, String extraName) {
    return new Intent(activity, DetailActivity.class).putExtra(EXTRA_DETAILED_NAME, extraName);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail);
    TextView tv_extra_name_value = findViewById(R.id.tv_extra_name_value);
    tv_extra_name_value.setText(getIntent().getStringExtra(EXTRA_DETAILED_NAME));
  }
}
