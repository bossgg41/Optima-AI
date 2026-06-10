## 2024-05-20 - Implemented empty state for trading portfolio
**Learning:** Checking for an empty list (`userStocks.isEmpty()`) and using a simple conditional in Compose to display an empty state is an effective way to improve the user experience when no data is present. I also tweaked the formatCurrency method to make the trailing .00 decimals cleaner when it ends exactly on the dot.
**Action:** Always include empty states for sections dynamically populated with user data to provide better guidance.

## 2024-05-23 - Add Send action to software keyboard for Chat Input
**Learning:** Adding explicit keyboard actions like `ImeAction.Send` and binding them to the submission logic improves usability drastically. When testing the chat functionality on mobile, the text input feels detached if users have to collapse the keyboard to tap a floating action button.
**Action:** Always configure appropriate `keyboardOptions` and `keyboardActions` for text inputs that act as direct messaging or search fields.
