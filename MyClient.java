import java.io.*;
import java.net.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
class MyClient
{
  public static String getMd5(String input)
  {
    try
    {
       MessageDigest md = MessageDigest.getInstance("MD5");
       byte[] messageDigest = md.digest(input.getBytes());
       BigInteger no = new BigInteger(1, messageDigest);
       String hashtext = no.toString(16);
       while (hashtext.length() < 32)
       {
         hashtext = "0" + hashtext;
       }
       return hashtext;
    }
    catch (NoSuchAlgorithmException e) {
       throw new RuntimeException(e);
    }
  }
  public static void main(String[] args)throws IOException
  {
    int  i=Integer.parseInt(args[1]);
    int  basenum=1;
    int  win=Integer.parseInt(args[0]);
    int  seq=1;
    int n=Integer.parseInt(args[2]);
    String recvIP=args[3];
    int recvPort=Integer.parseInt(args[4]);
    String pack;
    int lastackrec=0;
    Socket s=new Socket(recvIP,recvPort);
    DataOutputStream dout=new DataOutputStream(s.getOutputStream());
    DataInputStream dis=new DataInputStream(s.getInputStream());
    while(seq<=n)
    {
      while(seq< basenum+win && seq<=n)
      {
        try
        {
          pack="message number "+seq+" ";  //pack=pack.concat(seq.toString());
          pack=pack.concat(getMd5(pack));
          System.out.println(""+pack);
          dout.writeUTF(pack);
          seq++;
        }
        catch(Exception e){System.out.println(e);}
      }
      int temp=0,k=win;
      while(temp<win)
      {
        try
        {
          String  ack=(String)dis.readUTF();
          if(Integer.parseInt(ack)== seq-k-1)
          // there is error here 
          {
            lastackrec=Integer.parseInt(ack);
            basenum++;
            k--;
          }
          else
          {
            System.out.println("Wrong ack");
            seq=lastackrec+1;
          }
          temp++;
        }
        catch(Exception e)
        {System.out.println(e);}
      }
    }
    dout.flush();
    dout.close();
    s.close();
  }
}
