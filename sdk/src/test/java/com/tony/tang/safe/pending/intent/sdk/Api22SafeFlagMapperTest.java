package com.tony.tang.safe.pending.intent.sdk;

import static android.app.PendingIntent.FLAG_ONE_SHOT;
import static com.google.common.truth.Truth.assertThat;
import static com.tony.tang.safe.pending.intent.sdk.SafeFlagMapper.map;
import static com.tony.tang.safe.pending.intent.sdk.SafePendingIntent.Option.DEFAULT;
import static com.tony.tang.safe.pending.intent.sdk.SafePendingIntent.Option.IMMUTABLE;
import static com.tony.tang.safe.pending.intent.sdk.SafePendingIntent.Option.MUTABLE;

import android.os.Build;

import com.tony.tang.safe.pending.intent.sdk.FlagParam;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@Config(sdk = Build.VERSION_CODES.LOLLIPOP_MR1)
@RunWith(RobolectricTestRunner.class)
public class Api22SafeFlagMapperTest {

  @Test
  public void api_22_case_1_whenDefaultOptionProvided_shouldReturnDefaultOptionAsItIs() {
    assertThat(map(FlagParam.create(DEFAULT, android.app.PendingIntent.FLAG_ONE_SHOT)))
        .isEqualTo(FLAG_ONE_SHOT);
  }

  @Test
  public void api_22_case_2_whenMutableOptionProvided_shouldReturnDefaultOptionAsItIs() {
    assertThat(map(FlagParam.create(MUTABLE, android.app.PendingIntent.FLAG_ONE_SHOT)))
        .isEqualTo(FLAG_ONE_SHOT);
  }

  @Test
  public void api_22_case_3_whenImmutableOptionProvided_shouldReturnDefaultOptionAsItIs() {
    assertThat(map(FlagParam.create(IMMUTABLE, android.app.PendingIntent.FLAG_ONE_SHOT)))
        .isEqualTo(FLAG_ONE_SHOT);
  }
}
