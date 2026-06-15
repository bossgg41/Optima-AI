## 2024-05-20 - Implemented empty state for trading portfolio
**Learning:** Checking for an empty list (`userStocks.isEmpty()`) and using a simple conditional in Compose to display an empty state is an effective way to improve the user experience when no data is present. I also tweaked the formatCurrency method to make the trailing .00 decimals cleaner when it ends exactly on the dot.
**Action:** Always include empty states for sections dynamically populated with user data to provide better guidance.

## 2026-06-15 - Added keyboard actions and empty states to ChatScreen
**Learning:** Providing software keyboard actions (like ImeAction.Send) ensures users aren't forced to reach for a physical UI button, creating a smoother input flow. Combined with empty states, it guides the user effectively.
**Action:** Always configure keyboardOptions and keyboardActions for text inputs to trigger actions directly from the software keyboard, and provide empty states for dynamic lists.
