package com.tony.tang.safe.pending.intent.sdk;

import android.app.PendingIntent;
import android.os.Build;

class PendingIntentFlagProvider {
  public static int mutableFlags(int flags) {
    // force mutable on Android 12+
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
      flags |= PendingIntent.FLAG_MUTABLE;
    }

    // unset immutable on Android 6+
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      flags &= ~PendingIntent.FLAG_IMMUTABLE;
    }

    return flags;
  }

  public static int immutableFlags(int flags) {
    // unset mutability on Android 12+
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
      flags &= ~PendingIntent.FLAG_MUTABLE;
    }

    // set immutable on Android 6+
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      flags |= PendingIntent.FLAG_IMMUTABLE;
    }

    return flags;
  }
}
