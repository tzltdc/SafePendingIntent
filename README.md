A different unconsolidated approach on addressing the PendingIntent flag issue
===================================


Rules on SafePendingIntent:

If the `Option.DEFAULT` is provided:
If running device is lower than 23, no changes at all.
If running device is between 23(inclusive) and 30(inclusive), no changes at all.
If running device is greater or equal 31, appending the IMMUTABLE. To avoid crash

If the `Option.IMMUTABLE` is provided:
If running device is lower than 23, no changes at all.
If running device is between 23(inclusive) and 30(inclusive), appending the IMMUTABLE.
If running device is greater or equal 31, appending the IMMUTABLE.
if raw flags contains MUTABLE, throw exception

If the `Option.MUTABLE` is provided, we honor the options in Android system.
If running device is lower than 23, no changes at all.
If running device is between 23(inclusive) and 30(inclusive), no changes at all.
If running device is greater or equal 31, appending the MUTABLE.
if raw flags contains IMMUTABLE, throw exception


To migrate the existing API usage and allow target Android 12, we just use `SafePendingIntent#getXXX` with the DEFAULT options.
This way, it allows us to simply find and replace the API call. And it will guarantee that our monorepo will not throw exception when we are to target Android 12.
For device running below Android 12, no new side effects is introduced.
For device running on Android 12, we added the IMMUTABLE flag as the default recommended one, which is also good.
So we could address the migration easily.


To support future use cases or detailed use case, we add the parameter per the requirement when we are calling `SafePendingIntent#getXXX`.
Better yet, For the API consumer, they do NOT have to check the API version to specify the flags. They could use  `Option.DEFAULT` ,  `Option.IMMUTABLE` , `Option.MUTABLE` at their wish.