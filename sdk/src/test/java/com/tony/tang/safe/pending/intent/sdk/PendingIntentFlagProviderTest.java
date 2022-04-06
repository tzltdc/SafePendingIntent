package com.tony.tang.safe.pending.intent.sdk;

import static android.app.PendingIntent.FLAG_IMMUTABLE;
import static android.app.PendingIntent.FLAG_MUTABLE;
import static android.app.PendingIntent.FLAG_ONE_SHOT;
import static com.google.common.truth.Truth.assertThat;

import android.os.Build;

import com.tony.tang.safe.pending.intent.sdk.PendingIntentFlagProvider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
public class PendingIntentFlagProviderTest {

  @Config(sdk = Build.VERSION_CODES.LOLLIPOP)
  @Test
  public void api_21_alwaysKeepItAsItIs() {

    assertThat(PendingIntentFlagProvider.mutableFlags(FLAG_ONE_SHOT)).isEqualTo(FLAG_ONE_SHOT);

    assertThat(PendingIntentFlagProvider.immutableFlags(FLAG_ONE_SHOT)).isEqualTo(FLAG_ONE_SHOT);

    assertThat(PendingIntentFlagProvider.mutableFlags(FLAG_ONE_SHOT | FLAG_MUTABLE))
        .isEqualTo(FLAG_ONE_SHOT | FLAG_MUTABLE);
    assertThat(PendingIntentFlagProvider.immutableFlags(FLAG_ONE_SHOT | FLAG_IMMUTABLE))
        .isEqualTo(FLAG_ONE_SHOT | FLAG_IMMUTABLE);
  }

  @Config(sdk = Build.VERSION_CODES.M)
  @Test
  public void api_23_whenFlagIsAbsent_shouldOnlyAppendingImmutableFlag() {

    assertThat(PendingIntentFlagProvider.mutableFlags(FLAG_ONE_SHOT)).isEqualTo(FLAG_ONE_SHOT);

    // Only this statement add extra use case.
    assertThat(PendingIntentFlagProvider.immutableFlags(FLAG_ONE_SHOT))
        .isEqualTo(FLAG_ONE_SHOT | FLAG_IMMUTABLE);

    assertThat(PendingIntentFlagProvider.mutableFlags(FLAG_ONE_SHOT | FLAG_MUTABLE))
        .isEqualTo(FLAG_ONE_SHOT | FLAG_MUTABLE);
    assertThat(PendingIntentFlagProvider.immutableFlags(FLAG_ONE_SHOT | FLAG_IMMUTABLE))
        .isEqualTo(FLAG_ONE_SHOT | FLAG_IMMUTABLE);
  }

  @Config(sdk = Build.VERSION_CODES.S)
  @Test
  public void api_31_whenFlagIsAbsent_shouldAppendingIt() {

    assertThat(PendingIntentFlagProvider.mutableFlags(FLAG_ONE_SHOT))
        .isEqualTo(FLAG_ONE_SHOT | FLAG_MUTABLE);

    // Only this statement add extra use case.
    assertThat(PendingIntentFlagProvider.immutableFlags(FLAG_ONE_SHOT))
        .isEqualTo(FLAG_ONE_SHOT | FLAG_IMMUTABLE);

    assertThat(PendingIntentFlagProvider.mutableFlags(FLAG_ONE_SHOT | FLAG_MUTABLE))
        .isEqualTo(FLAG_ONE_SHOT | FLAG_MUTABLE);
    assertThat(PendingIntentFlagProvider.immutableFlags(FLAG_ONE_SHOT | FLAG_IMMUTABLE))
        .isEqualTo(FLAG_ONE_SHOT | FLAG_IMMUTABLE);
  }
}
