## 2024-05-24 - Canvas DrawScope Allocations
**Learning:** Found heavy list allocations (`flatMap { listOf(...) }`) happening inside Compose `Canvas` `DrawScope` during `ComparativeForecastChart` rendering. Since `DrawScope` operations can run frequently, allocating objects here causes unnecessary GC pressure.
**Action:** Always extract calculations that don't depend on the `DrawScope` size/context to variables outside the `Canvas`, using `remember` if they are derived from state, to avoid allocations during drawing.

## 2024-05-24 - Canvas GC Thrashing via Object Allocations & Local Function References
**Learning:** Instantiating `Path` and `PathEffect` inside `Canvas` or passing local function references (e.g. `::getPointOffset`) to functions called within `DrawScope` triggers `KFunction` and object allocations on every frame. This causes GC thrashing since `DrawScope` operations are executed frequently.
**Action:** Use `remember` outside `Canvas` to hoist `Path` and `PathEffect` objects and reuse them via `.reset()`. Avoid passing local function references into `DrawScope` functions; pass primitive layout values and calculate coordinates inline.
