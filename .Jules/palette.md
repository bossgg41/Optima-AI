## 2024-05-20 - Implemented empty state for trading portfolio
**Learning:** Checking for an empty list (`userStocks.isEmpty()`) and using a simple conditional in Compose to display an empty state is an effective way to improve the user experience when no data is present. I also tweaked the formatCurrency method to make the trailing .00 decimals cleaner when it ends exactly on the dot.
**Action:** Always include empty states for sections dynamically populated with user data to provide better guidance.

## 2024-10-24 - Added software keyboard "Send" action to chat input
**Learning:** By default, text fields don't map the software keyboard's "Enter" key to a submit action. For chat interfaces, users expect to be able to send messages directly from the keyboard without needing to reach for an on-screen "Send" button.
**Action:** Always configure `keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send)` and `keyboardActions` for text inputs where the primary user intent is to submit the entered text immediately (like search or chat).
