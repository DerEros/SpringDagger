# Example for centralized config

Instead of spreading annotations all over the code base and making 
it virtually impossible to eventually move to a different IoC
container, we try to keep as much Spring-related config as possible
in a few encapsulated classes.

While this approach generally works well with the DI features of
Spring, it is not really feasible (or even impossible) to implement
it with other libraries from the wide Spring ecosystem (e.g. 
WebMVC). So even when following the centralized config paradigm the
codebase will not be completely clean.