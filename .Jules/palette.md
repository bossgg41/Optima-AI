## 2024-05-20 - Implemented empty state for trading portfolio
**Learning:** Checking for an empty list (`userStocks.isEmpty()`) and using a simple conditional in Compose to display an empty state is an effective way to improve the user experience when no data is present. I also tweaked the formatCurrency method to make the trailing .00 decimals cleaner when it ends exactly on the dot.
**Action:** Always include empty states for sections dynamically populated with user data to provide better guidance.
## 2024-05-20 - Added ImeAction.Send to Chat Input
**Learning:** Text fields intended for immediate submission (like chat inputs) should implement `keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send)` and corresponding `keyboardActions` to allow users to seamlessly submit text directly from the software keyboard without needing to reach for a secondary button.
**Action:** Always configure `ImeAction.Send` and `keyboardActions` for primary text inputs meant to trigger a submission, improving navigation and user guidance.
