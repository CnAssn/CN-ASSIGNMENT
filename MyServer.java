import java.io.*;
import java.net.*;
class MyServer
{
  public static void main(String[] args)throws IOException
  {
    int  seqnum=Integer.parseInt(args[0]);
    int j=Integer.parseInt(args[1]);
    int port=Integer.parseInt(args[2]);
    ServerSocket ss=new ServerSocket(port);
    Socket s=ss.accept();
    DataOutputStream dout=new DataOutputStream(s.getOutputStream());
    DataInputStream dis=new DataInputStream(s.getInputStream());
    int i=1;
    while(true)
    {
      try
      {
        String  str=dis.readUTF();
        String [] spack=str.split(" ",4);
        //String [] sp1= spack[2].split(" ",2);
        System.out.println(spack[2]);
        int expecpacknum=Integer.parseInt(spack[2]);
        if(expecpacknum==i)
        {
          if(i % j != 0)
          {
            System.out.println("message= "+str);
            i++;
            String ack=""+expecpacknum;
            dout.writeUTF(ack);
          }
          else
          {
            System.out.println("lost pack "+str);
          }
        }
        else
        {
          String ack=""+(i-1);
          dout.writeUTF(ack);
        }
      }
      catch(Exception e){
        System.out.println(e);
      }
    }
    //ss.close();
  }
}
