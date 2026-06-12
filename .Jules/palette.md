## 2024-05-20 - Implemented empty state for trading portfolio
**Learning:** Checking for an empty list (`userStocks.isEmpty()`) and using a simple conditional in Compose to display an empty state is an effective way to improve the user experience when no data is present. I also tweaked the formatCurrency method to make the trailing .00 decimals cleaner when it ends exactly on the dot.
**Action:** Always include empty states for sections dynamically populated with user data to provide better guidance.

## 2024-05-20 - Add send action to chat input
**Learning:** Providing appropriate `keyboardOptions` and `keyboardActions` for text inputs allows users to trigger actions seamlessly without having to locate and tap a separate button, greatly improving form interaction and navigation flow.
**Action:** Always configure `keyboardOptions` (e.g., `ImeAction.Send`, `ImeAction.Done`, `ImeAction.Next`) alongside `keyboardActions` to handle those specific events appropriately.
