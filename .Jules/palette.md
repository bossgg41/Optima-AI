## 2024-05-20 - Implemented empty state for trading portfolio
**Learning:** Checking for an empty list (`userStocks.isEmpty()`) and using a simple conditional in Compose to display an empty state is an effective way to improve the user experience when no data is present. I also tweaked the formatCurrency method to make the trailing .00 decimals cleaner when it ends exactly on the dot.
**Action:** Always include empty states for sections dynamically populated with user data to provide better guidance.

## 2024-05-24 - Added software keyboard send action to chat inputs
**Learning:** Users often expect to trigger actions directly from their software keyboards, especially in chat interfaces. Relying solely on a physical UI button forces an awkward reach.
**Action:** Always configure `keyboardOptions` (e.g., `ImeAction.Send`) alongside `keyboardActions` (e.g., `onSend`) for text inputs to align with natural navigation expectations and improve usability.
