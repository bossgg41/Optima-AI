## 2024-05-20 - Implemented empty state for trading portfolio
**Learning:** Checking for an empty list (`userStocks.isEmpty()`) and using a simple conditional in Compose to display an empty state is an effective way to improve the user experience when no data is present. I also tweaked the formatCurrency method to make the trailing .00 decimals cleaner when it ends exactly on the dot.
**Action:** Always include empty states for sections dynamically populated with user data to provide better guidance.

## 2024-05-21 - Added KeyboardOptions for better UX
**Learning:** Adding specific `KeyboardOptions` (like `keyboardType = KeyboardType.Number` or `keyboardType = KeyboardType.Decimal`) to `OutlinedTextField` and `TextField` dramatically improves data entry friction. Furthermore, adding `KeyboardOptions(keyboardType = KeyboardType.Password)` and `visualTransformation = PasswordVisualTransformation()` masks passwords during login or auth steps, making the UX much more secure and professional.
**Action:** Always verify `KeyboardOptions` and `visualTransformation` for all newly added text inputs to ensure the keyboard type context matches the expected user input format.
