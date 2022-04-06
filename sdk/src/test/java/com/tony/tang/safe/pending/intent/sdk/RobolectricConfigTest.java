package com.tony.tang.safe.pending.intent.sdk;

import static com.google.common.truth.Truth.assertThat;

import android.os.Build;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@Config(sdk = Build.VERSION_CODES.S)
@RunWith(RobolectricTestRunner.class)
public class RobolectricConfigTest {

  @Test
  public void returnZeroIfTheFlagIsNotContained() {
    assertThat(Build.VERSION.SDK_INT).isEqualTo(31);
  }
}
