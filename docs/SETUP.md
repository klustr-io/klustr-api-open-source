# Setup for Development

## Requirements

* Java
* Maven
* Gitleaks
* jq
* git-filter-repo

## Principles

* Services MUST use dependency injection
* All features MUST use Provider Pattern
* Features MUST have Unit Tests

## APIs

* API MUST use rest semantics
* API MUST use credentials (OIDC)
* API MUST include metrics for latency (p95, p50, p99)
* API MUST leverage caching where appropriate

## Code Principles

* Code MUST be scanned for credentials
* Code MUST be documented for public interfaces
* Code MUST

`gitleaks detect --report-format html --report-path gitleaks-report.html`


## IntelliJ

Install the envfile so you can configure running with your .env.local file or other.
https://plugins.jetbrains.com/plugin/7861-envfile