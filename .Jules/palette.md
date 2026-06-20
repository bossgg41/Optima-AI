## 2024-05-20 - Implemented empty state for trading portfolio
**Learning:** Checking for an empty list (`userStocks.isEmpty()`) and using a simple conditional in Compose to display an empty state is an effective way to improve the user experience when no data is present. I also tweaked the formatCurrency method to make the trailing .00 decimals cleaner when it ends exactly on the dot.
**Action:** Always include empty states for sections dynamically populated with user data to provide better guidance.

## 2024-05-23 - Map Keyboard Send Action to Chat Inputs
**Learning:** Users naturally expect the "Enter" or "Send" button on their software keyboard to submit chat messages. Not supporting `ImeAction.Send` creates friction by forcing the user to tap out of the keyboard or reach for a separate UI button.
**Action:** Always configure `keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send)` and corresponding `keyboardActions` for text fields intended for messaging or quick submissions.
