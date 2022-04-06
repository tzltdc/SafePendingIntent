package com.tony.tang.safe.pending.intent.sdk;

import static android.app.PendingIntent.FLAG_IMMUTABLE;
import static android.app.PendingIntent.FLAG_MUTABLE;
import static android.app.PendingIntent.FLAG_ONE_SHOT;
import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;
import static com.google.common.truth.Truth.assertThat;
import static com.tony.tang.safe.pending.intent.sdk.SafeFlagMapper.map;
import static com.tony.tang.safe.pending.intent.sdk.SafePendingIntent.Option.DEFAULT;
import static com.tony.tang.safe.pending.intent.sdk.SafePendingIntent.Option.IMMUTABLE;
import static com.tony.tang.safe.pending.intent.sdk.SafePendingIntent.Option.MUTABLE;
import static org.junit.Assert.assertThrows;

import android.os.Build;

import com.tony.tang.safe.pending.intent.sdk.FlagParam;
import com.tony.tang.safe.pending.intent.sdk.SafePendingIntent.Option;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@Config(sdk = Build.VERSION_CODES.M)
@RunWith(RobolectricTestRunner.class)
public class Api23SafeFlagMapperTest {

  @Test
  public void api_23_case_1_whenDefaultOptionProvidedForNormalFlag_shouldReturnFlagAsItIs() {
    assertThat(map(FlagParam.create(DEFAULT, FLAG_ONE_SHOT | FLAG_UPDATE_CURRENT)))
        .isEqualTo(FLAG_ONE_SHOT | FLAG_UPDATE_CURRENT);
  }

  @Test
  public void api_23_case_2_1_whenMutableOptionProvidedForNormalFlag_shouldReturnFlagAsItIs() {
    assertThat(map(FlagParam.create(MUTABLE, FLAG_ONE_SHOT | FLAG_UPDATE_CURRENT)))
        .isEqualTo(FLAG_ONE_SHOT | FLAG_UPDATE_CURRENT);
  }

  @Test
  public void
      api_23_case_2_2_whenMutableOptionProvidedForImmutableFlag_shouldStillReturnFlagAsItIs() {
    // In API between 23 to 30, There is no flag such as PendingIntent#FLAG_MUTABLE.
    // Hence, we just return it as it as.
    assertThat(map(FlagParam.create(MUTABLE, FLAG_IMMUTABLE | FLAG_UPDATE_CURRENT)))
        .isEqualTo(FLAG_IMMUTABLE | FLAG_UPDATE_CURRENT);
  }

  @Test
  public void api_23_case_3_whenImmutableOptionProvided_shouldAppendImmutableFlag() {
    assertThat(map(FlagParam.create(IMMUTABLE, FLAG_ONE_SHOT | FLAG_UPDATE_CURRENT)))
        .isEqualTo(FLAG_IMMUTABLE | FLAG_ONE_SHOT | FLAG_UPDATE_CURRENT);
  }

  @Ignore("Such unit test is not required as The raw FLAG_MUTABLE flag does not exist in API 23.")
  @Test
  public void api_23_case_4_whenConflictedStateProvided_shouldThrowException() {
    IllegalStateException e =
        assertThrows(
            IllegalStateException.class,
            () -> map(FlagParam.create(Option.IMMUTABLE, FLAG_MUTABLE)));
    assertThat(e).hasMessageThat().contains("Conflicted with raw flag FLAG_MUTABLE");
  }
}
