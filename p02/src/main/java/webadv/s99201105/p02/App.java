package webadv.s99201105.p02;
import java.util.Scanner;
import java.io.*;
import java.security.*;
import org.apache.commons.codec.digest.DigestUtils;
public class App {
    public static void main(String[] args) throws IOException {
        File in=new File("E://ap.txt");//要加密的文件
        File out=new File("E://ap01.txt");//加密后的文件
        readWriteByLine(in,out);//调用方法加密
        Scanner scanner=new Scanner(System.in);
        System.out.print("输入账号:");
        String account=scanner.nextLine();
        System.out.print("输入密码:");
        String pwd=scanner.nextLine();
        if(check(account,pwd,out)==1){
         System.out.println("登陆成功");
        }else{
            System.out.println("登陆失败");
        }
    }
//获取加密后的编码序列
    public static String getSHA256StrJava(String str) {
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("utf-8"));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }

    private static String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                // 1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }


//将文件进行加密
    public static void readWriteByLine(File in,File out) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(in));
        //字节缓冲输出流
        //BufferedWriter bw = new BufferedWriter(new FileWriter("D://test02.txt",true));不重写
        BufferedWriter bw = new BufferedWriter(new FileWriter(out));//重写原来文件
        if(!out.exists()){
            out.createNewFile();
        }
        String line;
        while ((line = br.readLine()) != null) {
  
            String[] arr = line.split(" ");
            String str=getSHA256StrJava(arr[1]);
            bw.write(arr[0]+" "+str);
            bw.newLine();
        }
        bw.close();
        br.close();

    }
public static int check(String account,String pwd,File in) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader(in));
    String pwd1=getSHA256StrJava(pwd);
    String line;
    int i = 0;
    while ((line = br.readLine()) != null) {
        // 5.对读取到的文本进行切割，获取行中的序号和文本内容。
        String[] arr = line.split(" ");
        if (account.equals(arr[0]) && pwd1.equals(arr[1])) {

            i = 1;
            break;
        }
    }
    br.close();
    return i;
}
   public static String sha256hex(String input) {
        return DigestUtils.sha256Hex(input);
    }

}
