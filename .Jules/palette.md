## 2024-05-20 - Implemented empty state for trading portfolio
**Learning:** Checking for an empty list (`userStocks.isEmpty()`) and using a simple conditional in Compose to display an empty state is an effective way to improve the user experience when no data is present. I also tweaked the formatCurrency method to make the trailing .00 decimals cleaner when it ends exactly on the dot.
**Action:** Always include empty states for sections dynamically populated with user data to provide better guidance.

## 2024-05-20 - Adding explicit KeyboardOptions for Text Inputs in Compose
**Learning:** Adding the appropriate `KeyboardOptions` (such as `KeyboardType.Password`, `KeyboardType.Number`, `KeyboardType.Decimal`, or `KeyboardCapitalization.Characters`) significantly improves mobile input UX and data security. The password visualization also demands applying a `PasswordVisualTransformation()`. Failing to add explicit parameters relies on the default keyboard layout which increases typing friction or causes data-type validation issues for numeric/special fields.
**Action:** Always provide contextual `KeyboardOptions` configuration to `OutlinedTextField` and `TextField` implementations.
