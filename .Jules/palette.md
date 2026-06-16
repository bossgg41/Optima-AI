## 2024-05-20 - Implemented empty state for trading portfolio
**Learning:** Checking for an empty list (`userStocks.isEmpty()`) and using a simple conditional in Compose to display an empty state is an effective way to improve the user experience when no data is present. I also tweaked the formatCurrency method to make the trailing .00 decimals cleaner when it ends exactly on the dot.
**Action:** Always include empty states for sections dynamically populated with user data to provide better guidance.

## 2024-06-16 - Conversational Interface Empty States and Software Keyboard Ergonomics
**Learning:** In chat or messaging interfaces like the AI financial assistant, users may be confused when seeing a completely blank screen, and they experience friction if they have to lift their finger to tap a physical "Send" button after typing on a software keyboard.
**Action:** Always provide an empty state (e.g. "No messages yet. Start a conversation!") when the chat list is empty. Additionally, always configure `OutlinedTextField` or `TextField` inputs with `keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send)` and handle the submission logic inside `keyboardActions = KeyboardActions(onSend = { ... })` so users can seamlessly send messages directly from their software keyboard.
