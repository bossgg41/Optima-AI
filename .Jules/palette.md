## 2024-05-20 - Implemented empty state for trading portfolio
**Learning:** Checking for an empty list (`userStocks.isEmpty()`) and using a simple conditional in Compose to display an empty state is an effective way to improve the user experience when no data is present. I also tweaked the formatCurrency method to make the trailing .00 decimals cleaner when it ends exactly on the dot.
**Action:** Always include empty states for sections dynamically populated with user data to provide better guidance.

## 2024-05-24 - Enhance keyboard input types for accessibility and UX
**Learning:** Explicitly declaring `KeyboardOptions` and `VisualTransformation` on Android Compose TextFields reduces friction during data entry and secures sensitive fields like passwords.
**Action:** Always apply the correct `KeyboardType` (Number, Decimal, Password, etc.) to user inputs.
