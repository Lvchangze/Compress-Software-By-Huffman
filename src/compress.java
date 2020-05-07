import java.io.*;
import java.lang.String;
import java.lang.StringBuilder;
public class compress {

    public static void compress(File oldFile, String parentPath, String newFileName)throws Exception{
        File newFile = new File(parentPath+"\\"+newFileName);
        if (!newFile.exists()) {
            newFile.createNewFile();
        }

        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(newFile));
        int subNum=countSubFile(oldFile);//总文件的子文件个数

        bos.write(intToByte(subNum));//写入总文件的子文件个数
        bos.flush();
        if(subNum==0){//单个文件
              //写入该文件信息
//            System.out.println(outFile.getName());
//            System.out.println(outFile.getName().length());
                bos.write(oldFile.getName().length());//写入文件名的长度
                bos.flush();
//            System.out.println(outFile.getName().getBytes());
                bos.write(oldFile.getName().getBytes());//写入文件名对于的字符串编码
                bos.flush();
                compressFile(oldFile,parentPath,newFileName);
        }
        else{//文件夹
                //写入该的文件夹信息
                int[] parents = new int[subNum];//存入每个文件夹的子文件个数
                String[] name = new String[subNum];//存每个文件夹的名称
                {//写入根节点的信息
                    bos.write((byte) oldFile.getName().length());
                    bos.flush();
                    bos.write(oldFile.getName().getBytes());
                    bos.flush();
                }
                handleDir(-1, parents, name, oldFile);//填满数组parents和name
                for (int i = 0; i < subNum; i++) {//循环写入文件信息
                    bos.write(intToByte(parents[i]));
                    bos.flush();
                    bos.write(name[i].length());
                    bos.flush();
                    bos.write(name[i].getBytes());
                    bos.flush();
                }

            compressDir(oldFile,parentPath,newFileName);
        }

        bos.flush();
        bos.close();
    }

    public static int number=-1;//下标
    public static void handleDir(int parent,int[] parents ,String[] name,File oldFile){
        File[] files=oldFile.listFiles();
        for(int i=0;i<files.length;i++){
            if(files[i].isDirectory()){//若子文件是文件夹
                number++;
                parents[number]=parent;//存入每个文件夹下的子文件的个数
                name[number]=files[i].getName() ;//存入每个文件夹下的子文件的名称
                handleDir(number,parents,name,files[i]);
            }
            else{//若子文件是文件
                number++;
                parents[number]=parent;
                name[number]=files[i].getName();
            }
        }
    }

    public static int countSubFile(File f) {
        int num=0;
        if(f.isDirectory()){
            num=countDir(f,num);
        }
        return num;
    }

    public static int countDir(File f,int n) {
        File[] files=f.listFiles();
        for(int i=0;i<files.length;i++){
            if(files[i].isDirectory())
                n=countDir(files[i],n)+1;
            else
                n++;
        }
        return n;
    }

    public static void compressFile(File file,String parentPath,String newFileName) throws Exception {
        System.out.println("文件名："+file.getName());
        long time=System.currentTimeMillis();
        int[] byteCount = new int[256];
        //并行
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);
        FileInputStream fis_2 = new FileInputStream(file);
        BufferedInputStream bis_2 = new BufferedInputStream(fis_2);

        File f = new File(parentPath + "\\" + newFileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(f, true));
        if (!f.exists()) {
            f.createNewFile();
        }
        byte[] b = new byte[8192];//缓冲区
        int len;
        while ((len = bis.read(b)) != -1) {
            for (int i = 0; i < len; i++) {
                int tag = b[i] & 0xff;
                byteCount[tag]++;
            }
        }

        HuffmanTree huffmanTree = new HuffmanTree(byteCount);
        long codeLength = (newFileName.length() + 4) * 8 + 256 * 32 + huffmanTree.codeLength;
        System.out.println((double) codeLength / (8 * huffmanTree.codeNum));

        StringBuilder output = new StringBuilder();
        byte[] b2 = new byte[8192];
        int len2;
        int outputlen = 0;

        byte[] o = new byte[8192];//写入东西的存放区

        for (int i = 0; i < 256; i++) {
            bos.write(intToByte(huffmanTree.array[i].rate));
        }

        while ((len2 = bis_2.read(b2)) != -1) {//第二线程
            for (int i = 0; i < len2; i++) {
                int tag = b2[i] & 0xff;
                output.append(huffmanTree.array[tag].code);
                outputlen += huffmanTree.array[tag].codelen;
                if (outputlen >= 8192 * 8) {
                    for (int j = 0; j < 8192 * 8; j += 8) {
                        String str = output.substring(j, j + 8);
                        o[j / 8] = (byte) Integer.parseInt(str, 2);
                    }
                    bos.write(o);
                    output.delete(0, 8192 * 8);
                    outputlen -= 8192 * 8;
                }
            }
        }

        long zeroNum = (8 - (huffmanTree.codeLength) % 8) % 8;
        for (int i = 0; i < zeroNum; i++) {
            output.append("0");
            outputlen++;
        }

        byte[] o2 = new byte[outputlen / 8];//剩下的东西要写入的
        for (int j = 0; j < outputlen; j += 8) {
            String str = output.substring(j, j + 8);
            o2[j / 8] = (byte) Integer.parseInt(str, 2);
        }
        bos.write(o2);
        fis.close();
        fis_2.close();
        bis.close();
        bos.flush();
        bos.close();
        System.out.println("compressFile total time: " + (System.currentTimeMillis() - time) +"ms");
    }

    public static void compressDir(File file,String parentPath,String newFileName)throws Exception{//递归
        long nowTime =System.currentTimeMillis();
        File[] files=file.listFiles();
        for(int i=0;i<files.length;i++){
            if(files[i].isDirectory()) {
                compressDir(files[i],parentPath,newFileName);
            }
            else
                compressFile(files[i],parentPath,newFileName);
        }
        System.out.println("compressDir total time: "+(System.currentTimeMillis()-nowTime)+"ms");
    }

    public static byte[] intToByte(int value) {
        byte[] des = new byte[4];
        des[0] = (byte) (value & 0xff);  // 第一个8个bit位
        des[1] = (byte) ((value >> 8) & 0xff); //第二个8 bit位
        des[2] = (byte) ((value >> 16) & 0xff); //第三个 8 bit位
        des[3] = (byte) ((value >> 24) & 0xff); //第4个 8 bit位
//         (byte)((value >> 24) & 0xFF);
//         value向右移动24位, 然后和0xFF也就是(11111111)进行与运算
//         在内存中生成一个与 value 同类型的值
//         然后把这个值强制转换成byte类型, 再赋值给一个byte类型的变量 des[3]
        return des;
    }
}
