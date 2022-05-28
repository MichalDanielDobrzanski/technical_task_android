# Implementation

## Architecture

- 3 layer architecture - data, domain and presentation
- clean code
- `UseCase` and `Repotorory` pattens
- networking - `Retrofit`, `Moshi`
- async - `Rxjava3`
- dependency injection - `Hilt`
- included separation of concerns, encapsulation, testability, SOLID principles

### Error handling

- extracted error handling logic to a single place - `RetrofitResponseMapperImpl`
- used known class for HTTP errors -`HttpApiException`
- the errors are propagated downstream and are caught in `onError` handler.
- further extensions of this mechanism is to move error handling out of `onError` handler and
  use `Respose` - `Success`/`Failure` sealed class for `onNext` calls. The reactive framework
  provides downstream error propagation. So if there would be some exceptions (for instance in the
  data-domain) mapper logic, the end user still sees the error and the app does not crash. An
  improvement to this will be to use `.onErrorReturnItem()` as the last operator downstream,
  before `.subscribe` and thread manipulating operators.

## Decisions

### Gradle

- decided to extract dedicated Gradle module to provide time related objects for consistency and
  encapsulating the underlying implementation
- stored API_KEY inside `local.properties` file in order not to leak it into the VCS
- ideally the API_KEY should be accessed after OAuth2 authorization flow from the resource server as
  it is not safe to store such key in resulting APK/AAB - due to decompilation and
  reverse-engineering practices it can be extracted
- `data`, `domain` and `presentation` packaged can be moved to separate gradle modules with taking
  interface/implementation classes into considerations. However, this is not a strict rule as the
  app can me modularised feature-wise
- maintaining proper library versioning and sharing the config (like minSdk/targetSdk) between
  Gradle modules requires creating root (Gradle project wise) script with is applied to every Gradle
  module providing consistent setup. Gradle tends to use the later library version in the module
  when is it declaring the old version and some other module uses the later one.

### API problems

- API responses are missing creation time. As a solution, whenever items are received, they are
  having a creation date set (just for the app session). Offline caching was not assumed, but could
  be added with the help of `Room` library making it a single source of truth.
- Adding a new user creates it with current time timestamp.
- Added refreshing functionality for the list. The update interval is 5 seconds.

### Time

- using stable Java8 time API which respects time zones.

### Threading

- using as small amount of threads as needed in order to avoid context switching
- switching a thread is only done when necessary
- utilizing `RxJava3CallAdapterFactory.create()` - which setups a running background threads
  internally

### UI

- added and tested validators for user name and email inputs
- used `ListAdapter` with payload approach to optimize for long lists and costly UI rebuilds

### Improvements

- adding `HomeViewModel` unit tests for other functionalities (public methods) - making sure that
  internal view state is updated especially for remove user feature and refresh user list featute. 
- extracting known error hierarchy and propagating errors through `onNext` calls to facilitate MVI
  architecture
- parsing backend errors and propagating them to the presentation layer. Only HTTP status
  codes have been handled, not the response bodies