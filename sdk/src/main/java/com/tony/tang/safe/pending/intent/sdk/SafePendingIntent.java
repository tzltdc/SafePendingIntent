package com.tony.tang.safe.pending.intent.sdk;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * A helper class to enforce consumers of {@link PendingIntent} creation API specify its ideal
 * behavior.
 */
public class SafePendingIntent {

  // Default version to create PendingIntent

  public static PendingIntent getBroadcast(
      Context context, int requestCode, Intent intent, int flags) {
    return getBroadcast(Option.DEFAULT, context, requestCode, intent, flags);
  }

  public static PendingIntent getActivity(
      Context context, int requestCode, @NonNull Intent intent, int flags) {
    return getActivity(Option.DEFAULT, context, requestCode, intent, flags);
  }

  public static PendingIntent getActivity(
      Context context,
      int requestCode,
      @NonNull Intent intent,
      int flags,
      @Nullable Bundle options) {
    return getActivity(Option.DEFAULT, context, requestCode, intent, flags, options);
  }

  public static PendingIntent getActivities(
      Context context,
      int requestCode,
      @NonNull Intent[] intents,
      int flags,
      @Nullable Bundle options) {
    return getActivities(Option.DEFAULT, context, requestCode, intents, flags, options);
  }

  public static PendingIntent getActivities(
      Context context, int requestCode, @NonNull Intent[] intents, int flags) {
    return getActivities(Option.DEFAULT, context, requestCode, intents, flags, null);
  }

  public static PendingIntent getService(
      Context context, int requestCode, @NonNull Intent intent, int flags) {
    return getService(Option.DEFAULT, context, requestCode, intent, flags);
  }

  @RequiresApi(api = Build.VERSION_CODES.O)
  public static PendingIntent getForegroundService(
      Context context, int requestCode, @NonNull Intent intent, int flags) {
    return getForegroundService(Option.DEFAULT, context, requestCode, intent, flags);
  }

  // Actual implementation
  public static PendingIntent getBroadcast(
      Option option, Context context, int requestCode, Intent intent, int rawFlags) {
    return PendingIntent.getBroadcast(
        context, requestCode, intent, SafeFlagMapper.map(FlagParam.create(option, rawFlags)));
  }

  public static PendingIntent getActivity(
      Option option,
      Context context,
      int requestCode,
      @NonNull Intent intent,
      int flags,
      @Nullable Bundle options) {
    return PendingIntent.getActivity(
        context, requestCode, intent, SafeFlagMapper.map(FlagParam.create(option, flags)), options);
  }

  public static PendingIntent getActivity(
      Option option, Context context, int requestCode, @NonNull Intent intent, int flags) {
    return getActivity(option, context, requestCode, intent, flags, null);
  }

  public static PendingIntent getActivities(
      Option option, Context context, int requestCode, @NonNull Intent[] intents, int flags) {
    return getActivities(option, context, requestCode, intents, flags, null);
  }

  public static PendingIntent getActivities(
      Option option,
      Context context,
      int requestCode,
      @NonNull Intent[] intents,
      int flags,
      @Nullable Bundle options) {
    return PendingIntent.getActivities(
        context,
        requestCode,
        intents,
        SafeFlagMapper.map(FlagParam.create(option, flags)),
        options);
  }

  public static PendingIntent getService(
      Option option, Context context, int requestCode, @NonNull Intent intent, int flags) {
    return PendingIntent.getService(
        context, requestCode, intent, SafeFlagMapper.map(FlagParam.create(option, flags)));
  }

  @RequiresApi(api = Build.VERSION_CODES.O)
  public static PendingIntent getForegroundService(
      Option option, Context context, int requestCode, @NonNull Intent intent, int flags) {
    return PendingIntent.getForegroundService(
        context, requestCode, intent, SafeFlagMapper.map(FlagParam.create(option, flags)));
  }

  /**
   * An explicit option that requires the consumer of {@link SafePendingIntent} to be specified.
   *
   * <p>
   *
   * <p>If you just want to make sure that your app does not crash when it is targeted against
   * Android 12 with minimum effort, you could just simply pass in {@link #DEFAULT}, which will
   * apply the default behavior across different Android system.
   *
   * <p>For API below 31, it is NO OP. For API greater or equal 31, it will append {@link
   * PendingIntent#FLAG_IMMUTABLE}.
   *
   * <p>If you understand the subtle difference between {@link PendingIntent#FLAG_IMMUTABLE} and
   * {@link PendingIntent#FLAG_MUTABLE}, you could specify {@link #IMMUTABLE} if you want to make
   * your {@link PendingIntent} immutable. or {@link #MUTABLE} if you want to make your {@link
   * PendingIntent} mutable.
   */
  public enum Option {
    /** Append {@link PendingIntent#FLAG_IMMUTABLE} for API 31 or above. Otherwise, NO OP. */
    DEFAULT,
    /** Append {@link PendingIntent#FLAG_MUTABLE} for API 31 or above. Otherwise, NO OP. */
    MUTABLE,
    /** Append {@link PendingIntent#FLAG_IMMUTABLE} for API 23 or above. Otherwise, NO OP. */
    IMMUTABLE
  }
}
