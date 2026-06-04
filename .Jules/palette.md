## 2024-05-20 - Implemented empty state for trading portfolio
**Learning:** Checking for an empty list (`userStocks.isEmpty()`) and using a simple conditional in Compose to display an empty state is an effective way to improve the user experience when no data is present. I also tweaked the formatCurrency method to make the trailing .00 decimals cleaner when it ends exactly on the dot.
**Action:** Always include empty states for sections dynamically populated with user data to provide better guidance.

## 2024-06-04 - Jetpack Compose OutlinedTextField Password Masking
**Learning:** Hardcoded text fields used for authentication flows in Jetpack Compose should always be properly masked using `PasswordVisualTransformation()` and trigger the appropriate system keyboard by setting `KeyboardOptions(keyboardType = KeyboardType.Password)`. This prevents passwords from being shown in plain text as the user types and improves the data entry experience.
**Action:** When working on UI inputs, verify that the `keyboardOptions` and `visualTransformation` arguments match the semantic purpose of the `OutlinedTextField` (e.g. password, email, numeric). Ensure the `androidx.compose.ui.text.input.PasswordVisualTransformation` and `androidx.foundation.text.KeyboardOptions` are properly imported.
