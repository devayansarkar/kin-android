# <img src="../assets/kin-logo.png" height="32" alt="Kin Logo"> Base Compat Module
[![KDoc](https://img.shields.io/badge/Docs-KDoc-blue)](https://kinecosystem.github.io/kin-android/docs)

The [:base-compat](../base-compat) module is a replacement for, and fully API compatible with [kin-sdk-android](https://github.com/kinecosystem/kin-sdk-android)

This version is based on the new [:base](../base/README.md) module under the covers, which gives higher performance and reliability that you depend on.

# Installation
If you're currently making use of [kin-sdk-android](https://github.com/kinecosystem/kin-sdk-android) please upgrade your gradle files accordingly:
In the old [kin-sdk-android](https://github.com/kinecosystem/kin-sdk-android) sdk you would have had this:
 ```groovy
dependencies {
    // ...
     implementation 'com.github.kinecosystem.kin-sdk-android:kin-sdk-lib:1.0.7'
}
```
Now, replace that with the following and do a clean build:
```groovy
dependencies {
    // ...
    implementation "org.kin.sdk.android:base-compat:${versions.kin}"
}
```
Otherwise, if you're not using [kin-sdk-android](https://github.com/kinecosystem/kin-sdk-android), please checkout [:base](../base).

# Documentation
[kin-sdk-android](https://github.com/kinecosystem/kin-sdk-android) is now **Deprecated** but see the [old documentation](https://github.com/kinecosystem/kin-sdk-android/tree/master/kin-sdk) or [API reference](../docs) for more details on how to use it.
