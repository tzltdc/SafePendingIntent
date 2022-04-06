package com.tony.tang.safe.pending.intent.sdk;

class FlagHelper {

  public static boolean rawFlagContainsFlag(int assembledFlag, int targetFlag) {
    return targetFlag == (targetFlag & assembledFlag);
  }
}
