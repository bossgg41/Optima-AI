## 2024-05-20 - Implemented empty state for trading portfolio
**Learning:** Checking for an empty list (`userStocks.isEmpty()`) and using a simple conditional in Compose to display an empty state is an effective way to improve the user experience when no data is present. I also tweaked the formatCurrency method to make the trailing .00 decimals cleaner when it ends exactly on the dot.
**Action:** Always include empty states for sections dynamically populated with user data to provide better guidance.
## 2025-02-21 - Added empty state and keyboard actions to ChatScreen
**Learning:** Providing an empty state in a chat interface improves initial user engagement, and adding keyboard action (ImeAction.Send) significantly improves message entry fluidity on mobile.
**Action:** Always include empty states for lists and ensure text fields have appropriate keyboard actions.
