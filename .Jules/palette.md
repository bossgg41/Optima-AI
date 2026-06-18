## 2024-05-20 - Implemented empty state for trading portfolio
**Learning:** Checking for an empty list (`userStocks.isEmpty()`) and using a simple conditional in Compose to display an empty state is an effective way to improve the user experience when no data is present. I also tweaked the formatCurrency method to make the trailing .00 decimals cleaner when it ends exactly on the dot.
**Action:** Always include empty states for sections dynamically populated with user data to provide better guidance.

## 2024-06-18 - Added software keyboard action for Chat input
**Learning:** Users naturally expect to be able to send chat messages directly from their software keyboard using the "Send" action, rather than having to reach for a physical button on the screen. By configuring `keyboardOptions` with `ImeAction.Send` and `keyboardActions`, we reduce user friction during data entry and align with standard chat UI expectations.
**Action:** Always configure appropriate `keyboardOptions` and `keyboardActions` for text inputs that trigger a specific action, allowing users to submit directly from the software keyboard.
