## 2024-05-20 - Implemented empty state for trading portfolio
**Learning:** Checking for an empty list (`userStocks.isEmpty()`) and using a simple conditional in Compose to display an empty state is an effective way to improve the user experience when no data is present. I also tweaked the formatCurrency method to make the trailing .00 decimals cleaner when it ends exactly on the dot.
**Action:** Always include empty states for sections dynamically populated with user data to provide better guidance.

## 2024-05-24 - Implemented keyboard actions for ChatScreen
**Learning:** Users naturally expect to press the "Send" or "Enter" key on their software keyboard to submit chat messages. Forgetting to configure `keyboardOptions` with `ImeAction.Send` and `keyboardActions` with an `onSend` callback creates unnecessary friction, forcing them to manually tap the send button.
**Action:** Always configure appropriate `keyboardOptions` and `keyboardActions` for text inputs to allow users to trigger actions directly from the software keyboard, matching physical button functionality.
