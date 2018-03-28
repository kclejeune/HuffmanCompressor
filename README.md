# HuffmanEncoder
EECS 233 (Data Structures) Project - 
Compress .txt files using Huffman Compression 
## Background
Huffman coding uses a specific method for choosing the representation for each symbol, resulting in a prefix code (sometimes called "prefix-free codes", that is, the bit string representing some particular symbol is never a prefix of the bit string representing any other symbol). Huffman coding is such a widespread method for creating prefix codes that the term "Huffman code" is widely used as a synonym for "prefix code" even when such a code is not produced by Huffman's algorithm.
## Getting Started
In its present form, the project will create an encoded file based on an input file and encoding file.  
To run it, use:

java HuffmanCompressor inputFile encodingFile outputFile

Note: In general, the input file and encoding file will be the same.
