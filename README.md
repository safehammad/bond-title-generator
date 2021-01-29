# James Bond Film Title Generator

This command line application attempts to generate a novel random James Bond film titles based loosely on the existing James Bond film titles. Here are some typical examples:

- Never Say Love Again
- Die Another Sky
- Casino Thunder
- A Time to Die

## How it works

The entire catalogue of James Bond film titles is listed in the [templates.clj](src/safehammad/templates.clj) file. The words making up each title have been tagged with their [parts of speech ](https://www.ling.upenn.edu/courses/Fall_2003/ling001/penn_treebank_pos.html) such as noun, verb and adjective etc.

The generator works as follows:

1. A title is picked from the list at random.
2. A single word is removed from that title and its part of speach noted.
3. An alternative word with the same part of speech is picked at random from the other titles to replace the removed word.

## Bonus feature

This application can generate other novel output in addition to James Bond film titles. The full list is:

- James Bond film titles.
- Star Wars film titles
- Harry Potter film/book titles
- Power Rangers Dino Charge series episode titles
- Famous English proverbs and sayings

In the case of English proverbs, a blend of proverbs is called a [malaphor](https://www.thoughtco.com/malaphor-word-play-1691298). Here are some examples:

- The grass is better on the other side.
- Actions speak louder than chickens.
- Absence makes the bird grow fonder.

## Installation

This application is written in Clojure. If you don't have Clojure installed, this [Getting Started Guide](https://clojure.org/guides/getting_started) will get you up and running quickly.

To install the application, simply check out the source from Github.

## Usage

To generate a James Bond film title:

```
$ clj -M -m safehammad.bond
```

To generate other novel output:

```
$ clj -M -m safehammad.bond [jamesbond|starwars|harrypotter|proverbs|powerrangers]
```

## Tests

The following will run the tests:

```
$ ./bin/kaocha
```

If you're hacking on this, you might want to automatically run the tests on any change:

```
$ ./bin/kaocha --watch
```

## License

Copyright © 2020-2021 Safe Hammad

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
