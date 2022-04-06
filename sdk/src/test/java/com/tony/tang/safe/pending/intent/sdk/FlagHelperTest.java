package com.tony.tang.safe.pending.intent.sdk;

import static android.app.PendingIntent.FLAG_IMMUTABLE;
import static android.app.PendingIntent.FLAG_MUTABLE;
import static android.app.PendingIntent.FLAG_NO_CREATE;
import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;
import static com.google.common.truth.Truth.assertThat;

import com.tony.tang.safe.pending.intent.sdk.FlagHelper;

import org.junit.Test;

public class FlagHelperTest {

  @Test
  public void returnZeroIfTheFlagIsNotContained() {

    assertThat(FLAG_MUTABLE & (FLAG_NO_CREATE | FLAG_UPDATE_CURRENT | FLAG_IMMUTABLE)).isEqualTo(0);
  }

  @Test
  public void returnTheTargetFlagOnlyIfTheFlagIsContained() {

    assertThat(FLAG_MUTABLE & (FLAG_NO_CREATE | FLAG_UPDATE_CURRENT | FLAG_MUTABLE))
        .isEqualTo(FLAG_MUTABLE);
  }

  @Test
  public void returnFalseIfTheFlagIsNotContained() {
    assertThat(
            FlagHelper.rawFlagContainsFlag(
                (FLAG_NO_CREATE | FLAG_UPDATE_CURRENT | FLAG_IMMUTABLE), FLAG_MUTABLE))
        .isEqualTo(false);
  }

  @Test
  public void returnTrueIfTheFlagIsNotContained() {
    assertThat(
            FlagHelper.rawFlagContainsFlag(
                (FLAG_NO_CREATE | FLAG_UPDATE_CURRENT | FLAG_MUTABLE), FLAG_MUTABLE))
        .isEqualTo(true);
  }
}
