## 2024-05-20 - Implemented empty state for trading portfolio
**Learning:** Checking for an empty list (`userStocks.isEmpty()`) and using a simple conditional in Compose to display an empty state is an effective way to improve the user experience when no data is present. I also tweaked the formatCurrency method to make the trailing .00 decimals cleaner when it ends exactly on the dot.
**Action:** Always include empty states for sections dynamically populated with user data to provide better guidance.

## 2024-05-28 - Added keyboard action for Chat input
**Learning:** Users naturally expect to submit chat messages using the software keyboard's primary action button. Omitting `keyboardOptions` and `keyboardActions` on Compose `OutlinedTextField`s forces an awkward interaction where they must reach for a separate on-screen Send button.
**Action:** Always configure `KeyboardOptions(imeAction = ImeAction.Send)` and corresponding `keyboardActions` for chat or search inputs to match platform expectations.
