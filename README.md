# File Compression Huffman Code
  - Implementation of Huffman Algorithm to compress arbitrary files with storing the representation of the codewords in the compressed file,
    so that you can decompress the file back.
  - we also store number of compressed words to help in decompression file back.
  - It has the capability of considering more than one byte. For example, instead of just collecting the frequencies and finding codewords for single bytes.
    The same can be done assuming the basic unit is n bytes, where n is an integer.
    
## How to run Jar:
  - single runnable jar is used for both compression and decompression.
  - To use jar for compressing an input file, the following will be called:\
        java -jar HuffmanRunner.jar c absolute_path_to_input_file n\
    where c means compressing the file and n is the number of bytes that will be considered together.
  - To use it for decompressing an input file, the following be called:\
        java -jar HuffmanRunner.jar d absolute_path_to_input_file\
    where d means decompressing the file.
    
    ![how to run jar](https://user-images.githubusercontent.com/95590176/215638049-da018822-be91-4f71-bc99-e9128f7c4fc6.jpg)
    
## Analysis:
  - Seq File: file used to run our program has size 500 MB
  ![1](https://user-images.githubusercontent.com/95590176/215638650-fea672da-e651-486b-86e9-007212f34584.jpg)
  ![2](https://user-images.githubusercontent.com/95590176/215638654-f2c5161e-1e95-48cc-8605-6e1971c30026.jpg)
  ![3](https://user-images.githubusercontent.com/95590176/215638657-a0e7fd8c-5f3e-46db-89c9-5ae92f4221f9.jpg)

  - Lecture File: our greedy lecture pdf has size 800 KB
    ![1](https://user-images.githubusercontent.com/95590176/215639194-4fcde865-b4b4-45fa-bd4f-dd734f0fad18.jpg)
    ![2](https://user-images.githubusercontent.com/95590176/215639197-9e6d6710-ce96-48a0-a10c-232c54e0f19a.jpg)
    ![3](https://user-images.githubusercontent.com/95590176/215639199-e5f0ff9d-ee37-4075-b660-3f3344e7c89d.jpg)
    ![4](https://user-images.githubusercontent.com/95590176/215639200-c758cf9f-bb32-4148-8720-5254f196061f.jpg)

## comparison between our program and 7-Zip:
  - For SEQ File:\
    My best compression ratio for Lecture file = 0.364 at N = 4\
    7-Zip compression ratio = 117212 / 487944 = 0.24
  - For Lecture File:\
    My best compression ratio for Lecture file = 0.94 at N = 1\
    7-Zip compression ratio = 554 / 807 = 0.68
    
