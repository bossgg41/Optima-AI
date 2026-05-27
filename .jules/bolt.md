## 2024-05-24 - Canvas DrawScope Allocations
**Learning:** Found heavy list allocations (`flatMap { listOf(...) }`) happening inside Compose `Canvas` `DrawScope` during `ComparativeForecastChart` rendering. Since `DrawScope` operations can run frequently, allocating objects here causes unnecessary GC pressure.
**Action:** Always extract calculations that don't depend on the `DrawScope` size/context to variables outside the `Canvas`, using `remember` if they are derived from state, to avoid allocations during drawing.

## 2026-05-27 - [Compose Canvas GC Thrashing via Object Allocations]
**Learning:** Instantiating objects like `Path` and `PathEffect` or capturing lambda boundaries via `::functionRef` inside a Jetpack Compose `Canvas` drawing phase triggers garbage collection thrashing on every re-draw, drastically tanking frame performance. Native Compose Drawing paths are highly sensitive.
**Action:** Always hoist `Path` objects and heavy configurations like `PathEffect` outside the `Canvas` using `remember { Path() }` and simply call `.reset()` and reuse them in the drawing loop. Avoid dynamic function references that allocate inside loops.
