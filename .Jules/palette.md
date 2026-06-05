## 2024-05-20 - Implemented empty state for trading portfolio
**Learning:** Checking for an empty list (`userStocks.isEmpty()`) and using a simple conditional in Compose to display an empty state is an effective way to improve the user experience when no data is present. I also tweaked the formatCurrency method to make the trailing .00 decimals cleaner when it ends exactly on the dot.
**Action:** Always include empty states for sections dynamically populated with user data to provide better guidance.

## 2025-05-23 - Mask Password Input in Dialogs
**Learning:** Auth dialogs in Jetpack Compose applications using `OutlinedTextField` must explicitly set `visualTransformation = PasswordVisualTransformation()` and configure keyboard options for password entry to ensure security and prevent keyboard autocomplete suggestions or plaintext exposure.
**Action:** When auditing or implementing authentication forms, always verify that `PasswordVisualTransformation` and appropriate `KeyboardOptions` are applied to sensitive fields.
