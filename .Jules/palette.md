## 2024-05-20 - Implemented empty state for trading portfolio
**Learning:** Checking for an empty list (`userStocks.isEmpty()`) and using a simple conditional in Compose to display an empty state is an effective way to improve the user experience when no data is present. I also tweaked the formatCurrency method to make the trailing .00 decimals cleaner when it ends exactly on the dot.
**Action:** Always include empty states for sections dynamically populated with user data to provide better guidance.
## 2024-05-28 - Empty state & Keyboard actions for chat input
**Learning:** Users expect to be able to send chat messages directly from their software keyboard (e.g. by pressing the enter/send button). Adding `keyboardOptions` with `ImeAction.Send` and corresponding `keyboardActions` allows this behavior. In addition, chat interfaces without messages feel broken unless an empty state is explicitly provided.
**Action:** Always provide empty states (`list.isEmpty() && !isLoading`) for dynamic lists and configure `ImeAction.Send` alongside `keyboardActions` for primary text inputs meant for submission.
