## 2024-05-20 - Implemented empty state for trading portfolio
**Learning:** Checking for an empty list (`userStocks.isEmpty()`) and using a simple conditional in Compose to display an empty state is an effective way to improve the user experience when no data is present. I also tweaked the formatCurrency method to make the trailing .00 decimals cleaner when it ends exactly on the dot.
**Action:** Always include empty states for sections dynamically populated with user data to provide better guidance.

## 2026-06-05 - Added keyboard options and password masking to text fields
**Learning:** Applying appropriate `KeyboardOptions` and `visualTransformation` to text fields is a critical UX improvement that reduces friction during data entry and secures sensitive fields.
**Action:** Always verify text inputs have appropriate keyboard types and visual transformations configured.
