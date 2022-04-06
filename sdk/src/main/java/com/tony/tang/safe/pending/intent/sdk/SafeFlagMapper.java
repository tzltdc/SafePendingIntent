package com.tony.tang.safe.pending.intent.sdk;

import android.app.PendingIntent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import com.tony.tang.safe.pending.intent.sdk.SafePendingIntent.Option;

class SafeFlagMapper {

  private SafeFlagMapper() {}

  public static int map(FlagParam flagParam) {
    Option option = flagParam.option();
    switch (option) {
      case IMMUTABLE:
        return appendImmutableIfNeed(flagParam);
      case MUTABLE:
        return appendMutableIfNeed(flagParam);
      case DEFAULT:
      default:
        return applyDefaultRules(flagParam);
    }
  }

  private static int applyDefaultRules(FlagParam flagParam) {
    if (VERSION.SDK_INT < VERSION_CODES.S) {
      return flagParam.rawFlag();
    } else {
      boolean immutableSpecific = (flagParam.rawFlag() & PendingIntent.FLAG_IMMUTABLE) > 0;
      boolean mutableSpecific = (flagParam.rawFlag() & PendingIntent.FLAG_MUTABLE) > 0;
      return mutableSpecific || immutableSpecific
          ? flagParam.rawFlag()
          : flagParam.rawFlag() | PendingIntent.FLAG_IMMUTABLE;
    }
  }

  private static int appendMutableIfNeed(FlagParam flagParam) {
    if (VERSION.SDK_INT < VERSION_CODES.S) {
      return flagParam.rawFlag();
    } else {
      if (FlagHelper.rawFlagContainsFlag(flagParam.rawFlag(), PendingIntent.FLAG_IMMUTABLE)) {
        throw new IllegalStateException("Conflicted with raw flag FLAG_IMMUTABLE");
      } else {
        return flagParam.rawFlag() | PendingIntent.FLAG_MUTABLE;
      }
    }
  }

  private static int appendImmutableIfNeed(FlagParam flagParam) {
    if (VERSION.SDK_INT < VERSION_CODES.M) {
      return flagParam.rawFlag();
    } else {
      if (FlagHelper.rawFlagContainsFlag(flagParam.rawFlag(), PendingIntent.FLAG_MUTABLE)) {
        throw new IllegalStateException("Conflicted with raw flag FLAG_MUTABLE");
      } else {
        return flagParam.rawFlag() | PendingIntent.FLAG_IMMUTABLE;
      }
    }
  }
}
