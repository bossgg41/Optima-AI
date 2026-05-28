## 2024-05-20 - Implemented empty state for trading portfolio
**Learning:** Checking for an empty list (`userStocks.isEmpty()`) and using a simple conditional in Compose to display an empty state is an effective way to improve the user experience when no data is present. I also tweaked the formatCurrency method to make the trailing .00 decimals cleaner when it ends exactly on the dot.
**Action:** Always include empty states for sections dynamically populated with user data to provide better guidance.

## 2024-05-28 - Keyboard Options for Data Entry
**Learning:** Forgetting to add `KeyboardOptions` to Jetpack Compose text fields forces users into the default full-text keyboard, which creates high friction for purely numerical inputs (like stock amounts or prices) or specific formats (like uppercase tickers).
**Action:** Always verify that inputs intended for numbers, decimals, or specifically capitalized text have the appropriate `KeyboardOptions(keyboardType = KeyboardType.Number/Decimal, capitalization = ...)` applied to streamline form completion.
