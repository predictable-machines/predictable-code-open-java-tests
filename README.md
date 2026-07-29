# predictable-code-open-java-tests

> [!WARNING]
> **Internal testing scaffolding. Not an example, not a demo, not documentation.**
>
> This repository is public for one reason only: some of the GitHub integrations
> we are testing (code scanning alerts, checks, pull request annotations) do not
> work on private repositories. It exists so we can exercise those integrations
> against a throwaway project.
>
> It is **not** meant to illustrate anything:
>
> - It is **not** a tutorial, sample project, or reference implementation.
> - It does **not** show how to use Predictable Code, and the commands and file
>   layouts here are not a guide to anything.
> - It does **not** represent Predictable Code's capabilities, output quality, or
>   roadmap. Nothing here should be read as a product claim.
> - The Java code is deliberately trivial and, on some branches, deliberately
>   wrong. Do **not** copy it.
> - Nothing here is supported. It may be rewritten, force-pushed, or deleted
>   without notice, and issues and pull requests are not monitored.
>
> If you are looking for information about Predictable Code, this repository is
> not it.

## What is actually in here

A small vanilla Java banking application (accounts, deposits, withdrawals,
transfers, a transaction log) with no frameworks and no dependencies. It is
small enough to run a whole toolchain over quickly, which is the only property
we care about here.

Some branches deliberately introduce behavioral bugs so that a checking tool has
something to find. Those branches say so in their own README.

## Requirements

- Java 17+

## How to run

```bash
javac src/main/java/bank/*.java
java -cp src/main/java bank.Main
```
