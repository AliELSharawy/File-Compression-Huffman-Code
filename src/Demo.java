import java.io.*;
public class Demo {
    public static void main(String[] args) throws IOException {
        String operator = args[0];
        String inputFilePath = args[1];
        String outputFilePath = "";
        File file = new File(inputFilePath);
        String fileName = getFileName(file.getName());

        if(operator.equals("c")){
            int n = Integer.parseInt(args[2]);
            outputFilePath = file.getParentFile().getAbsolutePath() + "\\" +  file.getName() + ".hc";
            FileCompression fileCompression = new FileCompression(inputFilePath, outputFilePath, n);
            try {
                fileCompression.compress();
            }catch (IOException ioException){
                System.out.println("Invalid");
            }
        }
        else if(operator.equals("d")){
            outputFilePath = file.getParentFile().getAbsolutePath() + "\\extracted." + fileName;
            FileDecompression fileDecompression = new FileDecompression(inputFilePath,outputFilePath);
            try {
                fileDecompression.decompress();
            }catch (ClassNotFoundException c){
                System.out.println("map not found");
            }
        }
    }

    public static String getFileName(String fileName){
        for(int i = fileName.length() - 1; i > -1; i--){
            if(fileName.charAt(i) == '.')
                return fileName.substring(0,i);
        }
        return "";
    }

}
