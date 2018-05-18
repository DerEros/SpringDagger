# Example for decentralized config

Makes full use of Spring class annotations to configure and wire an
application. Configuration is close to where the code is which makes
it easier to keep both in sync. Central configuration is avoided as
far as possible.

This approach fits seemlessly with other Spring projects (non-DI,
e.g. WebMVC) which use annotations to activate their features.