## 2026-05-30 - Optimize Form Entry with Jetpack Compose KeyboardOptions
**Learning:** Using the correct `KeyboardOptions` (like `KeyboardType.Number`, `KeyboardType.Decimal`, and `KeyboardCapitalization`) in `OutlinedTextField` inputs dramatically reduces user friction during data entry by displaying the appropriate native software keyboard layout automatically.
**Action:** Always map the expected data type of a form field to its corresponding Jetpack Compose `KeyboardOptions` to optimize the input experience.

## 2024-05-20 - Implemented empty state for trading portfolio
**Learning:** Checking for an empty list (`userStocks.isEmpty()`) and using a simple conditional in Compose to display an empty state is an effective way to improve the user experience when no data is present. I also tweaked the formatCurrency method to make the trailing .00 decimals cleaner when it ends exactly on the dot.
**Action:** Always include empty states for sections dynamically populated with user data to provide better guidance.
