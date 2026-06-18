## 2024-05-20 - Implemented empty state for trading portfolio
**Learning:** Checking for an empty list (`userStocks.isEmpty()`) and using a simple conditional in Compose to display an empty state is an effective way to improve the user experience when no data is present. I also tweaked the formatCurrency method to make the trailing .00 decimals cleaner when it ends exactly on the dot.
**Action:** Always include empty states for sections dynamically populated with user data to provide better guidance.
## 2024-05-23 - Chat input keyboard send action
**Learning:** For user inputs like chat or forms, relying solely on an external `FloatingActionButton` (or Send button) can break user expectations if they attempt to press the 'Enter' or 'Send' key on the software keyboard without result. Adding Compose `keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send)` and mapping `keyboardActions = KeyboardActions(onSend = { ... })` resolves this and provides a much smoother UX.
**Action:** Always configure `KeyboardOptions` and `KeyboardActions` mapping to primary submission functions when adding standalone text fields for dynamic chat or searches.
