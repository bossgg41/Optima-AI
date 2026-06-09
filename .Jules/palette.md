## 2024-05-20 - Implemented empty state for trading portfolio
**Learning:** Checking for an empty list (`userStocks.isEmpty()`) and using a simple conditional in Compose to display an empty state is an effective way to improve the user experience when no data is present. I also tweaked the formatCurrency method to make the trailing .00 decimals cleaner when it ends exactly on the dot.
**Action:** Always include empty states for sections dynamically populated with user data to provide better guidance.

## 2024-05-23 - Add `ImeAction.Send` and `KeyboardActions` to chat inputs
**Learning:** Adding `keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send)` to a text input combined with `keyboardActions = KeyboardActions(onSend = { /* send action */ })` significantly reduces friction by allowing users to send messages directly from their software keyboard, without having to reach out for a separate send button on screen.
**Action:** Always configure the IME options and actions for inputs where submitting the data directly from the keyboard makes sense (like a search bar or chat input) to ensure a smooth flow.
