## 2024-05-20 - Implemented empty state for trading portfolio
**Learning:** Checking for an empty list (`userStocks.isEmpty()`) and using a simple conditional in Compose to display an empty state is an effective way to improve the user experience when no data is present. I also tweaked the formatCurrency method to make the trailing .00 decimals cleaner when it ends exactly on the dot.
**Action:** Always include empty states for sections dynamically populated with user data to provide better guidance.

## 2024-05-28 - Implemented keyboard actions for chat input
**Learning:** Adding `keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send)` and corresponding `keyboardActions` allows users to trigger chat submissions directly from their device's software keyboard, reducing friction and enhancing the overall messaging experience.
**Action:** Always configure appropriate `imeAction` and `keyboardActions` for text fields that act as primary triggers (e.g. search boxes, chat inputs, submit forms) to improve accessibility and user flow.
