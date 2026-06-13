## 2024-05-20 - Implemented empty state for trading portfolio
**Learning:** Checking for an empty list (`userStocks.isEmpty()`) and using a simple conditional in Compose to display an empty state is an effective way to improve the user experience when no data is present. I also tweaked the formatCurrency method to make the trailing .00 decimals cleaner when it ends exactly on the dot.
**Action:** Always include empty states for sections dynamically populated with user data to provide better guidance.

## 2024-05-24 - Enable Keyboard Submit Actions
**Learning:** Found that the main Chat screen required users to physically tap the 'Send' floating action button to submit messages. By adding `keyboardOptions` (ImeAction.Send) and corresponding `keyboardActions` (onSend) to the OutlinedTextField, users can now seamlessly trigger chat submissions directly from their device's software keyboard, reducing interaction friction.
**Action:** Always configure `ImeAction` and `KeyboardActions` on primary input fields, especially chat and search inputs, to support native keyboard submissions.
