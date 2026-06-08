## 2024-05-24 - Canvas DrawScope Allocations
**Learning:** Found heavy list allocations (`flatMap { listOf(...) }`) happening inside Compose `Canvas` `DrawScope` during `ComparativeForecastChart` rendering. Since `DrawScope` operations can run frequently, allocating objects here causes unnecessary GC pressure.
**Action:** Always extract calculations that don't depend on the `DrawScope` size/context to variables outside the `Canvas`, using `remember` if they are derived from state, to avoid allocations during drawing.

## 2025-06-08 - Hoisting Canvas Allocations
**Learning:** Instantiating `Path` and `PathEffect` objects within the Canvas `onDraw` block causes severe GC thrashing, leading to dropped frames in Android Compose charts.
**Action:** Use `remember { Path() }` and `remember { PathEffect... }` outside the `Canvas`, and reuse them inside `onDraw` using `.reset()` to minimize allocations per frame.
