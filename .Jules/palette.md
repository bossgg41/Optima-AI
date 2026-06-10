## 2024-05-20 - Implemented empty state for trading portfolio
**Learning:** Checking for an empty list (`userStocks.isEmpty()`) and using a simple conditional in Compose to display an empty state is an effective way to improve the user experience when no data is present. I also tweaked the formatCurrency method to make the trailing .00 decimals cleaner when it ends exactly on the dot.
**Action:** Always include empty states for sections dynamically populated with user data to provide better guidance.

## 2024-06-10 - Enhancing Software Keyboard Interactions in Chat Forms
**Learning:** In Compose chat applications, relying solely on an on-screen "Send" FloatingActionButton forces users to manually dismiss the soft keyboard or reach across the screen to send a message, introducing friction. By configuring `keyboardOptions` with `ImeAction.Send` alongside `keyboardActions(onSend = { ... })` on text inputs, users can submit data directly from the software keyboard, creating a more seamless and standard mobile chat experience.
**Action:** Always map `ImeAction` correctly to the intent of the form and handle the submission inside `KeyboardActions` for standard inputs, especially in single-input chat/messaging screens.
