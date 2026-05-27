## 2024-05-20 - Implemented empty state for trading portfolio
**Learning:** Checking for an empty list (`userStocks.isEmpty()`) and using a simple conditional in Compose to display an empty state is an effective way to improve the user experience when no data is present. I also tweaked the formatCurrency method to make the trailing .00 decimals cleaner when it ends exactly on the dot.
**Action:** Always include empty states for sections dynamically populated with user data to provide better guidance.

## 2026-05-27 - Added keyboard Send action to chat input
**Learning:** Implementing `KeyboardOptions(imeAction = ImeAction.Send)` and handling `KeyboardActions` on chat inputs is crucial to reducing friction. When users are typing a message, allowing them to send it straight from the keyboard instead of forcing a reach to a dedicated 'Send' FAB improves flow significantly.
**Action:** Always map software keyboard submit actions to the primary action for single-field forms or chat inputs.
