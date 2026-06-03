## 2024-05-20 - Implemented empty state for trading portfolio
**Learning:** Checking for an empty list (`userStocks.isEmpty()`) and using a simple conditional in Compose to display an empty state is an effective way to improve the user experience when no data is present. I also tweaked the formatCurrency method to make the trailing .00 decimals cleaner when it ends exactly on the dot.
**Action:** Always include empty states for sections dynamically populated with user data to provide better guidance.

## 2024-05-23 - Add keyboard configuration to authentication input
**Learning:** Proper application of `PasswordVisualTransformation()` and `KeyboardType.Password` to sensitive form fields (like role upgrade passwords) drastically improves UX and accessibility. It prevents shoulder surfing and ensures the system keyboard displays appropriate entry layouts without auto-correct disrupting input.
**Action:** Always apply explicit `keyboardOptions` and `visualTransformation` configs to input fields that expect specialized formatting or handle sensitive tokens.
