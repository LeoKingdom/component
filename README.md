# component
some commen components and tools,and other 3d libs
这个库的目标是:实现一个集成日常开发常用的view和viewgroup,
实现自定义的过程中使用到了一些其他的第三方库,因此在使用本lib的时候注意一下,
避免重复加载,从而出现不必要问题:使用了的第三方库有如下:
     //gson解析
    implementation 'com.google.code.gson:gson:2.8.5'
    //glide图片加载
    api 'com.github.bumptech.glide:glide:4.9.0'
    //retrofit网络框架
    api 'com.squareup.retrofit2:retrofit:2.5.0'
    //retrofit数据转换
    api 'com.squareup.retrofit2:converter-gson:2.4.0'
    //rx适配器
    api 'com.squareup.retrofit2:adapter-rxjava2:2.3.0'
    //rxjava
    api 'io.reactivex.rxjava2:rxjava:2.1.6'
    //rxandroid
    api 'io.reactivex.rxjava2:rxandroid:2.0.1'
    api 'com.squareup.okhttp3:logging-interceptor:3.8.0'
    //eventbus
    api 'org.greenrobot:eventbus:3.1.1'
    api 'com.android.support:recyclerview-v7:28.0.0'
    //
    api 'com.github.bumptech.glide:okhttp3-integration:4.0.0-RC1'
    
    系统库:
    api "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
    implementation 'com.android.support:appcompat-v7:28.0.0'
    testImplementation 'junit:junit:4.12'
    androidTestImplementation 'com.android.support.test:runner:1.0.2'
    androidTestImplementation 'com.android.support.test.espresso:espresso-core:3.0.2'
    
    本库也提供了dp的屏幕适配的values
