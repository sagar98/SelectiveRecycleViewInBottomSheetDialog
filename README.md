<h1 align="center">Selective RecyclerView in BottomSheetDialog</h1>
<p align="center">Material 3 BottomSheet with single/multi select RecyclerView, search, and simple callbacks</p>

[![github-readme-hero-image.jpg](https://i.postimg.cc/rmgk9WZm/github-readme-hero-image.jpg)](https://postimg.cc/2bqMkqDR)

## Features

- **Single or multiple selection**
- **Debounced search** and empty state
- **Select all / Clear all**
- **Interface or lambda callback APIs**

## Installation (JitPack)

Add JitPack to repositories and the dependency:

```gradle
// settings.gradle or top-level repositories
maven { url 'https://jitpack.io' }

// module build.gradle
implementation 'com.github.sagar98:SelectiveRecycleViewInBottomSheetDialog:<latest-tag>'
```

## Quick start

Define your list using `SelectionListObject`:

```kotlin
val items = arrayListOf(
  SelectionListObject(id = "1", value = "Option A"),
  SelectionListObject(id = "2", value = "Option B", isSelected = true)
)
```

### Option A: Interface callback

Implement `CustomBottomSheetDialogInterface` and show the fragment:

```kotlin
class MainActivity : AppCompatActivity(), CustomBottomSheetDialogInterface {

  override fun onCustomBottomSheetSelection(type: String) {
    val selected = items.filter { it.isSelected }
    // use selections
  }

  fun openSheet() {
    CustomBottomSheetDialogFragment.safeShow(
      supportFragmentManager,
      CustomBottomSheetDialogFragment.TAG,
      CustomBottomSheetDialogFragment(
        listenerContext = this,
        title = "Filter",
        selectionList = items,
        isMultiSelectAllowed = true,
        showSearch = true,
        searchHint = "Search options",
        showDragHandle = true
      )
    )
  }
}
```

### Option B: Lambda callback

```kotlin
CustomBottomSheetDialogLambdaFragment.safeShow(
  supportFragmentManager,
  "FilterSheet",
  CustomBottomSheetDialogLambdaFragment(
    title = "Filter",
    selectionList = items,
    isMultiSelectAllowed = true,
    showSearch = true,
    searchHint = "Search options",
    showDragHandle = true
  ) {
    val selected = items.filter { it.isSelected }
  }
)
```

## Theming

- Uses Material 3 components and inherits colors/typography from the host app.
- Customize visibility of search and drag handle via constructor flags.

## ProGuard/R8

No additional keep rules required.

## Compatibility

- minSdk 21, targetSdk 36
- Java/Kotlin 17, AndroidX

## License

Apache-2.0. See `LICENSE`.
