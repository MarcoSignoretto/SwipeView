# SwipeView
A swipe view for Android

SwipeView behaves like every other Android view, it supports state saving (open or closed) if an id is associated with that view.

You can specify the `layout` you want to use as a `front_layout` and the one you want to use as `back_layout`.

## Images
<img src="snapshots/swipeview.gif" width="49%">

## Setup

In your module `build.gradle` include the following dependency

```groovy
implementation 'io.github.marcosignoretto:swipeview:1.1.0'
```

## Usage

### Layout

#### Swipe view with default layouts
```xml
<io.github.marcosignoretto.swipeview.SwipeView
    android:layout_width="match_parent"
    android:layout_height="wrap_content"/>
```

#### Swipe view with custom front and back layouts
You can specify the `layout` resource file that you want to use as a `front_layout` and the one you want to use as `back_layout` using the appropriate xml attributes.

```xml
<io.github.marcosignoretto.swipeview.SwipeView
    android:id="@+id/swipeView"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:back_layout="@layout/delete_back_view"
    app:front_layout="@layout/report_item_front"/>
```

#### Swipe view with custom opening size
You can specify the opening size as follow:

```xml
<io.github.marcosignoretto.swipeview.SwipeView
    android:id="@+id/swipeView"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:opening_size="100dp"/>
```

### Code
You can manipulate and register listeners for the view events.

To open the view programmatically use the function `open()`
To close the view use `close()` instead.

#### Front view click listener
You can register a listener for the click on the front view using the normal `setOnClickListener`.

The click on this view is detected only when the view is in `closed` state.
```kotlin
view1.setOnClickListener(View.OnClickListener {
    Toast.makeText(this, "Front view clicked", Toast.LENGTH_SHORT).show()
})
```

#### Back view click listener
If you want to handle a click on the back view you have to register a listener using `setOnBackViewClickListener`.

The click on this view is detected only when the view is in `open` state.
```kotlin
view1.setOnBackViewClickListener(View.OnClickListener {
    Toast.makeText(this, "Back view clicked", Toast.LENGTH_SHORT).show()
})
```

#### Swipe view listener
You can also register a `SwipeViewListener` to be notified when the view has been opened or closed.

```kotlin
view1.setOnSwipeViewListener(object : SwipeViewListener {
    override fun onOpen() {
        Toast.makeText(this@MainActivity, "View opened", Toast.LENGTH_SHORT).show()
    }

    override fun onClose() {
        Toast.makeText(this@MainActivity, "View closed", Toast.LENGTH_SHORT).show()
    }
})
```

## Future improvements

1. Support different opening directions








