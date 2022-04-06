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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@Config(sdk = Build.VERSION_CODES.S)
@RunWith(RobolectricTestRunner.class)
public class Api31SafeFlagMapperTest {

  @Test
  public void
      api_31_case_1_whenDefaultOptionProvidedForNormalFlag_shouldReturnAppendImmutableFlag() {
    assertThat(map(FlagParam.create(DEFAULT, FLAG_ONE_SHOT | FLAG_UPDATE_CURRENT)))
        .isEqualTo(FLAG_IMMUTABLE | FLAG_ONE_SHOT | FLAG_UPDATE_CURRENT);
  }

  @Test
  public void api_31_case_2_0_whenOptionIncludedInRawFlag_shouldReturnAsItIs() {
    assertThat(map(FlagParam.create(MUTABLE, FLAG_MUTABLE))).isEqualTo(FLAG_MUTABLE);
    assertThat(map((FlagParam.create(IMMUTABLE, FLAG_IMMUTABLE)))).isEqualTo(FLAG_IMMUTABLE);
  }

  @Test
  public void api_31_case_2_1_whenMutableOptionProvidedForNormalFlag_shouldAppendMutableFlag() {
    assertThat(map(FlagParam.create(MUTABLE, FLAG_ONE_SHOT | FLAG_UPDATE_CURRENT)))
        .isEqualTo(FLAG_MUTABLE | FLAG_ONE_SHOT | FLAG_UPDATE_CURRENT);
  }

  @Test
  public void api_31_case_2_2_whenImmutableOptionProvidedForNormalFlag_shouldAppendImmutableFlag() {
    assertThat(map(FlagParam.create(IMMUTABLE, FLAG_ONE_SHOT | FLAG_UPDATE_CURRENT)))
        .isEqualTo(FLAG_IMMUTABLE | FLAG_ONE_SHOT | FLAG_UPDATE_CURRENT);
  }

  @Test
  public void
      api_31_case_2_3_whenImmutableOptionProvidedForMutableFlag_shouldAppendImmutableFlag() {
    IllegalStateException e =
        assertThrows(
            IllegalStateException.class,
            () -> map(FlagParam.create(Option.IMMUTABLE, FLAG_MUTABLE)));
    assertThat(e).hasMessageThat().contains("Conflicted with raw flag FLAG_MUTABLE");
  }

  @Test
  public void
      api_31_case_2_4_whenMutableOptionProvidedForImmutableFlag_shouldAppendImmutableFlag() {
    IllegalStateException e =
        assertThrows(
            IllegalStateException.class,
            () -> map(FlagParam.create(Option.MUTABLE, FLAG_IMMUTABLE)));
    assertThat(e).hasMessageThat().contains("Conflicted with raw flag FLAG_IMMUTABLE");
  }
}
