## 2024-05-20 - Implemented empty state for trading portfolio
**Learning:** Checking for an empty list (`userStocks.isEmpty()`) and using a simple conditional in Compose to display an empty state is an effective way to improve the user experience when no data is present. I also tweaked the formatCurrency method to make the trailing .00 decimals cleaner when it ends exactly on the dot.
**Action:** Always include empty states for sections dynamically populated with user data to provide better guidance.
## 2026-06-03 - Text Input Keyboard Configuration
**Learning:** Proper `KeyboardOptions` (like capitalization and type) and `visualTransformation` heavily reduce user friction on forms (like ticker entry and password masks).
**Action:** Always verify if a Jetpack Compose `TextField` benefits from specialized keyboard types (e.g., Decimal, Password, Characters).
