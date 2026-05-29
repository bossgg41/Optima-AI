## 2024-05-20 - Implemented empty state for trading portfolio
**Learning:** Checking for an empty list (`userStocks.isEmpty()`) and using a simple conditional in Compose to display an empty state is an effective way to improve the user experience when no data is present. I also tweaked the formatCurrency method to make the trailing .00 decimals cleaner when it ends exactly on the dot.
**Action:** Always include empty states for sections dynamically populated with user data to provide better guidance.

## 2026-05-29 - Added KeyboardOptions for Form Fields
**Learning:** Setting appropriate `KeyboardOptions` (e.g., `KeyboardType.Number`, `KeyboardType.Decimal`, `KeyboardCapitalization.Characters`) for specific inputs significantly reduces user friction.
**Action:** Always consider the input type and apply the correct `KeyboardOptions` for `OutlinedTextField` and `TextField` components in Jetpack Compose to display the optimal software keyboard.
