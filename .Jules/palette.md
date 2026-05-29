## 2024-05-20 - Implemented empty state for trading portfolio
**Learning:** Checking for an empty list (`userStocks.isEmpty()`) and using a simple conditional in Compose to display an empty state is an effective way to improve the user experience when no data is present. I also tweaked the formatCurrency method to make the trailing .00 decimals cleaner when it ends exactly on the dot.
**Action:** Always include empty states for sections dynamically populated with user data to provide better guidance.

## 2024-05-29 - Configured KeyboardOptions for data entry forms
**Learning:** In Compose, applying appropriate `KeyboardOptions` (e.g. `KeyboardType.Decimal`, `KeyboardCapitalization.Characters`) drastically improves data entry UX on mobile, preventing the user from needing to manually switch software keyboards.
**Action:** Always verify `OutlinedTextField` and `TextField` components have proper `keyboardOptions` set matching the required data type.
