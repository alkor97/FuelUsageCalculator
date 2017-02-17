# FuelUsageCalculator

Simple project to teach myself how to write applications for Android.

From the functional perspective this application computes car fuel usage over time. Following features are supported:
- add fuel usage entry (consisting of date, odometer value, fuel volume)
- edit fuel usage entry
- delete fuel usage entry
- compute fuel usage based on entries, updated after every data manipulation
- store fuel usage entries locally in device
- fetch fuel usage entries from internet
- application is localized in English (default) and Polish

From technical perspective:
- every fuel usage entry is presented as a card view stored in adapter view
- it is possible to edit fuel entry by performing long press on selected one
- it is possible to remove fuel entry by swiping it and then confirming operation
- fuel entry's field editing is performed in separate dialog
- fuel usage entries are saved in shared preferences
- fuel usage entries are read from internet location and complemented by entries from shared preferences

Things to do:
- provide support for different volume, distance, fuel usage units
- by default correct units should be selected based on locale
- push stored entries to external location
- add UI tests
