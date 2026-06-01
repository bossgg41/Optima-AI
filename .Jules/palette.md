## 2024-05-20 - Implemented empty state for trading portfolio
**Learning:** Checking for an empty list (`userStocks.isEmpty()`) and using a simple conditional in Compose to display an empty state is an effective way to improve the user experience when no data is present. I also tweaked the formatCurrency method to make the trailing .00 decimals cleaner when it ends exactly on the dot.
**Action:** Always include empty states for sections dynamically populated with user data to provide better guidance.

## 2026-06-01 - Optimize Form Input using Compose KeyboardOptions
**Learning:** Default text fields present standard alphabetic keyboards, introducing unnecessary friction for users entering data like numeric shares or formatted stock tickers. Utilizing Jetpack Compose's `KeyboardOptions` effectively handles numeric (e.g., `KeyboardType.Number`, `KeyboardType.Decimal`) or capitalized (e.g., `KeyboardCapitalization.Characters`) input, ensuring quicker and less error-prone entry.
**Action:** Always apply context-aware keyboard options to `OutlinedTextField` and `TextField` components in forms.
