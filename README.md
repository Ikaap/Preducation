<h1 align="center">Preducation</h1>

<p align="center">  
Preducation demonstrates modern Android development with Koin, Coroutines, Flow, ViewModel and Material Design based on MVVM architecture.
</p>

[//]: # (</br>)

![preview](https://github.com/Ikaap/Preducation/assets/88300976/f26dab56-583a-40c4-bd71-d7a9f62cc75b)

https://github.com/Ikaap/Preducation/assets/88300976/3d8acfbd-0746-418d-a176-4dd467c56c46


## Tech stack & Open-source libraries

- Minimum SDK level 24
- [Kotlin](https://kotlinlang.org/)
  based, [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/)
  for asynchronous.
    - Lifecycle: Observe Android lifecycles and handle UI states upon the lifecycle changes.
    - ViewModel: Manages UI-related data holder and lifecycle aware. Allows data to survive
      configuration changes such as screen rotations.
    - ViewBinding: Binds UI components in your layouts to data sources in your app using a
      declarative format rather than programmatically.
    - [Koin](https://github.com/InsertKoinIO/koin): for dependency injection.
- **AndroidX Libraries:** A set of Android libraries designed to make it easier and more consistent
  to develop Android apps. [AndroidX Overview](https://developer.android.com/jetpack/androidx)
- **Google Services:** Integration with various Google services, enhancing app functionality (e.g.,
  Google
  Sign-In). [Google Services Documentation](https://developers.google.com/android/guides/setup)

- Architecture
    - MVVM Architecture (View - ViewBinding - ViewModel - Model)
    - Repository Pattern
- [Material-Components](https://github.com/material-components/material-components-android):
  Material design components for building ripple animation, and CardView.
- [Glide](https://github.com/bumptech/glide), [GlidePalette](https://github.com/florent37/GlidePalette):
  Loading images from network.

- Firebase:
    - [Crashlytics]( https://firebase.google.com/docs/crashlytics): Provides real-time crash reports
      to help identify and fix issues quickly.
    - [Analytics](https://firebase.google.com/docs/analytics): Allows you to measure user
      interactions with your app.

- **UI Components:**
    - [Coil](https://github.com/coil-kt/coil): A modern image loading library for Android, providing
      features like image caching and loading.
    - [RecyclerView](https://developer.android.com/jetpack/androidx/releases/recyclerview): A UI
      component for displaying large sets of data efficiently.
    - [Navigation Component](https://developer.android.com/guide/navigation): A framework for
      navigating between different parts of an app.
    - [ImagePicker](https://github.com/dhaval2404/ImagePicker): ImagePicker is a library used in
      this project to simplify the process of picking images from the device's gallery. It provides
      an easy-to-use UI for selecting images.
    - [Shimmer](https://github.com/facebook/shimmer-android): Shimmer is a library that creates a
      shimmering effect for Android applications. It is used in this project to enhance the user
      interface with subtle, animated shimmering effects.
    - [OTP View](https://github.com/appsfeature/otp-view): OTP View is a library used for displaying
      One-Time Password (OTP) input fields. In the context of this project, it is utilized for
      handling OTP input in a user-friendly way.
    - [StyleableToast](https://github.com/muddz/StyleableToast): StyleableToast is a library for
      creating customizable toasts in Android applications. It is used in this project to display
      stylish and informative toasts, especially when dealing with OTP-related messages.
    - [RoundedProgressBar](https://github.com/MackHartley/RoundedProgressBar): RoundedProgressBar is
      a library that provides a customizable and rounded progress bar for Android applications. It
      is used in this project to visualize progress in a visually appealing manner.
    - [Groupie](https://github.com/lisawray/groupie): Groupie is a library for building complex
      RecyclerView layouts with minimal boilerplate code. In this project, it simplifies the
      implementation of RecyclerViews and adapters for displaying grouped data.
    - [Groupie ViewBinding](https://github.com/lisawray/groupie-viewbinding): Groupie ViewBinding is
      an extension for Groupie that allows seamless integration with ViewBinding. It simplifies the
      process of working with ViewBinding in RecyclerView items.

- **Networking:**
    - [Retrofit](https://github.com/square/retrofit): A type-safe HTTP client for Android and Java,
      making it easy to consume RESTful web services.
    - [OkHttp](https://github.com/square/okhttp): An efficient HTTP client for Android.

- **Testing Libraries:**
    - [MockK](https://github.com/mockk/mockk): A mocking library for Kotlin.
    - [Turbine](https://github.com/cashapp/turbine): A testing library for kotlinx.coroutines-based
      code.

- **Ktlin:**
- [Ktlin on GitHub](https://github.com/google/ksp): Ktlin is a Kotlin Symbol Processing API that
  provides a lightweight reflection library. It is used in this project for efficient processing of
  Kotlin symbols.

- **Chucker:**
- [Chucker on GitHub](https://github.com/ChuckerTeam/chucker) :Chucker is a library for inspecting
  OkHttp traffic during development. It provides a simple UI to view the details of network calls
  made by the app.

**Data Store:**

- [Data Store Documentation](https://developer.android.com/topic/libraries/architecture/datastore):
  Data Store is part of the Android Jetpack libraries and provides a modern and concise way to store
  data asynchronously. It is used for managing preferences in this project.

**Form Data (Edit Profile):**
- [Retrofit on GitHub](https://github.com/square/retrofit):This library (or feature)
  is used for handling form data, particularly in scenarios where the user is editing their profile.
  It streamlines the process of collecting and submitting user information.

## Architecture

**Preducation** is based on the MVVM architecture and the Repository pattern, which follows
the [Google's official architecture guidance](https://developer.android.com/topic/architecture).

![0](https://github.com/Ikaap/Preducation/assets/88300976/d1d30d06-01c5-46ac-b525-e86f5ee0eb0f)

The overall architecture of **Preducation** is composed of two layers; the UI layer and the data
layer. Each layer has dedicated components and they have each different responsibilities, as defined
below:

**Preducation** was built
with [Guide to app architecture](https://developer.android.com/topic/architecture), so it would be a
great sample to show how the architecture works in real-world projects.

### Architecture Overview

![1](https://github.com/Ikaap/Preducation/assets/88300976/87f0c41d-0c12-4584-b866-ea6cf7fd2bc0)

- Each layer
  follows [unidirectional event/data flow](https://developer.android.com/topic/architecture/ui-layer#udf);
  the UI layer emits user events to the data layer, and the data layer exposes data as a stream to
  other layers.
- The data layer is designed to work independently from other layers and must be pure, which means
  it doesn't have any dependencies on the other layers.

With this loosely coupled architecture, you can increase the reusability of components and
scalability of your app.

### UI Layer

![2](https://github.com/Ikaap/Preducation/assets/88300976/93eedd9b-6a9a-4695-8b70-1ce6ad989c1e)

The UI layer consists of UI elements to configure screens that could interact with users
and [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) that holds app
states and restores data when configuration changes.

- UI elements observe the data flow
  via [ViewBinding](https://developer.android.com/topic/libraries/view-binding?hl=id), which is the most
  essential part of the MVVM architecture.

### Data Layer

![3](https://github.com/Ikaap/Preducation/assets/88300976/0b0f2abd-2df1-476e-b762-4957d05f0048)

The data Layer consists of repositories, which include business logic, such as querying data from
the data source and requesting remote data from the network.

