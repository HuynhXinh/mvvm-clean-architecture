
## MVVM with clean architecture approach  
An Android project with MVVM pattern adapts which newest Android libraries: [Data binding](https://developer.android.com/topic/libraries/data-binding/), [Lifecycle-Aware](https://developer.android.com/topic/libraries/architecture/lifecycle), [LiveData](https://developer.android.com/topic/libraries/data-binding/), [LiveData](https://developer.android.com/topic/libraries/architecture/livedata), [Navigation](https://developer.android.com/guide/navigation/), [Paging](https://developer.android.com/topic/libraries/architecture/paging/), [Room](https://developer.android.com/topic/libraries/architecture/room), [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel), [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager).   
And some another: [Koin](https://insert-koin.io/) + [Scope](https://insert-koin.io/docs/2.0/getting-started/android-scope/) use for dependency injection, handle synchronization by [RxJava 2](https://github.com/ReactiveX/RxJava) + [RxAndroid](https://github.com/ReactiveX/RxAndroid),  communication with api via [Retrofit](https://github.com/square/retrofit).

I also have created rx wrapper: detect location,  google sign in, facebook login, RxErrorHandlingCallAdapterFactory,... git clone and enjoy :)

## Thanks
* [raphaelbussa](https://github.com/raphaelbussa/PermissionUtils) for very useful PermissionUtils
* [https://www.behance.net/gallery/57717777/Free-Travel-app-UI-Kit](https://www.behance.net/gallery/57717777/Free-Travel-app-UI-Kit)

## How to build?
Need to create secret-keys.gradle file (contain key of Google sign and Facebook) and add to root level project

    ext.GOOGLE_SIGN_IN_CLIENT_ID = "\"Your google sign in client id here\""
    ext.FACEBOOK_APP_ID = "\"Your facebook app id\""
    ext.FB_LOGIN_PROTOCOL_SCHEME = "\"Your fb login protocol scheme here\""

   - Google sing in id: https://developers.google.com/identity/sign-in/android/start
   - Facebook app id: https://developers.facebook.com/docs/facebook-login/android/

*If you don't want to run login function just comment out.*

## Appreciate
Many appreciate for any comments, contributors, report issue
And any star or fort are most motivation for me to update more new features

## Screenshot
![Login](/screenshot/login.png)

<div align="center">
    <img src="/screenshot/login.png" width="400px"</img> 
</div>

## License

	The MIT License (MIT)

	Copyright (c) 2019 Huynh Xinh

    Permission is hereby granted, free of charge, to any person obtaining a copy  
    of this software and associated documentation files (the "Software"), to deal  
    in the Software without restriction, including without limitation the rights  
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell  
    copies of the Software, and to permit persons to whom the Software is  
    furnished to do so, subject to the following conditions:  
  
    The above copyright notice and this permission notice shall be included in all  
    copies or substantial portions of the Software.  
  
    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR  
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,  
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE  
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER  
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,  
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  
    SOFTWARE.
