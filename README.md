# LLM4S - Large Language Models for Scala


<h4 align="center">
    <a href="https://github.com/llm4s/llm4s/blob/main/LICENSE">
        <img src="https://img.shields.io/badge/License-Apache%202.0-blue.svg" alt ="License">
    </a>
    <a href="https://discord.gg/4uvTPn6qww">
        <img src="https://img.shields.io/static/v1?label=Chat%20on&message=Discord&color=blue&logo=Discord&style=flat-square" alt="Discord">
    </a>
</h4>


## Overview

LLM4S provides a simple, robust, and scalable framework for building LLM applications in Scala. While most LLM work is done in Python, we believe that Scala offers a fundamentally better foundation for building reliable, maintainable AI-powered applications.

> **Note:** This is a work in progress project and is likely to change significantly over time.

## Why Scala for LLMs?

- **Type Safety**: Catch errors at compile time rather than runtime
- **Functional Programming**: Immutable data structures and pure functions for more predictable code
- **JVM Ecosystem**: Access to a vast array of libraries and tools
- **Concurrency**: Better handling of concurrent operations with Scala's actor model
- **Performance**: JVM performance with functional programming elegance

## Features

- **Containerized Workspace**: Secure execution environment for LLM-driven operations
- **Workspace Agent Interface**: Standardized protocol for file operations and command execution
- **Multi-Provider Support**: Planned support for multiple LLM providers (OpenAI, Anthropic, etc.)

## Project Structure

- **llm4s**: Main project - contains the core LLM4S framework
- **shared**: Shared code between main project and workspace runner
- **workspaceRunner**: Code that performs the requested actions on the workspace within the docker container
- **samples**: Usage examples demonstrating various features

## Getting Started

### Prerequisites

- JDK 21+
- SBT
- Docker (for containerized workspace)

### Building the Project

```bash
sbt compile
```

### Running the Examples

```bash
sbt "samples/runMain org.llm4s.samples.basic.BasicLLMCallingExample"
sbt "samples/runMain org.llm4s.samples.workspace.ContainerisedWorkspaceDemo"
```

## Roadmap

Our goal is to implement Scala equivalents of popular Python LLM frameworks:

- [ ] Single API access to multiple LLM providers (like LiteLLM)
- [ ] A comprehensive toolchain for building LLM apps (like LangChain/LangGraph)
- [ ] An agentic framework (like PydanticAI, CrewAI)
- [ ] Tokenization utilities (port of tiktoken)
- [ ] Full ReAct loop implementation
- [ ] Simple tool calling mechanism

## Tool Calling

Tool calling is a critical integration - we aim to make it as simple as possible:

### Tool Signature Generation

Using ScalaMeta to automatically generate tool definitions from Scala methods:

```scala
/** My tool does some funky things with a & b...
 * @param a The first thing
 * @param b The second thing
 */
def myTool(a: Int, b: String): ToolResponse = {
  // Implementation
}
```

ScalaMeta extracts method parameters, types, and documentation to generate OpenAI-compatible tool definitions.

### Tool Call Mapping

Mapping LLM tool call requests to actual method invocations through:
- Code generation
- Reflection-based approaches
- ScalaMeta-based parameter mapping

### Secure Execution

Tools run in a protected Docker container environment to prevent accidental system damage or data leakage.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.

