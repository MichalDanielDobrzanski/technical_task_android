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
- further extensions of this mechanism is to move error handling out of `onError` handler and use 

## Decisions

### Gradle
- decided to extract dedicated Gradle module to provide time related objects for consistency
- stored API_KEY inside `local.properties` file in order not to leak it into the VCS
- ideally the API_KEY should be accessed after OAuth2 authorization flow from the resource server as it is not safe to store such key in resulting APK/AAB - due to decompilation and reverse-engineering practices
- `data`, `domain` and `presentation` packaged can be moved to separate gradle modules with taking interface/implementation classes into considerations. However, this is not a strict rule as the app can me modularised feature-wise

### API problems
- API responses are missing creation time. As a solution, whenever items are received, they are having a creation date set.
- Adding a new user creates it with now timestamp
- The update interval is 5 seconds

### Time
- Using stable Java8 time API which respects date zones

### Threading
- using as small amount of threads as needed in order to avoid context switching
- switching a thread is only done when necessary
- utilizing `RxJava3CallAdapterFactory.create()` - which setups a running background threads internally

### UI
- added and tested validators for user name and email inputs
- used `ListAdapter` with payload approach to optimize for long lists and costly UI rebuilds

### Improvements
- adding `HomeViewModel` unit tests for other functionalities (public methods) - making sure that internal view state is updated
- extracting known error hierarchy and propagating errors through `onNext` calls to facilitate MVI architecture
- parsing backend errors and propagating them to the presentation layer - handled only HTTP status codes, not the responses