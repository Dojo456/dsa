I used association arrays to implement a Markov Text Generator. I ended up not using Kotlin's implementation of LinkedHashMap as opposed to my own HashMap as it contained a lot more utility.

In the end, I used this Markov analyzer and built a model based on the first three Dune books. If you run <code>Main.kt</code>, you should see the output of this. It simply generates 5 sentences based on those books and prints them out.

The results are pretty funny. I think my model doesn't do a great job of handling punctuation and line breaks and such, so you get some weird output. Overall though, it's definitely in the style of Frank Herbert and incorporates characters and items from the books. Pretty cool!