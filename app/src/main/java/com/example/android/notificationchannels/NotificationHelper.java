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

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

/** Helper class to manage notification channels, and create notifications. */
class NotificationHelper extends ContextWrapper {
  private NotificationManager manager;
  public static final String IMMUTABLE_CHANNEL = "default";
  public static final String MUTABLE_CHANNEL = "second";

  /**
   * Registers notification channels, which can be used later by individual notifications.
   *
   * @param context The application context
   */
  public NotificationHelper(Context context) {
    super(context);
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
      initializeNotificationChannel();
    }
  }

  @RequiresApi(api = Build.VERSION_CODES.O)
  private void initializeNotificationChannel() {

    NotificationChannel chan1 =
        new NotificationChannel(
            IMMUTABLE_CHANNEL,
            getString(R.string.noti_channel_default),
            NotificationManager.IMPORTANCE_DEFAULT);
    chan1.setLightColor(Color.GREEN);
    chan1.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
    getManager().createNotificationChannel(chan1);

    NotificationChannel chan2 =
        new NotificationChannel(
            MUTABLE_CHANNEL,
            getString(R.string.noti_channel_second),
            NotificationManager.IMPORTANCE_HIGH);
    chan2.setLightColor(Color.BLUE);
    chan2.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
    getManager().createNotificationChannel(chan2);
  }

  /**
   * Get a notification of type 1
   *
   * <p>Provide the builder rather than the notification it's self as useful for making notification
   * changes.
   *
   * @param title the title of the notification
   * @param body the body text for the notification
   * @param pendingIntent pendingIntent
   * @return the builder as it keeps a reference to the notification (since API 24)
   */
  public NotificationCompat.Builder getNotification1(
      String title, String body, PendingIntent pendingIntent) {
    return new NotificationCompat.Builder(getApplicationContext(), IMMUTABLE_CHANNEL)
        .setContentTitle(title)
        .setContentText(body)
        .setSmallIcon(getSmallIcon())
        .setContentIntent(pendingIntent)
        .setAutoCancel(true);
  }

  /**
   * Build notification for mutable channel.
   *
   * @param title Title for notification.
   * @param body Message for notification.
   * @param pendingIntent pendingIntent
   * @return A Notification.Builder configured with the selected channel and details
   */
  public NotificationCompat.Builder getNotification2(
      String title, String body, PendingIntent pendingIntent) {
    return new NotificationCompat.Builder(getApplicationContext(), MUTABLE_CHANNEL)
        .setContentTitle(title)
        .setContentText(body)
        .setSmallIcon(getSmallIcon())
        .setContentIntent(pendingIntent)
        .setAutoCancel(true);
  }

  /**
   * Send a notification.
   *
   * @param id The ID of the notification
   * @param notification The notification object
   */
  public void notify(int id, NotificationCompat.Builder notification) {
    getManager().notify(id, notification.build());
  }

  /**
   * Get the small icon for this app
   *
   * @return The small icon resource id
   */
  private int getSmallIcon() {
    return android.R.drawable.stat_notify_chat;
  }

  /**
   * Get the notification manager.
   *
   * <p>Utility method as this helper works with it a lot.
   *
   * @return The system service NotificationManager
   */
  private NotificationManager getManager() {
    if (manager == null) {
      manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }
    return manager;
  }
}
