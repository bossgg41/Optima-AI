## 2024-05-20 - Implemented empty state for trading portfolio
**Learning:** Checking for an empty list (`userStocks.isEmpty()`) and using a simple conditional in Compose to display an empty state is an effective way to improve the user experience when no data is present. I also tweaked the formatCurrency method to make the trailing .00 decimals cleaner when it ends exactly on the dot.
**Action:** Always include empty states for sections dynamically populated with user data to provide better guidance.
## 2024-05-24 - Improve chat input keyboard navigation and empty state
**Learning:** Implementing `keyboardOptions` and `keyboardActions` on text inputs drastically improves usability by allowing users to submit forms or messages directly from the software keyboard. Combined with explicit empty states, this provides clear guidance and immediate feedback to the user when no content is present.
**Action:** Always configure `ImeAction.Send` or `ImeAction.Done` along with a corresponding action handler for text inputs in Compose, and provide empty states for dynamic lists.
