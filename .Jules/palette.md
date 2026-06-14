## 2024-05-20 - Implemented empty state for trading portfolio
**Learning:** Checking for an empty list (`userStocks.isEmpty()`) and using a simple conditional in Compose to display an empty state is an effective way to improve the user experience when no data is present. I also tweaked the formatCurrency method to make the trailing .00 decimals cleaner when it ends exactly on the dot.
**Action:** Always include empty states for sections dynamically populated with user data to provide better guidance.

## 2024-05-23 - Chat software keyboard actions
**Learning:** Text inputs placed next to a "send" FloatingActionButton often trap users who instinctively press the software keyboard's standard enter/return key, doing nothing. Configuring `ImeAction.Send` maps the software enter key to the desired submit action, smoothing the interaction.
**Action:** Always configure `KeyboardOptions(imeAction = ImeAction.Send)` and implement the matching `KeyboardActions` logic for text inputs that act as a single-line message or search bar to enable native keyboard submission.
