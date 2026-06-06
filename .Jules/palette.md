## 2024-05-20 - Implemented empty state for trading portfolio
**Learning:** Checking for an empty list (`userStocks.isEmpty()`) and using a simple conditional in Compose to display an empty state is an effective way to improve the user experience when no data is present. I also tweaked the formatCurrency method to make the trailing .00 decimals cleaner when it ends exactly on the dot.
**Action:** Always include empty states for sections dynamically populated with user data to provide better guidance.

## 2024-05-25 - Secure Password Input UX
**Learning:** For authentication modals requiring sensitive input, plain `OutlinedTextField` lacks necessary feedback (masking characters) and keyboard optimizations. The default behavior risks exposing secrets to shoulder surfers and triggers inappropriate software keyboard suggestions.
**Action:** Always apply `visualTransformation = PasswordVisualTransformation()` and `keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)` when prompting for passwords.
