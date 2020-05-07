import java.io.*;
public class decompress {

    public static void decompress(File file, String parentPath) throws Exception {
        long time=System.currentTimeMillis();
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));//读取文件
        byte[] singleInteger = new byte[4];
        bis.read(singleInteger);//读入singleinterger中
        int subNum = bytesToInt(singleInteger, 0);//转化为int型

        if (subNum == 0) {//单个文件
            int nameLength = bis.read();
            byte[] name = new byte[nameLength];
            bis.read(name);
            String filename = "解压后" +new String(name);
            decompressFile(bis, parentPath+"\\"+filename);
        } else {

            System.out.println("子文件个数："+subNum);

            String[] name = new String[subNum];
            int[] parents = new int[subNum];
            byte[] subName;
            int rootNameLength = (bis.read() & 0xff);
            byte[] rootName = new byte[rootNameLength];
            bis.read(rootName);
            String rootFileName = new String(rootName);
            for (int i = 0; i < subNum; i++) {
                bis.read(singleInteger);
                parents[i] = bytesToInt(singleInteger, 0);
                int nameLength = (bis.read() & 0xff);
                subName = new byte[nameLength];
                bis.read(subName);
                name[i] = new String(subName);
            }

            fileStructure fs = new fileStructure(parents, name, parentPath+rootFileName);
            File df;
            df = new File(parentPath+rootFileName);
                df.mkdir();
            for (int i = 0; i < subNum; i++) {
                String nm = fs.subfile[i].filename;
                df = new File(nm);
                if (nm.indexOf('.') >= 0||nm.indexOf("empty_file")>0) {
                   if(nm.indexOf("empty_file")>0){//空
                       df.createNewFile();
                   }
                    else
                       decompressFile(bis, fs.subfile[i].filename);
                } else {
                    df.mkdir();
                }
            }
        }
        System.out.println("decompressFile total time: "+(System.currentTimeMillis()-time)+"ms");
    }

    public static void decompressFile(BufferedInputStream bis, String filename) throws Exception {
//        long time =System.currentTimeMillis();
        byte[] buffer = new byte[8192];
        byte[] integer = new byte[4 * 256];
        bis.read(integer);
        int[] rate = new int[256];
        for (int i = 0; i < 256 * 4; i += 4)
            rate[i / 4] = bytesToInt(integer, i);
        HuffmanTree huffman = new HuffmanTree(rate);//重新构树
        long zero = (8 - (huffman.codeLength % 8))%8;
        long codeByte = ((huffman.codeLength+zero) / 8);
        long times = codeByte / 8192;
        int left = (int) (codeByte % 8192);
        boolean[] buffer2;
        HuffmanNode posNode = huffman.root;//初始为根节点
        File f = new File(filename);
        System.out.println("解压的文件名："+filename);

        if (!f.exists()) {
            f.createNewFile();
        }

        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(f));
        for (int j = 0; j < times; j++) {
            bis.read(buffer);

            buffer2 = new boolean[8192 * 8];
            for (int i = 0; i < 8192; i++) {//输出缓冲区内
                int tmp = buffer[i];
                buffer2[8 * i] = ((tmp & 0x80) >> 7 == 1);
                buffer2[8 * i + 1] = ((tmp & 0x40) >> 6 == 1);
                buffer2[8 * i + 2] = ((tmp & 0x20) >> 5 == 1);
                buffer2[8 * i + 3] = ((tmp & 0x10) >> 4 == 1);
                buffer2[8 * i + 4] = ((tmp & 0x08) >> 3 == 1);
                buffer2[8 * i + 5] = ((tmp & 0x04) >> 2 == 1);
                buffer2[8 * i + 6] = ((tmp & 0x02) >> 1 == 1);
                buffer2[8 * i + 7] = ((tmp & 0x01) == 1);
            }

            for (int i = 0; i < 8192 * 8; i++) {
                if (buffer2[i])
                    posNode = posNode.right;
                else
                    posNode = posNode.left;
                if (posNode.isLeaf) {
                    bos.write((byte) posNode.data);
                    posNode = huffman.root;
                }
            }
        }

        buffer=new byte[left];//剩下的
        bis.read(buffer);
        buffer2 = new boolean[left * 8];
        for (int i = 0; i < left; i++) {//输出剩下的
            int tmp = buffer[i];
            buffer2[8 * i] = ((tmp & 0x80) >> 7 == 1);
            buffer2[8 * i + 1] = ((tmp & 0x40) >> 6 == 1);
            buffer2[8 * i + 2] = ((tmp & 0x20) >> 5 == 1);
            buffer2[8 * i + 3] = ((tmp & 0x10) >> 4 == 1);
            buffer2[8 * i + 4] = ((tmp & 0x08) >> 3 == 1);
            buffer2[8 * i + 5] = ((tmp & 0x04) >> 2 == 1);
            buffer2[8 * i + 6] = ((tmp & 0x02) >> 1 == 1);
            buffer2[8 * i + 7] = ((tmp & 0x01) == 1);
        }
        for (int i = 0; i < (left * 8 - zero); i++) {
            if (buffer2[i])
                posNode = posNode.right;
            else {
                posNode = posNode.left;
            }
            if (posNode.isLeaf) {
                bos.write((byte) posNode.data);
                posNode = huffman.root;
            }
        }
        bos.flush();
        bos.close();
//        System.out.println("解压文件时间"+(System.currentTimeMillis()-time));
    }

    public static int bytesToInt(byte[] des, int offset) {
        int value= des[offset] & 0xff
                | ((des[offset + 1] & 0xff) << 8)
                | ((des[offset + 2] & 0xff) << 16)
                | (des[offset + 3] & 0xff) << 24;
        return value;
    }
}
