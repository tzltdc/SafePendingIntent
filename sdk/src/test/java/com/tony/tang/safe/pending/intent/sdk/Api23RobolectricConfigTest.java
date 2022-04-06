package com.tony.tang.safe.pending.intent.sdk;

import static com.google.common.truth.Truth.assertThat;

import android.os.Build;
import androidx.test.core.app.ApplicationProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@Config(sdk = Build.VERSION_CODES.M)
@RunWith(RobolectricTestRunner.class)
public class Api23RobolectricConfigTest {

  @Test
  public void testIt() {
    // expected
    assertThat(Build.VERSION.SDK_INT).isEqualTo(23);

    // surprised to our intuition.
    assertThat(
            ApplicationProvider.getApplicationContext().getApplicationInfo().targetSdkVersion
                >= Build.VERSION_CODES.S)
        .isTrue();
  }
}
