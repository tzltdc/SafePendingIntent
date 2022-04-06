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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

/** Display main screen for sample. Displays controls for sending test notifications. */
public class MainActivity extends Activity {
  private static final String TAG = MainActivity.class.getSimpleName();

  private static final int NOTI_IMMUTABLE1 = 1100;
  private static final int NOTI_IMMUTABLE2 = 1101;
  private static final int NOTI_MUTABLE1 = 1200;
  private static final int NOTI_MUTABLE2 = 1201;

  private int immutable_button_1 = 0;
  private int immutable_button_2 = 0;
  private int mutable_button_1 = 0;
  private int mutable_button_2 = 0;
  /*
   * A view model for interacting with the UI elements.
   */
  private MainUi ui;

  /*
   * A
   */
  private NotificationHelper noti;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    noti = new NotificationHelper(this);
    ui = new MainUi(findViewById(R.id.activity_main));
    bindDetails();
  }

  private void bindDetails() {
    Button btn_details = findViewById(R.id.btn_details);
    btn_details.setOnClickListener(
        v -> startActivity(DetailActivity.constructDefaultIntent(MainActivity.this)));
  }

  /**
   * Send activity notifications.
   *
   * @param id The ID of the notification to create
   * @param title The title of the notification
   */
  public void sendNotification(int id, String title) {
    NotificationCompat.Builder nb = null;
    switch (id) {
      case NOTI_IMMUTABLE1:
        nb =
            noti.getNotification1(
                title,
                getString(R.string.immutable1_body),
                immutablePendingIntent(
                    String.format("Immutable SEND 1, clicked:%s", ++immutable_button_1)));
        break;

      case NOTI_IMMUTABLE2:
        nb =
            noti.getNotification1(
                title,
                getString(R.string.immutable2_body),
                immutablePendingIntent(
                    String.format("Immutable SEND 2, clicked:%s", ++immutable_button_2)));
        break;

      case NOTI_MUTABLE1:
        nb =
            noti.getNotification2(
                title,
                getString(R.string.mutable1_body),
                mutablePendingIntent(
                    String.format("Mutable SEND 1, clicked:%s", ++mutable_button_1)));
        break;

      case NOTI_MUTABLE2:
        nb =
            noti.getNotification2(
                title,
                getString(R.string.mutable2_body),
                mutablePendingIntent(
                    String.format("Mutable SEND 2, clicked:%s", ++mutable_button_2)));
        break;
    }
    if (nb != null) {
      noti.notify(id, nb);
    }
  }

  @SuppressLint("InlinedApi")
  @Nullable
  private PendingIntent immutablePendingIntent(String extraValue) {
    Intent notifyIntent = DetailActivity.constructIntent(this, extraValue);
    return PendingIntent.getActivity(this, 0, notifyIntent, PendingIntent.FLAG_IMMUTABLE);
  }

  @SuppressLint("InlinedApi")
  @Nullable
  private PendingIntent mutablePendingIntent(String extraValue) {
    Intent notifyIntent = DetailActivity.constructIntent(this, extraValue);
    return PendingIntent.getActivity(this, 0, notifyIntent, PendingIntent.FLAG_MUTABLE);
  }

  /** Send Intent to load system Notification Settings for this app. */
  public void goToNotificationSettings() {
    Intent i = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
    i.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
    startActivity(i);
  }

  /**
   * Send intent to load system Notification Settings UI for a particular channel.
   *
   * @param channel Name of channel to configure
   */
  public void goToNotificationSettings(String channel) {
    Intent i = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
    i.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
    i.putExtra(Settings.EXTRA_CHANNEL_ID, channel);
    startActivity(i);
  }

  /**
   * View model for interacting with Activity UI elements. (Keeps core logic for sample seperate.)
   */
  class MainUi implements View.OnClickListener {
    final TextView titleImmutable;
    final TextView titleMutable;

    private MainUi(View root) {
      titleImmutable = (TextView) root.findViewById(R.id.main_immutable_title);
      ((Button) root.findViewById(R.id.main_immutable_send1)).setOnClickListener(this);
      ((Button) root.findViewById(R.id.main_immutable_send2)).setOnClickListener(this);
      ((ImageButton) root.findViewById(R.id.main_immutable_config)).setOnClickListener(this);

      titleMutable = (TextView) root.findViewById(R.id.main_mutable_title);
      ((Button) root.findViewById(R.id.main_mutable_send1)).setOnClickListener(this);
      ((Button) root.findViewById(R.id.main_mutable_send2)).setOnClickListener(this);
      ((ImageButton) root.findViewById(R.id.main_mutable_config)).setOnClickListener(this);

      ((Button) root.findViewById(R.id.btnA)).setOnClickListener(this);
    }

    private String getTitleImmutableText() {
      if (titleImmutable != null) {
        return titleImmutable.getText().toString();
      }
      return "";
    }

    private String getTitleMutableText() {
      if (titleImmutable != null) {
        return titleMutable.getText().toString();
      }
      return "";
    }

    @Override
    public void onClick(View view) {
      switch (view.getId()) {
        case R.id.main_immutable_send1:
          sendNotification(NOTI_IMMUTABLE1, getTitleImmutableText());
          break;
        case R.id.main_immutable_send2:
          sendNotification(NOTI_IMMUTABLE2, getTitleImmutableText());
          break;
        case R.id.main_immutable_config:
          goToNotificationSettings(NotificationHelper.IMMUTABLE_CHANNEL);
          break;

        case R.id.main_mutable_send1:
          sendNotification(NOTI_MUTABLE1, getTitleMutableText());
          break;
        case R.id.main_mutable_send2:
          sendNotification(NOTI_MUTABLE2, getTitleMutableText());
          break;
        case R.id.main_mutable_config:
          goToNotificationSettings(NotificationHelper.MUTABLE_CHANNEL);
          break;
        case R.id.btnA:
          goToNotificationSettings();
          break;
        default:
          Log.e(TAG, "Unknown click event.");
          break;
      }
    }
  }
}
