# File Compression Huffman Code
  - Implementation of Huffman Algorithm to compress arbitrary files with storing the representation of the codewords in the compressed file,
    so that you can decompress the file back.
  - we also store number of compressed words to help in decompression file back.
  - It has the capability of considering more than one byte. For example, instead of just collecting the frequencies and finding codewords for single bytes.
    The same can be done assuming the basic unit is n bytes, where n is an integer.
    
## How to run Jar:
  - single runnable jar is used for both compression and decompression.
  - To use jar for compressing an input file, the following will be called:
        java -jar HuffmanRunner.jar c absolute_path_to_input_file n
    where c means compressing the file and n is the number of bytes that will be considered together.
  - To use it for decompressing an input file, the following be called: 
        java -jar HuffmanRunner.jar d absolute_path_to_input_file
    where d means decompressing the file.
    ![how to run jar](https://user-images.githubusercontent.com/95590176/215638049-da018822-be91-4f71-bc99-e9128f7c4fc6.jpg)

    
  
