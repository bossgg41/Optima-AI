## 2024-05-24 - Canvas DrawScope Allocations
**Learning:** Found heavy list allocations (`flatMap { listOf(...) }`) happening inside Compose `Canvas` `DrawScope` during `ComparativeForecastChart` rendering. Since `DrawScope` operations can run frequently, allocating objects here causes unnecessary GC pressure.
**Action:** Always extract calculations that don't depend on the `DrawScope` size/context to variables outside the `Canvas`, using `remember` if they are derived from state, to avoid allocations during drawing.

## 2024-05-25 - Canvas DrawScope Object Allocations (KFunction, Offset, Path)
**Learning:** Found that defining local functions (`fun getPointOffset`) inside Compose `Canvas` and passing them as references (`::getPointOffset`) to drawing routines causes a `KFunction` allocation on every draw frame. Similarly, creating `Path` and `PathEffect` objects, or continuously allocating wrapper objects like `Offset` inside the drawing loop causes severe GC thrashing due to the frequency of frame rendering.
**Action:** Hoist heavy objects like `Path` and `PathEffect` using `remember` outside `Canvas` and reuse them using `.reset()`. Pass primitive bounds (floats/doubles) directly to drawing functions and calculate coordinates inline instead of allocating wrapper objects like `Offset` in loops.
