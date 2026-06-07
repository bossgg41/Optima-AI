## 2024-05-20 - Implemented empty state for trading portfolio
**Learning:** Checking for an empty list (`userStocks.isEmpty()`) and using a simple conditional in Compose to display an empty state is an effective way to improve the user experience when no data is present. I also tweaked the formatCurrency method to make the trailing .00 decimals cleaner when it ends exactly on the dot.
**Action:** Always include empty states for sections dynamically populated with user data to provide better guidance.

## 2024-06-07 - Add KeyboardOptions
**Learning:** Appropriately applying `KeyboardOptions` (e.g. `KeyboardType.Number`, `KeyboardType.Decimal`, `KeyboardCapitalization.Characters`, `KeyboardType.Password`) and `visualTransformation` to `OutlinedTextField` reduces user friction during data entry and secures sensitive fields (e.g., preventing plaintext exposure and predictive text caching of credentials).
**Action:** Always add keyboard options matching the expected input to text fields.
