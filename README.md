# Advent of Code 2025 🎄

Solutions to [Advent of Code 2025](https://adventofcode.com/2025) puzzles written in Scala 3.

## About

Advent of Code is an annual December event featuring daily programming puzzles from December 1st through 25th. This repository contains my solutions implemented in Scala 3, following functional programming principles.

## Technologies

- **Language**: Scala 3.7.4
- **Build Tool**: sbt 1.11.7
- **Testing**: ScalaTest 3.2.19
- **Mocking**: Mockito 5.12

## Project Structure

```
advent-of-code/
├── src/
│   ├── main/
│   │   ├── scala/
│   │   │   ├── adventofcode/
│   │   │   │   ├── dayone/          # Day 1: Secret Entrance
│   │   │   │   │   ├── model/       # Dial, Turn, Position, Size
│   │   │   │   │   ├── input/       # TurnRowParser, TurnParser
│   │   │   │   │   ├── DialZeroCounter.scala
│   │   │   │   │   ├── SecretEntrancePart1.scala
│   │   │   │   │   └── SecretEntrancePart2.scala
│   │   │   │   ├── daytwo/          # Day 2: Gift Shop
│   │   │   │   │   ├── model/       # Range, SillyIdFinder
│   │   │   │   │   ├── input/       # RangeParser
│   │   │   │   │   ├── GiftShopPart1.scala
│   │   │   │   │   └── GiftShopPart2.scala
│   │   │   │   └── util/            # Shared utilities
│   │   │   │       ├── FileParser.scala
│   │   │   │       └── RowParser.scala
│   │   └── resources/
│   │       ├── dayone/          # Day 1 input files
│   │       └── daytwo/          # Day 2 input files
│   └── test/
│       └── scala/
│           └── adventofcode/
│               ├── dayone/          # Day 1 tests (62 tests)
│               │   ├── model/
│               │   │   ├── DialSpec.scala
│               │   │   └── DialZeroCounterSpec.scala
│               │   ├── SecretEntrancePart1Spec.scala
│               │   └── SecretEntrancePart2Spec.scala
│               └── daytwo/          # Day 2 tests (47 tests)
│                   ├── model/
│                   │   └── SillyIdFinderSpec.scala
│                   ├── GiftShopPart1Spec.scala
│                   └── GiftShopPart2Spec.scala
├── build.sbt                    # Build configuration
└── README.md
```

## Getting Started

### Prerequisites

- Java 11 or higher
- sbt (Scala Build Tool)

### Running Solutions

To run a specific day's solution:

```bash
# Day 1: Secret Entrance
sbt "runMain adventofcode.dayone.SecretEntrancePart1"
sbt "runMain adventofcode.dayone.SecretEntrancePart2"

# Day 2: Gift Shop
sbt "runMain adventofcode.daytwo.GiftShopPart1"
sbt "runMain adventofcode.daytwo.GiftShopPart2"
```

### Running Tests

To run all tests:

```bash
sbt test
```

To run tests for a specific package:

```bash
sbt "testOnly adventofcode.dayone.*"
sbt "testOnly adventofcode.daytwo.*"
```

To run tests for a specific test class:

```bash
sbt "testOnly *DialSpec"
sbt "testOnly *DialZeroCounterSpec"
sbt "testOnly *SecretEntrancePart1Spec"
sbt "testOnly *SecretEntrancePart2Spec"
sbt "testOnly *SillyIdFinderSpec"
sbt "testOnly *GiftShopPart1Spec"
sbt "testOnly *GiftShopPart2Spec"
```

## Solutions

### Day 1: Secret Entrance
The first puzzle involves a combination lock dial that rotates based on a series of left and right turns.

- **Part 1**: Calculate the final position of the dial after processing all turns from the starting position (50). Counts how many times the dial lands on position 0.
  - Input: Series of left (L) and right (R) turns with values (e.g., L68, L30, R48)
  - Output: Final dial position and zero landing count
  - Result: Final position 32, landed on zero 3 times

- **Part 2**: Enhanced solution that counts zero crossings during each individual turn, not just when landing on zero.
  - Uses `DialZeroCounter` to track all zero passages during rotation
  - Handles both wrapping scenarios (left and right turns)
  - Result: Final position 32, crossed through zero 6 times

**Key Components**:
- `Dial`: Represents a rotational dial with position and size
- `Turn`: ADT for left/right turns with values
- `DialZeroCounter`: Counts zero crossings during turns
- `TurnParser`: Parses input file into Turn sequences

### Day 2: Gift Shop
The second puzzle involves finding "silly" product IDs within given ranges.

- **Part 1**: Find all silly IDs in multiple ranges and sum them up.
  - A "silly" ID has an even number of digits where the first half equals the second half
  - Examples: 11, 22, 1010, 222222, 1188511885
  - Uses `BigDecimal` for handling large numbers
  - Input: File with ranges in format "start-end,start-end,..."
  - Result: Sum of all silly IDs = **1,227,775,554**

- **Part 2**: Enhanced algorithm that also finds palindromic numbers and numbers with repeating patterns.
  - Includes additional silly ID patterns beyond simple half-matching
  - Finds more silly IDs in the same ranges
  - Result: Sum of all silly IDs = **4,174,379,265**

**Key Components**:
- `SillyIdFinder`: Identifies silly IDs using digit comparison algorithms
  - `findSillyIdsPart1`: Finds IDs where first half equals second half
  - `findSillyIdsPart2`: Enhanced pattern matching for more silly ID types
- `Range`: Represents numeric ranges with head and tail
- `RangeParser`: Parses input file into Range sequences

**Silly ID Algorithm (Part 1)**:
1. Check if the number has an even number of digits
2. Split the digits in half
3. Compare if both halves are identical
4. Examples:
   - 1221: "12" ≠ "21" → Not silly
   - 1010: "10" = "10" → Silly!
   - 38593859: "3859" = "3859" → Silly!

**Part 2 Algorithm**:
- Extends Part 1 logic with additional pattern detection
- Identifies more complex silly ID patterns
- Examples of additional patterns found: 999, 111, 565656

*(More solutions will be added as the event progresses)*

## License

This project is licensed under the terms specified in the LICENSE file.

## Acknowledgments

- [Advent of Code](https://adventofcode.com/) by Eric Wastl
- Scala community for excellent documentation and libraries

