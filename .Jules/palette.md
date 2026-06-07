## 2024-05-20 - Implemented empty state for trading portfolio
**Learning:** Checking for an empty list (`userStocks.isEmpty()`) and using a simple conditional in Compose to display an empty state is an effective way to improve the user experience when no data is present. I also tweaked the formatCurrency method to make the trailing .00 decimals cleaner when it ends exactly on the dot.
**Action:** Always include empty states for sections dynamically populated with user data to provide better guidance.

## 2024-05-23 - Add specific KeyboardOptions to Compose TextFields
**Learning:** Adding specific `KeyboardOptions` (e.g. `KeyboardType.Number`, `KeyboardType.Decimal`, `KeyboardCapitalization.Characters`) and `visualTransformation` (like `PasswordVisualTransformation`) greatly reduces user friction when inputting domain-specific data such as stock tickers or sensitive items like passwords.
**Action:** Always map appropriate `KeyboardOptions` to input fields that expect specialized formatting (numbers, decimals, all caps, passwords) across Jetpack Compose apps.
