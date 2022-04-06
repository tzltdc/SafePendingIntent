package com.tony.tang.safe.pending.intent.sdk;

import com.google.auto.value.AutoValue;
import com.tony.tang.safe.pending.intent.sdk.SafePendingIntent.Option;

/** The wrap input param to build {@link android.app.PendingIntent}. */
@AutoValue
abstract class FlagParam {

  public abstract Option option();

  public abstract int rawFlag();

  public static FlagParam create(Option option, int rawFlag) {
    return new AutoValue_FlagParam(option, rawFlag);
  }
}
