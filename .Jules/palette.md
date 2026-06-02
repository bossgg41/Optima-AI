## 2024-05-20 - Implemented empty state for trading portfolio
**Learning:** Checking for an empty list (`userStocks.isEmpty()`) and using a simple conditional in Compose to display an empty state is an effective way to improve the user experience when no data is present. I also tweaked the formatCurrency method to make the trailing .00 decimals cleaner when it ends exactly on the dot.
**Action:** Always include empty states for sections dynamically populated with user data to provide better guidance.

## 2024-05-21 - Added KeyboardOptions to AddAssetDialog inputs
**Learning:** By providing `KeyboardOptions` (like `KeyboardType.Decimal` for numbers and `KeyboardCapitalization.Characters` for stock tickers) to Compose `OutlinedTextField`s, we ensure that the OS opens the most appropriate virtual keyboard. This reduces user friction dramatically compared to the default text keyboard.
**Action:** Always configure the `keyboardOptions` property on all text input fields to match the expected data type, especially in data entry dialogs.
